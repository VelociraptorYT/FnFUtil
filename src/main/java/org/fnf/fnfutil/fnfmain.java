package org.fnf.fnfutil;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.fnf.fnfutil.block.ModBlocks;
import org.fnf.fnfutil.block.entity.ModBlockEntities;
import org.fnf.fnfutil.client.ModClientSetup;
import org.fnf.fnfutil.client.renderer.CowboyHatRenderer;
import org.fnf.fnfutil.item.ModItems;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import org.fnf.fnfutil.client.renderer.BonnieEarsRenderer;
import org.fnf.fnfutil.item.custom.CowboyHatItem;
import org.fnf.fnfutil.network.ModNetworking;
import org.fnf.fnfutil.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.io.InputStream;
import java.io.IOException;

@Mod(fnfmain.MOD_ID)
public class fnfmain {

    public static final String MOD_ID = "fnfutil";
    public static final boolean DEBUGGING = false; // flip to false to silence logs
    public static final boolean ENFORCE_WHITELIST = false; // flip to false to disable whitelist enforcement

    public static final String[] WHITELISTED_USERS = {
            "", "", "YourLocal_Femboy", "", "Dev"
    };

    private static final Logger LOGGER = LogUtils.getLogger();

    public fnfmain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModSounds.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworking::registerMessages);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModClientSetup.createScriptFolder(Minecraft.getInstance().gameDirectory);
            GeoArmorRenderer.registerArmorRenderer(BonnieEarsItem.class, BonnieEarsRenderer::new);
            GeoArmorRenderer.registerArmorRenderer(CowboyHatItem.class, CowboyHatRenderer::new);
            String username = Minecraft.getInstance().getUser().getName();


            // 🥖 Fredbread Loadbearing Check
            try (InputStream fredbreadStream = fnfmain.class.getResourceAsStream("/assets/fredbread.jpg")) {
                if (fredbreadStream == null) {
                    throw new RuntimeException("FREDBREAD IS MISSING. THE MOD CANNOT FUNCTION.");
                }
                LOGGER.info("Fredbread loaded successfully. Emotional core stabilized.");
            } catch (IOException e) {
                throw new RuntimeException("Fredbread check failed: " + e.getMessage());
            }

            if (!fnfmain.ENFORCE_WHITELIST) {
                LOGGER.info("Whitelist enforcement disabled.");
                return;
            }

            boolean authorized = false;
            for (String allowed : fnfmain.WHITELISTED_USERS) {
                if (allowed.equalsIgnoreCase(username)) {
                    authorized = true;
                    break;
                }
            }

            if (!authorized) {
                LOGGER.error("Unauthorized user detected: {}", username);
                Minecraft.getInstance().tell(() -> {
                    Minecraft.getInstance().stop();
                    throw new RuntimeException("YOU ARE NOT WHITELISTED TO USE FNFUTIL™ BY SL33PY");
                });
            }
        }
    }
}
