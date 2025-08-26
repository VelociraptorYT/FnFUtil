package org.fnf.fnfutil.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.fnf.fnfutil.client.ModClientSetup;
import org.fnf.fnfutil.client.script.ScriptManager;
import org.fnf.fnfutil.client.script.ScriptOverlayRenderer;

import java.io.IOException;

import static org.fnf.fnfutil.fnfmain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientForgeEvents {
    private static boolean togglePressed = false;
    private static boolean nextScriptPressed = false;
    private static boolean scrollUpPressed = false;
    private static boolean scrollDownPressed = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        // Toggle overlay
        if (ModClientSetup.TOGGLE_SCRIPT_OVERLAY.isDown()) {
            if (!togglePressed) {
                togglePressed = true;
                ScriptOverlayRenderer.visible = !ScriptOverlayRenderer.visible;

                if (ScriptOverlayRenderer.visible) {
                    if (ScriptOverlayRenderer.manager == null) {
                        ScriptOverlayRenderer.manager = new ScriptManager(mc.gameDirectory);
                    }
                    ScriptOverlayRenderer.manager.refreshScriptList();
                    try {
                        ScriptOverlayRenderer.manager.loadScript(ScriptOverlayRenderer.manager.getCurrentScriptName());
                    } catch (IOException e) {
                        mc.player.sendSystemMessage(
                                net.minecraft.network.chat.Component.literal("Failed to load script")
                        );
                    }
                }
            }
        } else {
            togglePressed = false;
        }

        if (!ScriptOverlayRenderer.visible) return;

        // Scroll up (debounced)
        if (ModClientSetup.SCROLL_UP.isDown()) {
            if (!scrollUpPressed) {
                scrollUpPressed = true;
                ScriptOverlayRenderer.scrollIndex = Math.max(0, ScriptOverlayRenderer.scrollIndex - 1);
            }
        } else {
            scrollUpPressed = false;
        }

        // Scroll down (debounced)
        if (ModClientSetup.SCROLL_DOWN.isDown()) {
            if (!scrollDownPressed) {
                scrollDownPressed = true;
                ScriptOverlayRenderer.scrollIndex = Math.min(
                        ScriptOverlayRenderer.manager.getLines().size() - 1,
                        ScriptOverlayRenderer.scrollIndex + 1
                );
            }
        } else {
            scrollDownPressed = false;
        }

        // Switch script (debounced)
        if (ModClientSetup.NEXT_SCRIPT.isDown()) {
            if (!nextScriptPressed) {
                nextScriptPressed = true;
                ScriptOverlayRenderer.manager.nextScript();
                ScriptOverlayRenderer.scrollIndex = 0;
            }
        } else {
            nextScriptPressed = false;
        }
    }
}
