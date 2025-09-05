package org.fnf.fnfutil.client.script;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.ChatFormatting;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class ScriptManager {
    private final File scriptFolder;
    private final Map<String, Integer> speakerColors = new HashMap<>();
    private final List<ScriptLine> lines = new ArrayList<>();
    private List<String> availableScripts = new ArrayList<>();
    private int currentScriptIndex = 0;

    // Replace with your actual folder ID and API key
    private static final String DRIVE_FOLDER_ID = "1Cr-g2tKtD-dcyCoXIyuGk1LxAhnT6X2S";
    private static final String API_KEY = "AIzaSyBPWfXdhkzVb2HC8_pVjVi_IZdimvZ6IeU";

    public ScriptManager(File gameDir) {
        this.scriptFolder = new File(gameDir, "fnfutil/scripts");
        if (!scriptFolder.exists()) scriptFolder.mkdirs();
        syncScriptsFromDrive();
        refreshScriptList();
    }

    private void syncScriptsFromDrive() {
        try {
            String queryUrl = "https://www.googleapis.com/drive/v3/files?q='" + DRIVE_FOLDER_ID + "'+in+parents&key=" + API_KEY;
            HttpURLConnection conn = (HttpURLConnection) new URL(queryUrl).openConnection();
            conn.setRequestMethod("GET");

            try (InputStream in = conn.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                JsonObject response = new Gson().fromJson(reader, JsonObject.class);
                JsonArray files = response.getAsJsonArray("files");

                for (JsonElement element : files) {
                    JsonObject file = element.getAsJsonObject();
                    String name = file.get("name").getAsString();
                    String id = file.get("id").getAsString();

                    if (name.toLowerCase().endsWith(".txt")) {
                        File target = new File(scriptFolder, name);
                        if (!target.exists()) {
                            downloadScriptFromDrive(id, target);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[ScriptManager] Failed to sync from Google Drive");
            e.printStackTrace();
        }
    }

    private void downloadScriptFromDrive(String fileId, File target) {
        try {
            URL url = new URL("https://drive.google.com/uc?export=download&id=" + fileId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream in = conn.getInputStream();
                 FileOutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("[ScriptManager] Downloaded: " + target.getName());
        } catch (Exception e) {
            System.out.println("[ScriptManager] Failed to download: " + target.getName());
            e.printStackTrace();
        }
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
