package org.fnf.fnfutil.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;
import org.fnf.fnfutil.client.sound.CassetteSoundManager;

import java.util.function.Supplier;

public class StopCassetteSoundPacket {
    public final BlockPos pos;

    public StopCassetteSoundPacket(BlockPos pos) {
        this.pos = pos;
    }

    public StopCassetteSoundPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(StopCassetteSoundPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                CassetteSoundManager.stop(packet.pos);

            }
        });
        ctx.get().setPacketHandled(true);
    }
}
