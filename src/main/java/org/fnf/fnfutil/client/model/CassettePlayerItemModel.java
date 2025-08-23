package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.CassettePlayerItem;
import org.fnf.fnfutil.item.custom.CoffeeMachineItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CassettePlayerItemModel extends AnimatedGeoModel<CassettePlayerItem> {

    @Override
    public ResourceLocation getModelResource(CassettePlayerItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/cassette_player.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CassettePlayerItem object) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/cassette_player.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CassettePlayerItem animatable) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/cassette_player.animation.json");
    }
}