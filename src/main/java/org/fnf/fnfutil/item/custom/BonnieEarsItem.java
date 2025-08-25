package org.fnf.fnfutil.item.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import org.fnf.fnfutil.sound.ModSounds;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class BonnieEarsItem extends GeoArmorItem implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean wasEquipped = false;

    public BonnieEarsItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return false;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            boolean isWearing = living.getItemBySlot(getSlot()) == stack;
            var tag = stack.getOrCreateTag();

            boolean wasEquipped = tag.getBoolean("WasEquipped");

            if (isWearing && !wasEquipped) {
                tag.putBoolean("WasEquipped", true);
            }

            if (!isWearing && wasEquipped) {
                tag.putBoolean("WasEquipped", false);
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.ENDO_DETACH.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}
