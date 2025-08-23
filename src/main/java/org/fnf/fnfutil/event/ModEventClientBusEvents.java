package org.fnf.fnfutil.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.fnf.fnfutil.client.renderer.CassettePlayerBlockRenderer;
import org.fnf.fnfutil.client.renderer.CoffeeMachineBlockRenderer;
import org.fnf.fnfutil.client.renderer.CoffeeBlockRenderer;
import org.fnf.fnfutil.block.entity.ModBlockEntities;
import org.fnf.fnfutil.client.renderer.BonnieEarsRenderer;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

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

    }
}
