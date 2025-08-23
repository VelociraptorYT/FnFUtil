package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CoffeeBlockEntity;
import org.fnf.fnfutil.fnfmain;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class CoffeeBlockModel extends AnimatedGeoModel<CoffeeBlockEntity> {

    @Override
    public ResourceLocation getModelResource(CoffeeBlockEntity coffeeBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/coffee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoffeeBlockEntity coffeeBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/coffee.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoffeeBlockEntity coffeeBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}
