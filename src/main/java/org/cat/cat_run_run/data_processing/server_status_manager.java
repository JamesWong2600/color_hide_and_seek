package org.cat.cat_run_run.data_processing;

import com.google.gson.*;
import org.cat.cat_run_run.variable.variable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public class server_status_manager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void loadStatus(File dataFolder, Logger logger) {
        File file = new File(dataFolder, "server_status.json");
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JsonElement element = JsonParser.parseReader(reader);

            if (element == null || element.isJsonNull() || !element.isJsonObject()) {
                logger.warning("server_status.json is invalid!");
                return;
            }

            JsonObject json = element.getAsJsonObject();

            // Load the global variables back into your 'variable' class
            if (json.has("session")) {
                variable.games_session = json.get("session").getAsInt();
            }
            if (json.has("time")) {
                variable.secondsElapsed = json.get("time").getAsInt();
            }
            if (json.has("countdown")) {
                variable.delay_cooldown = json.get("countdown").getAsInt();
            }

            logger.info("[Status] Loaded session: " + variable.games_session);

        } catch (Exception e) {
            logger.severe("Could not load server_status.json: " + e.getMessage());
        }
    }

    public static void save(File dataFolder) {
        File file = new File(dataFolder, "server_status.json");
        JsonObject root = new JsonObject();

        // Save the current values from your 'variable' class
        root.addProperty("session", variable.games_session);
        root.addProperty("time", variable.secondsElapsed);
        root.addProperty("countdown", variable.delay_cooldown);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
