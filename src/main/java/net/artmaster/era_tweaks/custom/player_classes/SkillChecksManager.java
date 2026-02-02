package net.artmaster.era_tweaks.custom.player_classes;

import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.gui.SkillButton;
import net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist.AlchemistSkills;
import net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant.CommonAssistantSkills;
import net.artmaster.era_tweaks.custom.player_classes.assistant.smith.SmithSkills;
import net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.BowmanSkills;
import net.artmaster.era_tweaks.custom.player_classes.warrior.common_warrior.CommonWarriorSkills;
import net.artmaster.era_tweaks.custom.player_classes.warrior.paladin.PaladinSkills;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.ScoutSkills;
import net.artmaster.era_tweaks.custom.player_classes.wizard.necromancer.NecromancerSkills;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.PriestSkills;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillChecksManager {
    public static boolean isCompR(int requiredLevel, String requiredAtt, PlayerSAttrubitesData sAttributesData) {
        boolean output = false;

        Map<String, Integer> playerLevels = Map.of(
                "intellect", sAttributesData.getIntellectLevel(),
                "body", sAttributesData.getBodyLevel(),
                "society", sAttributesData.getSocietyLevel()
        );
        int playerLevel = playerLevels.getOrDefault(requiredAtt, 0);

        if (playerLevel >= requiredLevel) {
            output = true;
        }

        return output;
    }

    public static Component getTitleClass(int page) {
        Map<Integer, String> titleClasses = Map.of(
                1, "text.era_tweaks.warrior_class",
                2, "text.era_tweaks.wizard_class",
                3, "text.era_tweaks.assistant_class"
        );
        return Component.translatable(titleClasses.getOrDefault(page, "text.era_tweaks.unknown_class"));
    }

    public static List<SkillButton> getNodes(int page, ResourceLocation BUTTON_TEXTURE, LocalPlayer player, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }

        if (page == 1) {
            List<SkillButton> commonWarriorSkills = CommonWarriorSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(commonWarriorSkills);

            List<SkillButton> paladinSkills = PaladinSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(paladinSkills);

            List<SkillButton> bowmanSkills = BowmanSkills.getNodes(BUTTON_TEXTURE, player, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(bowmanSkills);

            List<SkillButton> scoutSkills = ScoutSkills.getNodes(BUTTON_TEXTURE, player, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(scoutSkills);
        } else if (page == 2) {
            List<SkillButton> necromancerSkills = NecromancerSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(necromancerSkills);

            List<SkillButton> priestSkills = PriestSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(priestSkills);
        }
        else if (page == 3) {
            List<SkillButton> commonAssistantSkills = CommonAssistantSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(commonAssistantSkills);

            List<SkillButton> smithSkills = SmithSkills.getNodes(BUTTON_TEXTURE, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(smithSkills);

            List<SkillButton> alchemistSkills = AlchemistSkills.getNodes(BUTTON_TEXTURE, player, data, sAttributesData, buttonWidth, buttonHeight);
            nodes.addAll(alchemistSkills);
        }

        return nodes;
    }

}
