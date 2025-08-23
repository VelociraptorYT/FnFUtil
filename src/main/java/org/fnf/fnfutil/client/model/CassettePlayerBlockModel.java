package org.fnf.fnfutil.client.model;

import net.minecraft.resources.ResourceLocation;
import org.fnf.fnfutil.block.entity.custom.CassettePlayerBlockEntity;
import org.fnf.fnfutil.fnfmain;
import org.fnf.fnfutil.item.custom.CassettePlayerItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CassettePlayerBlockModel extends AnimatedGeoModel<CassettePlayerBlockEntity> {

    @Override
    public ResourceLocation getModelResource(CassettePlayerBlockEntity CassettePlayerBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "geo/cassette_player.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CassettePlayerBlockEntity CassettePlayerBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "textures/models/block/cassette_player.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CassettePlayerBlockEntity CassettePlayerBlockEntity) {
        return new ResourceLocation(fnfmain.MOD_ID, "animations/cassette_player.animation.json");
    }
}