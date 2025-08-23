package org.fnf.fnfutil.client.renderer;


import org.fnf.fnfutil.client.model.CassettePlayerItemModel;
import org.fnf.fnfutil.item.custom.CassettePlayerItem;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class CassettePlayerItemRenderer extends GeoItemRenderer<CassettePlayerItem> {
    public CassettePlayerItemRenderer() {
        super(new CassettePlayerItemModel());
    }
}