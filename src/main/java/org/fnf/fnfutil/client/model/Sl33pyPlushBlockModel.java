package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.FionaBlockEntity;
import org.fnf.fnfutil.block.entity.custom.Sl33pyPlushBlockEntity;
import org.fnf.fnfutil.fnfmain;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Sl33pyPlushBlockModel extends AnimatedGeoModel<Sl33pyPlushBlockEntity> {

    @Override
    public ResourceLocation getModelResource(Sl33pyPlushBlockEntity animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/sl33py_plush.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sl33pyPlushBlockEntity entity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/sl33py_plush.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sl33pyPlushBlockEntity animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/sl33py_plush.animation.json");
    }
}