package org.fnf.fnfutil.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CassettePlayerBlockEntity;
import org.fnf.fnfutil.block.entity.custom.CoffeeMachineBlockEntity;
import org.fnf.fnfutil.client.model.CassettePlayerBlockModel;
import org.fnf.fnfutil.client.model.CoffeeMachineBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CassettePlayerBlockRenderer extends GeoBlockRenderer<CassettePlayerBlockEntity> {

    public CassettePlayerBlockRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new CassettePlayerBlockModel());
    }

    public RenderType getRenderType(CassettePlayerBlockEntity animatable,
                                    ResourceLocation texture,
                                    MultiBufferSource bufferSource,
                                    float partialTick) {
        // Entity cutout (no culling) is the usual render type for block entities with textures
        return RenderType.entityCutout(getTextureLocation(animatable));
    }
}