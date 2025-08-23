package org.fnf.fnfutil.client.renderer;

import org.fnf.fnfutil.client.model.BonnieEarsModel;
import org.fnf.fnfutil.item.custom.BonnieEarsItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BonnieEarsRenderer extends GeoArmorRenderer<BonnieEarsItem> {
    public BonnieEarsRenderer() {
        super(new BonnieEarsModel());

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