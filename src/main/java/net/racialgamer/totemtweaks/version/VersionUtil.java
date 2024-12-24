package net.racialgamer.totemtweaks.version;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class VersionUtil {
    public static TTVersion getLocalVersion() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer("totemtweaks");
        if (modContainer.isPresent()) {
            String version = modContainer.get().getMetadata().getVersion().getFriendlyString();
            return TTVersion.fromString(version);
        } else {
            return null;
        }
    }

    public static TTVersion fetchLatestGitHubVersion() {
        try {
            URLConnection connection = new URL("https://api.github.com/repos/RacialGamer/Totem-Tweaks/releases/latest").openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonResponse = reader.readLine();
            reader.close();
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
            return TTVersion.fromString(jsonObject.get("tag_name").getAsString().replaceFirst("^[vV]", ""));
        } catch (IOException e) {
            return null;
        }
    }
}
