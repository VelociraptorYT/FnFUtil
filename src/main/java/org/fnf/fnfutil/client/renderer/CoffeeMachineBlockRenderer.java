package org.fnf.fnfutil.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CoffeeMachineBlockEntity;
import org.fnf.fnfutil.client.model.CoffeeMachineBlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CoffeeMachineBlockRenderer extends GeoBlockRenderer<CoffeeMachineBlockEntity> {

    public CoffeeMachineBlockRenderer(Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new CoffeeMachineBlockModel());
    }

    public RenderType getRenderType(CoffeeMachineBlockEntity animatable,
                                    ResourceLocation texture,
                                    MultiBufferSource bufferSource,
                                    float partialTick) {
        // Entity cutout (no culling) is the usual render type for block entities with textures
        return RenderType.entityCutout(getTextureLocation(animatable));
    }
}