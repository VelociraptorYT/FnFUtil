package org.fnf.fnfutil.client.renderer;

import org.fnf.fnfutil.client.model.CoffeeMachineItemModel;
import org.fnf.fnfutil.item.custom.CoffeeMachineItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class CoffeeMachineItemRenderer extends GeoItemRenderer<CoffeeMachineItem> {
    public CoffeeMachineItemRenderer() {
        super(new CoffeeMachineItemModel());
    }
}
