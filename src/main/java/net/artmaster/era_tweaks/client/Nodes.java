package net.artmaster.era_tweaks.client;

import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.api.gui.SkillButton;
import net.artmaster.era_tweaks.api.gui.SkillCondition;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class Nodes {
    public static List<SkillButton> getNodes(int page, ResourceLocation BUTTON_TEXTURE, LocalPlayer player, PlayerClassData data, PlayerSAttrubitesData sAttrubitesData, int buttonWidth, int buttonHeight) {
        List<SkillButton> nodes = List.of();

        if (page == 1) {
            nodes = List.of(
                    new SkillButton(
                            0, 0, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill1"),
                            "skill1",
                            new SkillCondition(List.of(), true),
                            "none",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" ")
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("paladin")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.paladin_subclass")), false);
                                } else Network.serverDataAction("skill1", 1);
                            }
                    ),
                    new SkillButton(
                            0, 50, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill2"),
                            "skill2",
                            new SkillCondition(List.of(), true),
                            "none",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.building"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("5"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("paladin")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.paladin_subclass")), false);
                                } else {
                                    if (data.getPlayerSkills().contains("skill1")) {
                                        if (sAttrubitesData.getBuildingLevel() >= 5) {
                                            Network.serverDataAction("skill2", 1);
                                        } else {
                                            player.connection.getConnection();
                                            Network.serverDataAction("", 4);
                                            player.displayClientMessage(Component.literal("У вас недостаточный уровень"), false);
                                        }

                                    } else {
                                        player.connection.getConnection();
                                        Network.serverDataAction("", 4);
                                        player.displayClientMessage(Component.literal("Вы не изучили предыдущий навык!"), false);
                                    }
                                }


                            }
                    ),
                    new SkillButton(
                            0, 100, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill3"),
                            "skill3",
                            new SkillCondition(List.of(), true),
                            "none",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.building"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("10"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("paladin")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.paladin_subclass")), false);
                                }
                                if (data.getPlayerSkills().contains("skill2")) {
                                    Network.serverDataAction("skill3", 1);
                                } else {
                                    player.connection.getConnection();
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.literal("Вы не изучили предыдущий навык!"), false);
                                }
                            }
                    )
            );

        }
        else if (page == 2) {
            nodes = List.of(
                    new SkillButton(
                            -140, 50, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill41"),
                            "skill41",
                            new SkillCondition(List.of(), true),
                            "skill42",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.crafting"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("4"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("bowman")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.bowman_subclass")), false);
                                } else {
                                    //Взаимоисключающие навыки
                                    if (!data.getPlayerSkills().contains("skill42")) {
                                        if (sAttrubitesData.getCraftingLevel() >= 4) {
                                            Network.serverDataAction("skill41", 1);
                                        } else {
                                            player.connection.getConnection();
                                            Network.serverDataAction("", 4);
                                            player.displayClientMessage(Component.literal("У вас недостаточный уровень"), false);
                                        }
                                    } else {
                                        player.connection.getConnection();
                                        Network.serverDataAction("", 4);
                                        player.displayClientMessage(Component.literal("Вы уже изучили взаимоисключающий навык!"), false);
                                    }
                                }



                            }
                    ),
                    new SkillButton(
                            -20, 50, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill42"),
                            "skill42",
                            new SkillCondition(List.of(), true),
                            "skill41",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.magic"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("6"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("bowman")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.bowman_subclass")), false);
                                } else {
                                    //Взаимоисключающие навыки
                                    if (!data.getPlayerSkills().contains("skill41")) {
                                        if (sAttrubitesData.getMagicLevel() >= 6) {
                                            Network.serverDataAction("skill42", 1);
                                        } else {
                                            player.connection.getConnection();
                                            Network.serverDataAction("", 4);
                                            player.displayClientMessage(Component.literal("У вас недостаточный уровень"), false);
                                        }
                                    } else {
                                        player.connection.getConnection();
                                        Network.serverDataAction("", 4);
                                        player.displayClientMessage(Component.literal("Вы уже изучили взаимоисключающий навык!"), false);
                                    }
                                }



                            }
                    ),
                    new SkillButton(
                            120, 50, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill43"),
                            "skill43",
                            new SkillCondition(List.of(), true),
                            "none",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.magic"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("6"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("bowman")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.bowman_subclass")), false);
                                } else {
                                    if (sAttrubitesData.getMagicLevel() >= 6) {
                                        Network.serverDataAction("skill43", 1);
                                    } else {
                                        player.connection.getConnection();
                                        Network.serverDataAction("", 4);
                                        player.displayClientMessage(Component.literal("У вас недостаточный уровень"), false);
                                    }
                                }


                            }
                    ),

                    new SkillButton(
                            -80, 100, buttonWidth, buttonHeight,
                            BUTTON_TEXTURE,
                            Component.translatable("skill.era_tweaks.skill44"),
                            "skill44",
                            new SkillCondition(List.of("skill42", "skill43"), false),
                            "none",
                            List.of(
                                    Component.translatable("tooltip.era_tweaks.skill_requirements"),
                                    Component.literal(" "),
                                    Component.literal("• ")
                                            .append(Component.translatable("sattribute.era_tweaks.magic"))
                                            .append(Component.literal(": "))
                                            .append(Component.literal("6"))
                            ),
                            btn -> {
                                if (!data.getPlayerSubClass().equals("bowman")) {
                                    Network.serverDataAction("", 4);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.another_class",
                                            Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass"), Component.translatable("text.era_tweaks.bowman_subclass")), false);
                                } else {
                                    //Родительские навыки
                                    if (data.getPlayerSkills().contains("skill42") || data.getPlayerSkills().contains("skill43")) {
                                        if (sAttrubitesData.getMagicLevel() >= 6) {
                                            Network.serverDataAction("skill44", 1);
                                        } else {
                                            player.connection.getConnection();
                                            Network.serverDataAction("", 4);
                                            player.displayClientMessage(Component.literal("У вас недостаточный уровень"), false);
                                        }
                                    } else {
                                        player.connection.getConnection();
                                        Network.serverDataAction("", 4);
                                        player.displayClientMessage(Component.literal("Вы не изучили предыдущие навыки!"), false);
                                    }
                                }



                            }
                    )
            );

        }

        else {
            return List.of();
        }


        return nodes;

    }




    public static Component getTitleSubClass(int page) {
        Component title = null;
        if (page == 1) {
            title = Component.translatable("text.era_tweaks.paladin_subclass");
        }
        else if (page == 2) {
            title = Component.translatable("text.era_tweaks.bowman_subclass");
        }
        else if (page == 3) {
            title = Component.translatable("text.era_tweaks.scout_subclass");
        }
        else if (page == 4) {
            title = Component.translatable("text.era_tweaks.druid_subclass");
        }
        else if (page == 5) {
            title = Component.translatable("text.era_tweaks.priest_subclass");
        }
        else if (page == 6) {
            title = Component.translatable("text.era_tweaks.necromancer_subclass");
        }
        else if (page == 7) {
            title = Component.translatable("text.era_tweaks.commissar_subclass");
        }
        else if (page == 8) {
            title = Component.translatable("text.era_tweaks.alchemist_subclass");
        }
        else if (page == 9) {
            title = Component.translatable("text.era_tweaks.smith_subclass");
        }

        return title;
    }

    public static Component getTitleClass(int page) {
        Component title = null;
        if (page >= 1 && page <= 3) {
            title = Component.translatable("text.era_tweaks.warrior_class");
        }
        else if (page >= 4 && page <= 6) {
            title = Component.translatable("text.era_tweaks.wizard_class");
        }
        else if (page >= 7 && page <= 9) {
            title = Component.translatable("text.era_tweaks.assistant_class");
        }

        return title;
    }
}
