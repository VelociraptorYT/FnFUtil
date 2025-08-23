package org.fnf.fnfutil.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.mojang.text2speech.Narrator.LOGGER;


public class ModNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel CHANNEL;

    public static void registerMessages() {
        CHANNEL = NetworkRegistry.newSimpleChannel(

                new ResourceLocation("fnfutil", "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        LOGGER.info("Registered networking channel for fnfutil");


        int id = 0;
        CHANNEL.registerMessage(id++, StartCassetteSoundPacket.class,
                StartCassetteSoundPacket::encode,
                StartCassetteSoundPacket::decode,
                StartCassetteSoundPacket::handle);

        CHANNEL.registerMessage(id++, StopCassetteSoundPacket.class,
                StopCassetteSoundPacket::encode,
                StopCassetteSoundPacket::decode,
                StopCassetteSoundPacket::handle);
    }
}

