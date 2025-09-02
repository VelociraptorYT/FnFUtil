package org.fnf.fnfutil.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.network.NetworkDirection;
import org.fnf.fnfutil.block.entity.ModBlockEntities;
import org.fnf.fnfutil.network.ModNetworking;
import org.fnf.fnfutil.network.StartCassetteSoundPacket;
import org.fnf.fnfutil.network.StopCassetteSoundPacket;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CassettePlayerBlockEntity extends BlockEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private AnimationController<CassettePlayerBlockEntity> controller;

    private ItemStack cassette = ItemStack.EMPTY;
    private boolean playing = false;

    private long tickCount = 0;
    private long recordStartedTick = 0;
    private int ticksSinceLastEvent = 0;
    private float soundRange = Float.MAX_VALUE; // default range in blocks

    private String currentAnimation;

    public CassettePlayerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CASSETTE_PLAYER_BLOCK_ENTITY.get(), pos, state);
        this.currentAnimation = "animation.cassette_player.idle"; // fallback
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        controller = new AnimationController<>(this, "controller", 0, this::predicate);
        animationData.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(getCurrentAnimation(), true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean hasCassette() {
        return !cassette.isEmpty();
    }

    public ItemStack getCassette() {
        return cassette;
    }

    public void setSoundRange(float range) {
        this.soundRange = range;
    }

    public float getSoundRange() {
        return soundRange;
    }

    public void insertCassette(ItemStack stack) {
        this.cassette = stack.copy();
        startPlaying();
        setChanged();
    }

    public ItemStack ejectCassette() {
        if (cassette.isEmpty()) return ItemStack.EMPTY;

        ItemStack temp = cassette.copy();
        cassette = ItemStack.EMPTY;
        stopPlaying();
        setChanged();
        return temp;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void startPlaying() {
        if (!level.isClientSide() && !cassette.isEmpty() && !playing) {
            if (cassette.getItem() instanceof RecordItem record) {
                ResourceLocation soundId = Registry.SOUND_EVENT.getKey(record.getSound());
                for (ServerPlayer player : ((ServerLevel) level).players()) {
                    ModNetworking.CHANNEL.sendTo(
                            new StartCassetteSoundPacket(worldPosition, soundId.toString(), getSoundRange()),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                }

                playing = true;
                recordStartedTick = tickCount;
                setChanged();
                setCurrentAnimation("animation.cassette_player.play_puppet");
            }
        }
    }

    public void stopPlaying() {
        if (!level.isClientSide() && playing) {
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                ModNetworking.CHANNEL.sendTo(
                        new StopCassetteSoundPacket(worldPosition),
                        player.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );
            }

            playing = false;
            setChanged();
            setCurrentAnimation("animation.cassette_player.idle");
        }
    }

    public void setCurrentAnimation(String animationName) {
        this.currentAnimation = animationName != null ? animationName : "animation.cassette_player.idle";

        if (level != null && level.isClientSide && controller != null) {
            controller.markNeedsReload(); // force refresh
            controller.setAnimation(new AnimationBuilder().addAnimation(this.currentAnimation, true));
        }

        if (level != null && !level.isClientSide) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getCurrentAnimation() {
        return currentAnimation != null ? currentAnimation : "animation.cassette_player.idle";
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putString("CurrentAnimation", getCurrentAnimation());
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        currentAnimation = tag.contains("CurrentAnimation") ? tag.getString("CurrentAnimation") : "animation.cassette_player.idle";
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        currentAnimation = tag.contains("CurrentAnimation") ? tag.getString("CurrentAnimation") : "animation.cassette_player.idle";
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CassettePlayerBlockEntity entity) {
        entity.ticksSinceLastEvent++;
        entity.tickCount++;

        if (entity.playing && entity.cassette.getItem() instanceof RecordItem record) {
            if (entity.tickCount >= entity.recordStartedTick + record.getLengthInTicks()) {
                entity.stopPlaying();
            } else if (entity.ticksSinceLastEvent >= 20) {
                entity.ticksSinceLastEvent = 0;
                level.gameEvent(null, GameEvent.JUKEBOX_PLAY, pos);
            }
        }
    }
}
