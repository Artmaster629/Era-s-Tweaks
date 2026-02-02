package net.artmaster.era_tweaks.custom.player_classes.warrior.paladin;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
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



//МЕЖДУ ВЗАИМОИСКЛЮЧАЮЩИМИ - 60 разницы
//МЕЖДУ 2-мя ДОЧЕРНИМИ К ВЗАИМОИСКЛЮЧАЮЩИМ - 90
public class PaladinSkills {

    public static List<SkillButton> getNodes(ResourceLocation BUTTON_TEXTURE, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }
        nodes.add(new SkillButton(
                -480, -200, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_1"),
                "paladin_skill_1",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_1"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("5"))
                                .withStyle(isCompR(5, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.warrior_class"))
                                .withStyle(data.getPlayerClass().equals("warrior") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.FIRE_RUNE,
                btn -> {
                    if (!data.getPlayerClass().equals("warrior")) {
                        return;
                    }
                    if (!data.getPlayerSubClass().equals("unknown")) {
                        return;
                    }
                    if (!isCompR(5, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("paladin_skill_1", 1);
                    Network.toServerAction("paladin", 3);
                }
        ));

        nodes.add(new SkillButton(
                -580, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_2_1"),
                "paladin_skill_2_1",
                new SkillCondition(List.of("paladin_skill_1"), true),
                "paladin_skill_2_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_2_1"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_2_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),

                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.HITHER_THITHER_WAND,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; // Проверка на подкласс
                    if (!isCompR(10, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_2_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_2_2"),
                "paladin_skill_2_2",
                new SkillCondition(List.of("paladin_skill_1"), true),
                "paladin_skill_2_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_2_2"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_2_2_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("7"))
                                .withStyle(isCompR(7, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("5"))
                                .withStyle(isCompR(5, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.NETHERITE_MAGE_HELMET,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; // Проверка на подкласс
                    if (!isCompR(5, "intellect", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(7, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_2_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -480, -80, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_3"),
                "paladin_skill_3",
                new SkillCondition(List.of("paladin_skill_2_1", "paladin_skill_2_2"), false),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_3"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_3_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("20"))
                                .withStyle(isCompR(20, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.CINDEROUS_SOULCALLER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; //Проверка на подкласс
                    if (!isCompR(20, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_3", 1);
                }
        ));

        nodes.add(new SkillButton(
                -580, -20, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_4_1"),
                "paladin_skill_4_1",
                new SkillCondition(List.of("paladin_skill_3"), true),
                "paladin_skill_4_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_4_1"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_4_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("25"))
                                .withStyle(isCompR(25, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),

                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.TORCHFLOWER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; //Проверка на подкласс
                    if (!isCompR(25, "body", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(10, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_4_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, -20, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_4_2"),
                "paladin_skill_4_2",
                new SkillCondition(List.of("paladin_skill_3"), true),
                "paladin_skill_4_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_4_2"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_4_2_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("25"))
                                .withStyle(isCompR(25, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),

                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.FIRE_CHARGE,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; //Проверка на подкласс
                    if (!isCompR(25, "body", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(15, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_4_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -580, 70, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_5_1"),
                "paladin_skill_5_1",
                new SkillCondition(List.of("paladin_skill_4_1", "paladin_skill_4_2"), false),
                "paladin_skill_5_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_5_1"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_5_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("35"))
                                .withStyle(isCompR(35, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.PYRIUM_STAFF,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; //Проверка на подкласс
                    if (!isCompR(35, "body", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(15, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_5_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, 70, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.paladin_skill_5_2"),
                "paladin_skill_5_2",
                new SkillCondition(List.of("paladin_skill_4_1", "paladin_skill_4_2"), false),
                "paladin_skill_5_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.paladin_skill_5_2"),
                        Component.translatable("tooltip.era_tweaks.paladin_skill_5_2_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("30"))
                                .withStyle(isCompR(30, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("20"))
                                .withStyle(isCompR(20, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.paladin_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("paladin") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.PALADIN_CHESTPLATE,
                btn -> {
                    if (!data.getPlayerSubClass().equals("paladin")) return; //Проверка на подкласс
                    if (!isCompR(30, "body", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(20, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("paladin_skill_5_2", 1);
                }
        ));

        return nodes;
    }
}
