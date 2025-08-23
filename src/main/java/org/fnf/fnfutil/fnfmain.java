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
import org.fnf.fnfutil.item.ModItems;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import org.fnf.fnfutil.client.renderer.BonnieEarsRenderer;
import org.fnf.fnfutil.network.ModNetworking;
import org.fnf.fnfutil.sound.ModSounds;
import org.slf4j.Logger;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import org.fnf.fnfutil.item.ModCreativeModeTab;
// The value here should match an entry in the META-INF/mods.toml file
@Mod(fnfmain.MOD_ID)
public class fnfmain {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fnfutil";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public fnfmain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered

        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModSounds.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworking::registerMessages);

    }
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        GeoArmorRenderer.registerArmorRenderer(BonnieEarsItem.class, BonnieEarsRenderer::new);
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
