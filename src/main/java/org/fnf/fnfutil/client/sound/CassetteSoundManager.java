package org.fnf.fnfutil.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;
import java.util.Map;

public class CassetteSoundManager {
    private static final Map<BlockPos, SoundInstance> playingSounds = new HashMap<>();

    public static void play(BlockPos pos, SoundEvent sound, float range) {
        // Global playback: player-centered, non-attenuated sound
        SoundInstance instance = SimpleSoundInstance.forUI(sound, 1.0F, 1.0F);
        Minecraft.getInstance().getSoundManager().play(instance);
        playingSounds.put(pos, instance);
    }

    public static void stop(BlockPos pos) {
        SoundInstance instance = playingSounds.remove(pos);
        if (instance != null) {
            Minecraft.getInstance().getSoundManager().stop(instance);
        }
    }
}
