package net.artmaster.era_tweaks.custom.player_classes.assistant.smith;

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
public class SmithSkills {

    public static List<SkillButton> getNodes(ResourceLocation BUTTON_TEXTURE, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }
        nodes.add(new SkillButton(
                -480, -200, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_1"),
                "smith_skill_1",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_1"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_1_desc"),
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
                                .append(Component.translatable("text.era_tweaks.assistant_class"))
                                .withStyle(data.getPlayerClass().equals("assistant") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.FIRE_RUNE,
                btn -> {
                    if (!data.getPlayerClass().equals("assistant")) {
                        return;
                    }
                    if (!data.getPlayerSubClass().equals("unknown")) {
                        return;
                    }
                    if (!isCompR(5, "body", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("smith_skill_1", 1);
                    Network.toServerAction("smith", 3);
                }
        ));

        nodes.add(new SkillButton(
                -580, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_2_1"),
                "smith_skill_2_1",
                new SkillCondition(List.of("smith_skill_1"), true),
                "smith_skill_2_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_2_1"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_2_1_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.HITHER_THITHER_WAND,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; // Проверка на подкласс
                    if (!isCompR(10, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_2_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, -140, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_2_2"),
                "smith_skill_2_2",
                new SkillCondition(List.of("smith_skill_1"), true),
                "smith_skill_2_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_2_2"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_2_2_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.NETHERITE_MAGE_HELMET,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; // Проверка на подкласс
                    if (!isCompR(5, "intellect", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(7, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_2_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -580, -50, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_3_1"),
                "smith_skill_3_1",
                new SkillCondition(List.of("smith_skill_2_1", "smith_skill_2_2"), false),
                "smith_skill_3_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_3_1"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_3_1_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.CINDEROUS_SOULCALLER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; //Проверка на подкласс
                    if (!isCompR(20, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_3_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, -50, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_3_2"),
                "smith_skill_3_2",
                new SkillCondition(List.of("smith_skill_2_1", "smith_skill_2_2"), false),
                "smith_skill_3_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_3_2"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_3_2_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.CINDEROUS_SOULCALLER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; //Проверка на подкласс
                    if (!isCompR(20, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_3_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -480, 10, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_4"),
                "smith_skill_4",
                new SkillCondition(List.of("smith_skill_3_1", "smith_skill_3_2"), false),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_4"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_4_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.CINDEROUS_SOULCALLER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; //Проверка на подкласс
                    if (!isCompR(20, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_4", 1);
                }
        ));

        nodes.add(new SkillButton(
                -480, 100, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.smith_skill_5"),
                "smith_skill_5",
                new SkillCondition(List.of("smith_skill_4"), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.smith_skill_5"),
                        Component.translatable("tooltip.era_tweaks.smith_skill_5_desc"),
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
                                .append(Component.translatable("text.era_tweaks.smith_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("smith") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.CINDEROUS_SOULCALLER,
                btn -> {
                    if (!data.getPlayerSubClass().equals("smith")) return; //Проверка на подкласс
                    if (!isCompR(20, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("smith_skill_5", 1);
                }
        ));

        return nodes;
    }
}
