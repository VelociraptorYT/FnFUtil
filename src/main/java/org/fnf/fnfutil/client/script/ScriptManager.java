package org.fnf.fnfutil.client.script;

import net.minecraft.ChatFormatting;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class ScriptManager {
    private final File scriptFolder;
    private final Map<String, Integer> speakerColors = new HashMap<>();
    private final List<ScriptLine> lines = new ArrayList<>();
    private List<String> availableScripts = new ArrayList<>();
    private int currentScriptIndex = 0;

    public ScriptManager(File gameDir) {
        this.scriptFolder = new File(gameDir, "fnfutil/scripts");
        if (!scriptFolder.exists()) scriptFolder.mkdirs();
        refreshScriptList();
    }

    public void refreshScriptList() {
        File[] files = scriptFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files != null) {
            availableScripts = Arrays.stream(files)
                    .map(File::getName)
                    .sorted()
                    .toList();
        }
    }

    public List<String> getAvailableScripts() {
        return availableScripts;
    }

    public String getCurrentScriptName() {
        return availableScripts.isEmpty() ? "" : availableScripts.get(currentScriptIndex);
    }

    public void nextScript() {
        if (!availableScripts.isEmpty()) {
            currentScriptIndex = (currentScriptIndex + 1) % availableScripts.size();
            try {
                loadScript(getCurrentScriptName());
            } catch (IOException ignored) {}
        }
    }

    public List<ScriptLine> getLines() {
        return lines;
    }

    public void loadScript(String filename) throws IOException {
        speakerColors.clear();
        lines.clear();

        File file = new File(scriptFolder, filename);
        List<String> raw = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        boolean parsingTable = true;

        for (String line : raw) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (parsingTable && line.startsWith("#")) continue;
            if (parsingTable && line.contains("|")) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String colorStr = parts[2].trim();
                    int color = parseColor(colorStr);
                    speakerColors.put(name, color);
                }
            } else {
                parsingTable = false;
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String speaker = parts[0].trim();
                    String content = parts[1].trim();
                    int color = speakerColors.getOrDefault(speaker, 0xFFFFFF);
                    lines.add(new ScriptLine(speaker, content, color));
                } else {
                    lines.add(new ScriptLine("", line, 0xAAAAAA));
                }
            }
        }
    }

    private int parseColor(String input) {
        try {
            if (input.startsWith("#")) return Integer.parseInt(input.substring(1), 16);
            return ChatFormatting.valueOf(input.toUpperCase()).getColor();
        } catch (Exception e) {
            return 0xFFFFFF;
        }
    }
}
