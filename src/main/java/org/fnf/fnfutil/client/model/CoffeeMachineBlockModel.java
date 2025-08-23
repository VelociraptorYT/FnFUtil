package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CoffeeMachineBlockEntity;
import org.fnf.fnfutil.fnfmain;
import software.bernie.geckolib3.model.AnimatedGeoModel;



public class CoffeeMachineBlockModel extends AnimatedGeoModel<CoffeeMachineBlockEntity> {

    @Override
    public ResourceLocation getModelResource(CoffeeMachineBlockEntity coffeeMachineBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/coffee_machine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoffeeMachineBlockEntity coffeeMachineBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/coffee_machine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoffeeMachineBlockEntity coffeeMachineBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}
