package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import org.fnf.fnfutil.item.custom.CowboyHatItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CowboyHatModel extends AnimatedGeoModel<CowboyHatItem> {

    @Override
    public ResourceLocation getModelResource(CowboyHatItem cowboyHatItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/cowboy_hat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CowboyHatItem cowboyHatItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/armor/cowboy_hat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CowboyHatItem cowboyHatItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}
