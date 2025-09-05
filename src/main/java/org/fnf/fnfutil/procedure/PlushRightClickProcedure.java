package org.fnf.fnfutil.procedure;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class PlushRightClickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        int animationValue = 1;
        BlockPos pos = new BlockPos(x, y, z);
        BlockState state = world.getBlockState(pos);

        Property<?> animationProp = state.getBlock().getStateDefinition().getProperty("animation");
        if (animationProp instanceof IntegerProperty intProp) {
            if (intProp.getPossibleValues().contains(animationValue)) {
                world.setBlock(pos, state.setValue(intProp, animationValue), 3);
            }
        }

        if (world instanceof Level level) {
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("fnfutil:plush_honk"));
            if (sound == null) return;

            if (!level.isClientSide()) {
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.playLocalSound(x, y, z, sound, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }
}