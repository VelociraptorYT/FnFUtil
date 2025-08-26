package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.FionaBlockEntity;
import org.fnf.fnfutil.fnfmain;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class FionaBlockModel extends AnimatedGeoModel<FionaBlockEntity> {

    @Override
    public ResourceLocation getModelResource(FionaBlockEntity fionaBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/fiona.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FionaBlockEntity fionaBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/fiona.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FionaBlockEntity fionaBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}
