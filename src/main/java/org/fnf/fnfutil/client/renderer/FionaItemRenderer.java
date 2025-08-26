package org.fnf.fnfutil.client.renderer;

import org.fnf.fnfutil.client.model.FionaItemModel;
import org.fnf.fnfutil.item.custom.FionaItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class FionaItemRenderer extends GeoItemRenderer<FionaItem> {
    public FionaItemRenderer() {
        super(new FionaItemModel());
    }
}
