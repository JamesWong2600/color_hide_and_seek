package org.cat.cat_run_run.data_processing;

import com.google.gson.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.cat.cat_run_run.mojangapi.MojangAPI.getUUID;

public class hunter_manager {
    // We store UUIDs in the set because names can change, but UUIDs are forever.
    private static final Set<String> hunterUUIDs = new HashSet<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void loadHunters(File dataFolder) {
        File file = new File(dataFolder, "hunters.json");
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("hunters")) {
                JsonArray array = json.getAsJsonArray("hunters");
                hunterUUIDs.clear();
                for (JsonElement element : array) {
                    hunterUUIDs.add(element.getAsJsonObject().get("uuid").getAsString());
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void addHunter(String name, File dataFolder) {
        // Use your existing getUUID method
        String uuid = getUUID(name);
        if (uuid != null) {
            hunterUUIDs.add(uuid);
            save(dataFolder);
        }
    }

    public static void removeHunter(String name, File dataFolder) {
        String uuid = getUUID(name);
        if (uuid != null) {
            hunterUUIDs.remove(uuid);
            save(dataFolder);
        }
    }

    public static void save(File dataFolder) {
        File file = new File(dataFolder, "hunters.json");
        JsonObject root = new JsonObject();
        JsonArray array = new JsonArray();

        for (String uuidStr : hunterUUIDs) {
            JsonObject playerObj = new JsonObject();

            // Look up the name from Bukkit's cache using the UUID
            // We use Bukkit here because calling Mojang API in a loop is too slow
            String actualName = org.bukkit.Bukkit.getOfflinePlayer(UUID.fromString(uuidStr)).getName();

            playerObj.addProperty("name", actualName != null ? actualName : "Unknown");
            playerObj.addProperty("uuid", uuidStr);
            array.add(playerObj);
        }

        root.add("hunters", array);
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(root, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static boolean isHunter(String playeruuid){
        return playeruuid != null && hunterUUIDs.contains(playeruuid);
    }
}