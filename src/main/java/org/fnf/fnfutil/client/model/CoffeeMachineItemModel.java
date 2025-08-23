package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CoffeeMachineBlockEntity;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.CoffeeMachineItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CoffeeMachineItemModel extends AnimatedGeoModel<CoffeeMachineItem> {

    @Override
    public ResourceLocation getModelResource(CoffeeMachineItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/coffee_machine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoffeeMachineItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/model/block/coffee_machine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoffeeMachineItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}

