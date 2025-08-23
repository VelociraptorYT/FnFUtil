package org.fnf.fnfutil.client.renderer;


import org.fnf.fnfutil.client.model.CoffeeItemModel;
import org.fnf.fnfutil.item.custom.CoffeeItem;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class CoffeeItemRenderer extends GeoItemRenderer<CoffeeItem> {
    public CoffeeItemRenderer() {
        super(new CoffeeItemModel());
    }
}
