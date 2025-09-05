package org.fnf.fnfutil.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import org.fnf.fnfutil.block.entity.custom.Sl33pyPlushBlockEntity;
import org.fnf.fnfutil.client.model.Sl33pyPlushBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class Sl33pyPlushBlockRenderer extends GeoBlockRenderer<Sl33pyPlushBlockEntity> {

    public Sl33pyPlushBlockRenderer(Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new Sl33pyPlushBlockModel());
    }

    public RenderType getRenderType(Sl33pyPlushBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        // Entity cutout (no culling) is the usual render type for block entities with textures
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}