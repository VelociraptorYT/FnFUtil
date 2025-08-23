package org.fnf.fnfutil.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.fnf.fnfutil.client.sound.CassetteSoundManager;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;

public class StopCassetteSoundPacket {
    private final BlockPos pos;

    public StopCassetteSoundPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(StopCassetteSoundPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.pos);
    }

    public static StopCassetteSoundPacket decode(FriendlyByteBuf buf) {
        return new StopCassetteSoundPacket(buf.readBlockPos());
    }

    public static void handle(StopCassetteSoundPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            CassetteSoundManager.stop(packet.pos);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
