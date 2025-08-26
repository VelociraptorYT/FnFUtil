package org.fnf.fnfutil.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import net.minecraftforge.network.NetworkDirection;

public class ModNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(fnfmain.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void registerMessages() {
        CHANNEL.messageBuilder(StartCassetteSoundPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StartCassetteSoundPacket::new)
                .encoder(StartCassetteSoundPacket::encode)
                .consumerMainThread(StartCassetteSoundPacket::handle)
                .add();
        CHANNEL.messageBuilder(StopCassetteSoundPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StopCassetteSoundPacket::new)
                .encoder(StopCassetteSoundPacket::encode)
                .consumerMainThread(StopCassetteSoundPacket::handle)
                .add();
    }
}
