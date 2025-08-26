package org.fnf.fnfutil.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.FionaBlockEntity;
import org.fnf.fnfutil.client.model.FionaBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class FionaBlockRenderer extends GeoBlockRenderer<FionaBlockEntity> {

    public FionaBlockRenderer(Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new FionaBlockModel());
    }

    public RenderType getRenderType(FionaBlockEntity animatable,
                                    ResourceLocation texture,
                                    MultiBufferSource bufferSource,
                                    float partialTick) {
        // Entity cutout (no culling) is the usual render type for block entities with textures
        return RenderType.entityCutout(getTextureLocation(animatable));
    }
}