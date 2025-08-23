package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BonnieEarsModel extends AnimatedGeoModel<BonnieEarsItem> {

    @Override
    public ResourceLocation getModelResource(BonnieEarsItem bonnieEarsItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/bonnie_ears.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BonnieEarsItem bonnieEarsItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/armor/bonnie_ears.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BonnieEarsItem bonnieEarsItem) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/dead_animation.json");
    }
}
