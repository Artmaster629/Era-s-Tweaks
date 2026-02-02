package net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant;

import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.gui.SkillButton;
import net.artmaster.era_tweaks.custom.gui.SkillCondition;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.artmaster.era_tweaks.custom.player_classes.SkillChecksManager.isCompR;

public class CommonAssistantSkills {


    public static List<SkillButton> getNodes(ResourceLocation BUTTON_TEXTURE, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }
        nodes.add(new SkillButton(
                -580, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_1"),
                "common_assistant_skill_1",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("5"))
                                .withStyle(isCompR(5, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(5, "body", sAttributesData)) {
                        return;
                    }
                    Network.toServerAction("common_assistant_skill_1", 1);
                }
        ));
        nodes.add(new SkillButton(
                -480, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_2"),
                "common_assistant_skill_2",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(10, "body", sAttributesData)) {
                        return;
                    }
                    Network.toServerAction("common_assistant_skill_2", 1);
                }
        ));
        nodes.add(new SkillButton(
                -380, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_3"),
                "common_assistant_skill_3",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(15, "body", sAttributesData)) {
                        return;
                    }
                    Network.toServerAction("common_assistant_skill_3", 1);
                }
        ));
        nodes.add(new SkillButton(
                -280, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_4"),
                "common_assistant_skill_4",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(10, "intellect", sAttributesData)) {
                        return;
                    }
                    Network.toServerAction("common_assistant_skill_4", 1);
                }
        ));
        nodes.add(new SkillButton(
                -180, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_5"),
                "common_assistant_skill_5",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("25"))
                                .withStyle(isCompR(25, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(25, "body", sAttributesData)) {
                        return;
                    }
                    Network.toServerAction("common_assistant_skill_5", 1);
                }
        ));

        nodes.add(new SkillButton(
                134, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_6"),
                "common_assistant_skill_6",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("30"))
                                .withStyle(isCompR(30, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(30, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("common_assistant_skill_6", 1);
                }
        ));

        nodes.add(new SkillButton(
                234, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_7"),
                "common_assistant_skill_7",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(15, "intellect", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("common_assistant_skill_7", 1);
                }
        ));

        nodes.add(new SkillButton(
                334, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_8"),
                "common_assistant_skill_8",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("40"))
                                .withStyle(isCompR(40, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(40, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("common_assistant_skill_8", 1);
                }
        ));

        nodes.add(new SkillButton(
                434, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_9"),
                "common_assistant_skill_9",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("45"))
                                .withStyle(isCompR(45, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(45, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("common_assistant_skill_9", 1);
                }
        ));

        nodes.add(new SkillButton(
                534, -300, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.common_assistant_skill_10"),
                "common_assistant_skill_10",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("50"))
                                .withStyle(isCompR(50, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.IRON_AXE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!isCompR(50, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("common_assistant_skill_10", 1);
                }
        ));
        return nodes;
    }


}
