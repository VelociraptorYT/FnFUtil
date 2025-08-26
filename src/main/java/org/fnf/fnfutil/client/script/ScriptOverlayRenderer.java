package org.fnf.fnfutil.client.script;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static org.fnf.fnfutil.fnfmain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ScriptOverlayRenderer {
    public static boolean visible = false;
    public static int scrollIndex = 0;
    public static ScriptManager manager;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!visible || manager == null || event.getOverlay().id().equals("chat")) return;

        Minecraft mc = Minecraft.getInstance();
        PoseStack stack = event.getPoseStack();
        List<ScriptLine> lines = manager.getLines();

        // Determine longest visible line
        int maxWidth = 0;
        for (int i = scrollIndex; i < Math.min(lines.size(), scrollIndex + 7); i++) {
            ScriptLine line = lines.get(i);
            String text = line.speaker.isEmpty() ? line.content : line.speaker + ": " + line.content;
            int lineWidth = mc.font.width(text);
            if (lineWidth > maxWidth) maxWidth = lineWidth;
        }

        int width = maxWidth + 10;
        int height = 100;
        int x = (mc.getWindow().getGuiScaledWidth() - width) / 2;
        int y = 20;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //GuiComponent.fill(stack, x - 5, y - 5, x + width + 5, y + height + 5, 0xAA000000);
        RenderSystem.disableBlend();

        mc.font.draw(stack, "Script: " + manager.getCurrentScriptName(), x, y - 12, 0xFFFFFF);

        for (int i = scrollIndex; i < Math.min(lines.size(), scrollIndex + 7); i++) {
            ScriptLine line = lines.get(i);
            String text = line.speaker.isEmpty() ? line.content : line.speaker + ": " + line.content;
            mc.font.draw(stack, text, x, y, line.color);
            y += 12;
        }
    }
}
