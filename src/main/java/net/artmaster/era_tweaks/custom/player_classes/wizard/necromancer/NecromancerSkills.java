package net.artmaster.era_tweaks.custom.player_classes.wizard.necromancer;

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
public class NecromancerSkills {

    public static List<SkillButton> getNodes(ResourceLocation BUTTON_TEXTURE, PlayerClassData data, PlayerSAttrubitesData sAttributesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = new ArrayList<>();
        if (!nodes.isEmpty()) {
            nodes.clear();
        }


        nodes.add(new SkillButton(
                -480, -200, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_1"),
                "necromancer_skill_1",
                new SkillCondition(List.of(), true),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_1"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("1"))
                                .withStyle(isCompR(1, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("4"))
                                .withStyle(isCompR(4, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.class"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.wizard_class"))
                                .withStyle(data.getPlayerClass().equals("wizard") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.is_enough_skills"))
                                .withStyle(data.getPlayerSkills().size() >= 3 ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.NETHERITE_BOOTS,
                btn -> {
                    if (!data.getPlayerClass().equals("wizard")) {
                        return;
                    }
                    if (!data.getPlayerSubClass().equals("unknown")) {
                        return;
                    }
                    if (!isCompR(4, "body", sAttributesData)) {
                        return;
                    }
                    if (!isCompR(1, "intellect", sAttributesData)) {
                        return;
                    }
                    if (data.getPlayerSkills().size() < 3) {return;}
                    Network.toServerAction("necromancer_skill_1", 1);
                    Network.toServerAction("necromancer", 3);
                }
        ));

        nodes.add(new SkillButton(
                -480, -110, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_2"),
                "necromancer_skill_2",
                new SkillCondition(List.of("necromancer_skill_1"), true),
                "necromancer_skill_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_2"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_2_desc"),
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
                                .append(Component.translatable("text.era_tweaks.necromancer_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("necromancer") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.HELLRAZOR,

                btn -> {
                    if (!data.getPlayerSubClass().equals("necromancer")) return; // Проверка на подкласс
                    if (!isCompR(10, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("necromancer_skill_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -480, -20, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_3"),
                "necromancer_skill_3",
                new SkillCondition(List.of("necromancer_skill_2"), true),
                "necromancer_skill_3",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_3"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_3_desc"),
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
                                .append(Component.translatable("text.era_tweaks.necromancer_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("necromancer") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.HELLRAZOR,

                btn -> {
                    if (!data.getPlayerSubClass().equals("necromancer")) return; // Проверка на подкласс
                    if (!isCompR(10, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("necromancer_skill_3", 1);
                }
        ));



        nodes.add(new SkillButton(
                -580, 40, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_4_1"),
                "necromancer_skill_4_1",
                new SkillCondition(List.of("necromancer_skill_3"), false),
                "necromancer_skill_4_2",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_4_1"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_4_1_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("7"))
                                .withStyle(isCompR(7, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.necromancer_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("necromancer") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.NATURE_UPGRADE_ORB,
                btn -> {
                    if (!data.getPlayerSubClass().equals("necromancer")) return; // Проверка на подкласс
                    if (!isCompR(7, "intellect", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(15, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("necromancer_skill_4_1", 1);
                }
        ));

        nodes.add(new SkillButton(
                -380, 40, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_4_2"),
                "necromancer_skill_4_2",
                new SkillCondition(List.of("necromancer_skill_3"), false),
                "necromancer_skill_4_1",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_4_2"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_4_2_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("7"))
                                .withStyle(isCompR(7, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("15"))
                                .withStyle(isCompR(15, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.necromancer_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("necromancer") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                ItemRegistry.NATURE_UPGRADE_ORB,
                btn -> {
                    if (!data.getPlayerSubClass().equals("necromancer")) return; // Проверка на подкласс
                    if (!isCompR(7, "intellect", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(15, "body", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("necromancer_skill_4_2", 1);
                }
        ));

        nodes.add(new SkillButton(
                -480, 100, buttonWidth, buttonHeight,
                BUTTON_TEXTURE,
                Component.translatable("skill.era_tweaks.necromancer_skill_5"),
                "necromancer_skill_5",
                new SkillCondition(List.of("necromancer_skill_4_1", "necromancer_skill_4_2"), false),
                "none",
                () -> List.of(
                        Component.translatable("skill.era_tweaks.necromancer_skill_5"),
                        Component.translatable("tooltip.era_tweaks.necromancer_skill_5_desc"),
                        Component.literal(" "),
                        Component.translatable("tooltip.era_tweaks.skill_requirements"),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.body"))
                                .append(Component.literal(": "))
                                .append(Component.literal("40"))
                                .withStyle(isCompR(40, "body", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("sattribute.era_tweaks.intellect"))
                                .append(Component.literal(": "))
                                .append(Component.literal("10"))
                                .withStyle(isCompR(10, "intellect", sAttributesData) ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("• ")
                                .append(Component.translatable("text.era_tweaks.subclass"))
                                .append(Component.literal(": "))
                                .append(Component.translatable("text.era_tweaks.necromancer_subclass"))
                                .withStyle(data.getPlayerSubClass().equals("necromancer") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ),
                Items.NETHER_STAR,
                btn -> {
                    if (!data.getPlayerSubClass().equals("necromancer")) return; // Проверка на подкласс
                    if (!isCompR(40, "body", sAttributesData)) return; //Проверка на атрибут
                    if (!isCompR(10, "intellect", sAttributesData)) return; //Проверка на атрибут
                    Network.toServerAction("necromancer_skill_5", 1);
                }
        ));


        return nodes;
    }


}
