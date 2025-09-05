package org.fnf.fnfutil.client.renderer;

import org.fnf.fnfutil.client.model.BonnieEarsModel;
import org.fnf.fnfutil.client.model.CowboyHatModel;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import org.fnf.fnfutil.item.custom.CowboyHatItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class CowboyHatRenderer extends GeoArmorRenderer<CowboyHatItem> {
    public CowboyHatRenderer() {
        super(new CowboyHatModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}