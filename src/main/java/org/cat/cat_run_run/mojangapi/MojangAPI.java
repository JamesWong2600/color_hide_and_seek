package org.cat.cat_run_run.mojangapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MojangAPI {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String getOfflineUUID(String playerName) {
        // Minecraft offline UUIDs are MD5 hashes of "OfflinePlayer:PlayerName"
        String offlineName = "OfflinePlayer:" + playerName;
        return String.valueOf(UUID.nameUUIDFromBytes(offlineName.getBytes(StandardCharsets.UTF_8)));
    }

    public static String getUUID(String name) {
        CompletableFuture.supplyAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + name))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                    String id = json.get("id").getAsString();

                    // Mojang returns UUIDs without dashes (undashed)
                    // We need to insert them to make it a valid Java UUID
                    return id.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5"
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null; // Player not found or API error
        });
        return null;
    }
}