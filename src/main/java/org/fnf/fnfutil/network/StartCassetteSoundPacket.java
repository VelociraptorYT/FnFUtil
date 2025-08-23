package org.fnf.fnfutil.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;
import org.fnf.fnfutil.client.sound.CassetteSoundManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StartCassetteSoundPacket {
    private final BlockPos pos;
    private final ResourceLocation soundId;

    public StartCassetteSoundPacket(BlockPos pos, ResourceLocation soundId) {
        this.pos = pos;
        this.soundId = soundId;
    }

    public static void encode(StartCassetteSoundPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.pos);
        buf.writeResourceLocation(packet.soundId);
    }

    public static StartCassetteSoundPacket decode(FriendlyByteBuf buf) {
        return new StartCassetteSoundPacket(buf.readBlockPos(), buf.readResourceLocation());
    }

    public static void handle(StartCassetteSoundPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            SoundEvent sound = Registry.SOUND_EVENT.get(packet.soundId);
            if (sound != null) {
                CassetteSoundManager.play(packet.pos, sound);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
