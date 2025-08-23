package org.fnf.fnfutil.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;

public class CassetteSoundManager {
    private static final Map<BlockPos, CassetteSoundInstance> playingSounds = new HashMap<>();

    public static void play(BlockPos pos, SoundEvent sound) {
        CassetteSoundInstance instance = new CassetteSoundInstance(sound, pos);
        Minecraft.getInstance().getSoundManager().play(instance);
        playingSounds.put(pos, instance);
    }

    public static void stop(BlockPos pos) {
        CassetteSoundInstance instance = playingSounds.remove(pos);
        if (instance != null) {
            instance.stopNow();
        }
    }

    private static class CassetteSoundInstance extends AbstractTickableSoundInstance {
        private boolean done = false;

        public CassetteSoundInstance(SoundEvent sound, BlockPos pos) {
            super(sound, SoundSource.RECORDS, RandomSource.create());

            this.x = pos.getX() + 0.5;
            this.y = pos.getY() + 0.5;
            this.z = pos.getZ() + 0.5;
            this.volume = 1.0F;
            this.pitch = 1.0F;
            this.looping = false;
            this.delay = 0;
        }

        @Override
        public boolean canPlaySound() {
            return !done;
        }

        @Override
        public void tick() {
            if (done) {
                this.stop();
            }
        }

        public void stopNow() {
            this.done = true;
        }
    }
}
