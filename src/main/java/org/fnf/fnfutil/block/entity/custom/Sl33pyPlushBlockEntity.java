package org.fnf.fnfutil.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.fnf.fnfutil.block.custom.Sl33pyPlushBlock;
import org.fnf.fnfutil.block.entity.ModBlockEntities;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class Sl33pyPlushBlockEntity extends RandomizableContainerBlockEntity implements IAnimatable, WorldlyContainer {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    private String prevAnim = "0";

    public Sl33pyPlushBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SL33PY_PLUSH_BLOCK_ENTITY.get(), pos, state);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        int animValue = getBlockState().getValue(Sl33pyPlushBlock.ANIMATION);
        String anim = String.valueOf(animValue);

        if (anim.equals("0")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(anim, EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends BlockEntity & IAnimatable> PlayState procedurePredicate(AnimationEvent<E> event) {
        int animValue = getBlockState().getValue(Sl33pyPlushBlock.ANIMATION);
        String anim = String.valueOf(animValue);

        if (!anim.equals("0") && event.getController().getAnimationState() == AnimationState.Stopped) {
            if (!anim.equals(prevAnim)) {
                event.getController().markNeedsReload();
            }

            event.getController().setAnimation(new AnimationBuilder().addAnimation(anim, EDefaultLoopTypes.PLAY_ONCE));

            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                Property<?> prop = getBlockState().getBlock().getStateDefinition().getProperty("animation");
                if (prop instanceof IntegerProperty intProp) {
                    level.setBlock(worldPosition, getBlockState().setValue(intProp, 0), 3);
                }
                event.getController().markNeedsReload();
            }
        }

        prevAnim = anim;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!trySaveLootTable(tag)) {
            stacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.saveAllItems(tag, stacks);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (!tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, stacks);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("sl33py_plush");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.stacks = items;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!isRemoved() && side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            return handlers[side.ordinal()].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers) {
            handler.invalidate();
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv) {
        return ChestMenu.threeRows(id, inv, this);
    }
}
