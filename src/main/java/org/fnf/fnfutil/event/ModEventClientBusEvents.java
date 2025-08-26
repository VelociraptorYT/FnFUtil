package org.fnf.fnfutil.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.fnf.fnfutil.client.ModClientSetup;
import org.fnf.fnfutil.client.renderer.*;
import org.fnf.fnfutil.block.entity.ModBlockEntities;
import org.fnf.fnfutil.client.script.ScriptManager;
import org.fnf.fnfutil.client.script.ScriptOverlayRenderer;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.io.IOException;

import static org.fnf.fnfutil.fnfmain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {



    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(BonnieEarsItem.class, new BonnieEarsRenderer());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.COFFEE_MACHINE_BLOCK_ENTITY.get(), CoffeeMachineBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CASSETTE_PLAYER_BLOCK_ENTITY.get(), CassettePlayerBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.COFFEE_BLOCK_ENTITY.get(), CoffeeBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIONA_BLOCK_ENTITY.get(), FionaBlockRenderer::new);
    }
}
