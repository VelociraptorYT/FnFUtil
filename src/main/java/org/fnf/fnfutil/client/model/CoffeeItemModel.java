package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.CoffeeItem;
import org.fnf.fnfutil.item.custom.CoffeeMachineItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CoffeeItemModel extends AnimatedGeoModel<CoffeeItem> {

    @Override
    public ResourceLocation getModelResource(CoffeeItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/coffee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoffeeItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/coffee.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoffeeItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}

