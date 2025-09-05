package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.FionaItem;
import org.fnf.fnfutil.item.custom.Sl33pyPlushItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Sl33pyPlushItemModel extends AnimatedGeoModel<Sl33pyPlushItem> {
    @Override
    public ResourceLocation getModelResource(Sl33pyPlushItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/sl33py_plush.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sl33pyPlushItem entity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/sl33py_plush.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sl33pyPlushItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/sl33py_plush_animation.json");
    }
}
