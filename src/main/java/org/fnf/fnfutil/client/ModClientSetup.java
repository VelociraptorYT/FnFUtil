package org.fnf.fnfutil.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.io.File;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import static org.fnf.fnfutil.fnfmain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientSetup {

    public static void createScriptFolder(File gameDir) {
        File scriptFolder = new File(gameDir, "fnfutil/scripts");
        if (!scriptFolder.exists()) {
            boolean created = scriptFolder.mkdirs();
            if (created) {
                System.out.println("[FNFUtil] Created script folder: " + scriptFolder.getAbsolutePath());
            } else {
                System.err.println("[FNFUtil] Failed to create script folder");
            }
        }
    }
    public static final KeyMapping TOGGLE_SCRIPT_OVERLAY = new KeyMapping("key.fnfutil.toggle_script", InputConstants.KEY_O, "key.categories.fnfutil");
    public static final KeyMapping SCROLL_UP = new KeyMapping("key.fnfutil.scroll_up", InputConstants.KEY_UP, "key.categories.fnfutil");
    public static final KeyMapping SCROLL_DOWN = new KeyMapping("key.fnfutil.scroll_down", InputConstants.KEY_DOWN, "key.categories.fnfutil");
    public static final KeyMapping NEXT_SCRIPT = new KeyMapping("key.fnfutil.next_script", InputConstants.KEY_P, "key.categories.fnfutil");



    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_SCRIPT_OVERLAY);
        event.register(SCROLL_UP);
        event.register(SCROLL_DOWN);
        event.register(NEXT_SCRIPT);
    }
}
