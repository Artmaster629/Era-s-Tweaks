package net.artmaster.era_tweaks.network;

import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant.CommonAssistant;
import net.artmaster.era_tweaks.custom.player_classes.assistant.smith.Smith;
import net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.Bowman;
import net.artmaster.era_tweaks.custom.player_classes.warrior.common_warrior.CommonWarrior;
import net.artmaster.era_tweaks.custom.player_classes.warrior.paladin.Paladin;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.Scout;
import net.artmaster.era_tweaks.custom.player_classes.wizard.necromancer.Necromancer;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.Priest;
import net.minecraft.server.level.ServerPlayer;
import java.util.HashMap;
import java.util.Map;

public class ChangesApplier {
    @FunctionalInterface
    public interface Applier {
        void apply(PlayerClassData data, ServerPlayer player);
    }

    private static final Map<String, ChangesApplier.Applier> REGISTRY = new HashMap<>();

    static {
        // warrior
        REGISTRY.put("warrior", CommonWarrior.ApplyChanges::makeChangesToClass);
        REGISTRY.put("paladin", Paladin.ApplyChanges::makeChangesToClass);
        REGISTRY.put("alchemist", Bowman.ApplyChanges::makeChangesToClass);
        REGISTRY.put("scout", Scout.ApplyChanges::makeChangesToClass);

        // wizard
        REGISTRY.put("necromancer", Necromancer.ApplyChanges::makeChangesToClass);
        REGISTRY.put("priest", Priest.ApplyChanges::makeChangesToClass);

        // assistant
        REGISTRY.put("assistant", CommonAssistant.ApplyChanges::makeChangesToClass);
        REGISTRY.put("smith", Smith.ApplyChanges::makeChangesToClass);
    }

    public static void apply(PlayerClassData data, ServerPlayer player, String packet) {
        for (String key : REGISTRY.keySet()) {
            if (packet.contains(key)) {
                ChangesApplier.Applier applier = REGISTRY.get(key);
                if (applier != null) {
                    applier.apply(data, player);
                }
                break;
            }
        }


    }
}
