package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.FionaItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FionaItemModel extends AnimatedGeoModel<FionaItem> {

    @Override
    public ResourceLocation getModelResource(FionaItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/fiona.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FionaItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/model/block/fiona.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FionaItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}

