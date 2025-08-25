package org.fnf.fnfutil.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.sound.ModSounds;

public class ToyGunItem extends Item {
    public ToyGunItem(Item.Properties props) {
        super(props);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Hold indefinitely until released
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // Keeps use cycle active
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack == newStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player) || level.isClientSide) return;

        if (fnfmain.DEBUGGING) System.out.println("TG-Debug: releaseUsing triggered");

        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(look.scale(5)); // 5 block range

        HitResult hit = level.clip(new net.minecraft.world.level.ClipContext(
                start, end,
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE,
                player
        ));

        if (fnfmain.DEBUGGING) System.out.println("TG-Debug: Raycast hit = " + hit.getType());

        var entities = level.getEntities(player, player.getBoundingBox().expandTowards(look.scale(5)).inflate(1.0),
                e -> e.isPickable() && e != player);

        for (var target : entities) {
            Vec3 toTarget = target.getEyePosition().subtract(start);
            if (toTarget.normalize().dot(look) > 0.98) {
                target.hurt(net.minecraft.world.damagesource.DamageSource.playerAttack(player), 0.001F);
                if (fnfmain.DEBUGGING) System.out.println("TG-Debug: Hit entity = " + target.getName().getString());
                break;
            }
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.TOY_GUN_FIRE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        if (fnfmain.DEBUGGING) System.out.println("TG-Debug: Sound played");
    }
}
