package net.artmaster.era_tweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BlockXpConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File("config/era_tweaks/block_xp_multipliers.json");

    public static final Map<String, Double> blockMultipliers = new HashMap<>();

    public static void load() {
        try {
            if (!configFile.exists()) {
                createDefault();
            }

            FileReader reader = new FileReader(configFile);
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            reader.close();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                blockMultipliers.put(entry.getKey(), entry.getValue().getAsDouble());
            }

            LOGGER.info("Загружено " + blockMultipliers.size() + " множителей блоков.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDefault() {
        try {
            Files.createDirectories(configFile.getParentFile().toPath());

            JsonObject json = new JsonObject();




            for (Block block : BuiltInRegistries.BLOCK.stream().toList()) {
                ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
                if (key != null) {
                    json.addProperty(key.toString(), 1.0);
                }
            }

            FileWriter writer = new FileWriter(configFile);
            gson.toJson(json, writer);
            writer.flush();
            writer.close();

            LOGGER.info("Создан стандартный файл конфигурации block_xp_multipliers.json с множителями 1.0.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getMultiplier(String blockId) {
        return blockMultipliers.getOrDefault(blockId, 1.0);
    }
}
