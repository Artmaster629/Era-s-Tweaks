package net.artmaster.era_tweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeniedByClassConfig {

    public record CancelByClassRule(
            String classStr,
            String subclassStr,
            List<String> requiredSkills
    ) {}
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File("config/era_tweaks/denied_by_class_config.json");







    public static final Map<String, CancelByClassRule> deniedMap = new HashMap<>();


    public static Map<String, CancelByClassRule> getList() {
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

                String classStr = obj.get("class").getAsString();
                String subclassStr = obj.get("subclass").getAsString();
                List<String> requiredSkills = gson.fromJson(
                        obj.getAsJsonArray("required_skills"),
                        STRING_LIST
                );


                deniedMap.put(
                        entry.getKey(),
                        new CancelByClassRule(classStr, subclassStr, requiredSkills)
                );
            }

            LOGGER.info("Загружено {} denied-правил", deniedMap.size());
        } catch (Exception e) {
            LOGGER.error("Ошибка загрузки denied_by_class_config.json: ", e);
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

                rule.addProperty("class", "warrior");
                rule.addProperty("subclass", "");
                rule.add("required_skills", gson.toJsonTree(List.of("subclass_skill_1", "subclass_skill_2")));

                root.add(id.toString(), rule);
            }

            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(root, writer);
            }

            LOGGER.info("Создан стандартный denied_by_class_config.json");
        } catch (IOException e) {
            LOGGER.error("Ошибка создания denied_by_class_config.json", e);
        }
    }
}
