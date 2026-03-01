package org.cat.cat_run_run.data_processing;

import com.google.gson.*;
import org.bukkit.entity.Player;
import org.eclipse.aether.util.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class player_status_manager {
    private static final Map<String, String> playerColors = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void loadColors(File dataFolder) {
        File file = new File(dataFolder, "player_colors.json");
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JsonElement element = JsonParser.parseReader(reader);

            // CHECK: If file is empty or null, stop here
            if (element == null || element.isJsonNull() || !element.isJsonObject()) {
                System.out.println("[Colors] player_colors.json is empty or invalid. Skipping load.");
                return;
            }

            JsonObject json = element.getAsJsonObject();
            if (json.has("colors")) {
                JsonObject colorsObj = json.getAsJsonObject("colors");
                playerColors.clear();
                for (String uuid : colorsObj.keySet()) {
                    playerColors.put(uuid, colorsObj.get(uuid).getAsString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerColor(Player player, String colorName, File dataFolder) {
        playerColors.put(player.getUniqueId().toString(), colorName.toUpperCase());
        save(dataFolder);
    }

    public static String getPlayerColor(Player player) {
        return playerColors.getOrDefault(player.getUniqueId().toString(), "NONE");
    }

    public static void save(File dataFolder) {
        File file = new File(dataFolder, "player_colors.json");
        JsonObject root = new JsonObject();
        JsonObject colorsObj = new JsonObject();

        for (Map.Entry<String, String> entry : playerColors.entrySet()) {
            colorsObj.addProperty(entry.getKey(), entry.getValue());
        }

        root.add("colors", colorsObj);
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(root, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
