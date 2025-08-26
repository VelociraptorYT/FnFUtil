package org.fnf.fnfutil.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.fnf.fnfutil.client.sound.CassetteSoundManager;

import java.util.function.Supplier;

public class StartCassetteSoundPacket {
    public final BlockPos pos;
    public final String soundId; // Stored as string for safe serialization

    public StartCassetteSoundPacket(BlockPos pos, String soundId) {
        this.pos = pos;
        this.soundId = soundId;
    }

    public StartCassetteSoundPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.soundId = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(soundId);
    }

    public static void handle(StartCassetteSoundPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                ResourceLocation soundLoc = new ResourceLocation(packet.soundId);
                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundLoc);

                if (sound != null) {
                    CassetteSoundManager.play(packet.pos, sound);
                } else {
                    System.err.println("CassetteSoundManager: Unknown sound ID " + packet.soundId);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
