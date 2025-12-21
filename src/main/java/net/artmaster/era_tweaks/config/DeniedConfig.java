package net.artmaster.era_tweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeniedConfig {

    public record CancelRule(
            List<String> requiredAttributes,
            List<Integer> requiredLevels,
            String requiredSkill
    ) {}
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File("config/era_tweaks/denied_config.json");







    public static final Map<String, CancelRule> deniedMap = new HashMap<>();


    public static Map<String, CancelRule> getList() {
        return deniedMap;
    }

    public static void load() {
        try {
            if (!configFile.exists()) {
                createDefault();
            }

            JsonObject root;
            try (FileReader reader = new FileReader(configFile)) {
                root = gson.fromJson(reader, JsonObject.class);
            }

            deniedMap.clear();

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                JsonObject obj = entry.getValue().getAsJsonObject();

                Type STRING_LIST = new TypeToken<List<String>>(){}.getType();
                Type INT_LIST = new TypeToken<List<Integer>>(){}.getType();

                List<String> attributes = gson.fromJson(
                        obj.getAsJsonArray("required_attributes"),
                        STRING_LIST
                );

                List<Integer> levels = gson.fromJson(
                        obj.getAsJsonArray("required_levels"),
                        INT_LIST
                );

                String skill = obj.get("required_skill").getAsString();

                deniedMap.put(
                        entry.getKey(),
                        new CancelRule(attributes, levels, skill)
                );
            }

            LOGGER.info("Загружено {} denied-правил", deniedMap.size());
        } catch (Exception e) {
            LOGGER.error("Ошибка загрузки denied_config.json", e);
        }
    }

    private static void createDefault() {
        try {
            Files.createDirectories(configFile.getParentFile().toPath());

            JsonObject root = new JsonObject();


            for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                if (id == null) continue;
                JsonObject rule = new JsonObject();

                rule.add("required_attributes", gson.toJsonTree(List.of("fight")));
                rule.add("required_levels", gson.toJsonTree(List.of(1)));
                rule.addProperty("required_skill", "skill1");

                root.add(id.toString(), rule);
            }

            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(root, writer);
            }

            LOGGER.info("Создан стандартный denied_config.json");
        } catch (IOException e) {
            LOGGER.error("Ошибка создания denied_config.json", e);
        }
    }
}
