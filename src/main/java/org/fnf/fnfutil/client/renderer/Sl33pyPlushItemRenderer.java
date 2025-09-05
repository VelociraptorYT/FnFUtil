package org.fnf.fnfutil.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SaddleItem;
import org.fnf.fnfutil.client.model.FionaItemModel;
import org.fnf.fnfutil.client.model.Sl33pyPlushItemModel;
import org.fnf.fnfutil.item.custom.FionaItem;
import org.fnf.fnfutil.item.custom.Sl33pyPlushItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class Sl33pyPlushItemRenderer extends GeoItemRenderer<Sl33pyPlushItem> {
    public Sl33pyPlushItemRenderer() {
        super(new Sl33pyPlushItemModel());
    }


    public RenderType getRenderType(Sl33pyPlushItem animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        // Cutout render type for item with transparency
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
