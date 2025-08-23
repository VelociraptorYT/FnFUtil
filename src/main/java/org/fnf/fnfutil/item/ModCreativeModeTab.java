package org.fnf.fnfutil.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab FNFUTIL = new CreativeModeTab("fnfutil") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BONNIE_EARS.get());
        }
    };
}