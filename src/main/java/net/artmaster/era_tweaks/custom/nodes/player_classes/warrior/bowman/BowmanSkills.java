package net.artmaster.era_tweaks.custom.nodes.player_classes.warrior.bowman;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.gui.SkillButton;
import net.artmaster.era_tweaks.custom.gui.SkillCondition;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.artmaster.era_tweaks.custom.nodes.SkillChecksManager.isCompR;


//МЕЖДУ ВЗАИМОИСКЛЮЧАЮЩИМИ - 60 разницы
//МЕЖДУ 2-мя ДОЧЕРНИМИ К ВЗАИМОИСКЛЮЧАЮЩИМ - 90
public class BowmanSkills {

    public static List<SkillButton> getNodes(ResourceLocation BUTTON_TEXTURE, LocalPlayer player, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }

        nodes.add(new SkillButton(
                -16, -200, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_1"),
                "bowman_skill_1",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_1"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("2"))
                                .withStyle(isCompR(2, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("3"))
                                .withStyle(isCompR(3, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.warrior_class"))
                                .withStyle(data.getPlayerClass().equals("warrior") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.AUTOLOADER_CROSSBOW,
                btn -> {
                    if (!data.getPlayerClass().equals("warrior")) {
                        return;
                    }
                    if (!data.getPlayerSubClass().equals("unknown")) {
                        return;
                    }
                    if (!isCompR(2, "body", sAttributesData)) {
                        return;
                    }
                    if (!isCompR(3, "intellect", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("bowman_skill_1", 1);
                    Network.toServerAction("bowman", 3);
                }
        ));

        nodes.add(new SkillButton(
                -116, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_2_1"),
                "bowman_skill_2_1",
                new SkillCondition(List.of("bowman_skill_1"), true),
                "bowman_skill_2_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_2_1"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_2_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.bowman_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("alchemist") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.BOW,
                btn -> {
                    if (!data.getPlayerSubClass().equals("alchemist")) return; // Проверка на подкласс
                    if (!isCompR(10, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("bowman_skill_2_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                84, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_2_2"),
                "bowman_skill_2_2",
                new SkillCondition(List.of("bowman_skill_1"), true),
                "bowman_skill_2_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_2_2"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_2_2_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.bowman_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("alchemist") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.CROSSBOW,
                btn -> {
                    if (!data.getPlayerSubClass().equals("alchemist")) return; // Проверка на подкласс
                    if (!isCompR(10, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("bowman_skill_2_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -16, -80, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_3"),
                "bowman_skill_3",
                new SkillCondition(List.of("bowman_skill_2_1", "bowman_skill_2_2"), false),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_3"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_3_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("20"))
                                .withStyle(isCompR(20, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.bowman_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("alchemist") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.COOKED_RABBIT,
                btn -> {
                    if (!data.getPlayerSubClass().equals("alchemist")) return; // Проверка на подкласс
                    if (!isCompR(20, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("bowman_skill_3", 1);
                }
        ));
        nodes.add(new SkillButton(
                -16, 10, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_4"),
                "bowman_skill_4",
                new SkillCondition(List.of("bowman_skill_3"), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_4"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_4_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("30"))
                                .withStyle(isCompR(30, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.bowman_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("alchemist") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.SPLASH_POTION,
                btn -> {
                    if (!data.getPlayerSubClass().equals("alchemist")) return; // Проверка на подкласс
                    if (!isCompR(30, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("bowman_skill_4", 1);
                }
        ));
        nodes.add(new SkillButton(
                -16, 100, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.bowman_skill_5"),
                "bowman_skill_5",
                new SkillCondition(List.of("bowman_skill_4"), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.bowman_skill_5"),
                        Component.translatable("tooltip.era_tweaks.bowman_skill_5_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("35"))
                                .withStyle(isCompR(35, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.bowman_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("alchemist") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.TIPPED_ARROW,
                btn -> {
                    if (!data.getPlayerSubClass().equals("alchemist")) return; // Проверка на подкласс
                    if (!isCompR(35, "intellect", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(15, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("bowman_skill_5", 1);
                }
        ));



        return nodes;
    }


}
