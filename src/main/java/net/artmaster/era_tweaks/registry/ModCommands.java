package net.artmaster.era_tweaks.registry;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;


@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class ModCommands {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("upgrades")
                        .then(Commands.literal("menu")
                            .executes(ctx -> {
                                CommandSourceStack source = ctx.getSource();
                                if (source.getEntity() instanceof ServerPlayer player) {

                                    Network.syncSkills(player);
                                    Network.syncClasses(player);
                                    Network.sendOpenGui(player, "upgrade_manage_screen");
                                }
                                return 1;
                            })
                        )
                        .then(Commands.literal("class_select_menu")
                                .executes(ctx -> {
                                    CommandSourceStack source = ctx.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        var data = player.getData(ModAttachments.PLAYER_CLASS);
                                        data.changePlayerClass("unknown");
                                        Network.syncSkills(player);
                                        Network.syncClasses(player);
                                        Network.sendOpenGui(player, "class_select_screen");
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("get_progress_xp")
                                .executes(ctx -> {
                                    CommandSourceStack source = ctx.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        Network.syncSkills(player);
                                        Network.syncClasses(player);
                                        var data = player.getData(ModAttachments.PLAYER_CLASS);
                                        source.sendSystemMessage(Component.literal("Прогресс получения очка прокачки: "+data.getUpgradesPointsProgress()));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("add_progress_xp")
                                .executes(ctx -> {
                                    CommandSourceStack source = ctx.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        Network.syncSkills(player);
                                        Network.syncClasses(player);
                                        var data = player.getData(ModAttachments.PLAYER_CLASS);
                                        data.addUpgradesPoints(1);
                                        data.setUpgradesPointsProgress(0);
                                        Network.syncClasses(player);
                                        source.sendSystemMessage(Component.literal("Добавлено очко прокачки"));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("add_skill")
                                .then(Commands.argument("skill_id", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String skill_id = StringArgumentType.getString(ctx, "skill_id");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
                                                data.getPlayerSkills().add(skill_id);


                                                Network.syncSkills(player);
                                                Network.syncClasses(player);

                                                source.sendSuccess(() -> Component.literal("§aИгроку "+player.getName()+" выдан навык \""+Component.translatable("skill.era_tweaks."+skill_id).getString()+"\"."), true);
                                            }

                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("remove_skill")
                                .then(Commands.argument("skill_id", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String skill_id = StringArgumentType.getString(ctx, "skill_id");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
                                                if (data.getPlayerSkills().contains(skill_id)) {
                                                    data.getPlayerSkills().remove(skill_id);
                                                } else {
                                                    source.sendFailure(Component.literal("§cИгрок "+player.getName()+" не имеет навык \""+Component.translatable("skill.era_tweaks."+skill_id).getString()+"\"."));
                                                    return 0;
                                                }


                                                Network.syncSkills(player);
                                                Network.syncClasses(player);

                                                source.sendSuccess(() -> Component.literal("§aИгроку "+player.getName()+" убран навык \""+Component.translatable("skill.era_tweaks."+skill_id).getString()+"\"."), true);
                                            }

                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("clear_skills")
                                .executes(ctx -> {
                                    CommandSourceStack source = ctx.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
                                        data.getPlayerSkills().clear();


                                        Network.syncSkills(player);
                                        Network.syncClasses(player);
                                        Network.sendOpenGui(player, "upgrade_manage_screen");
                                        source.sendSuccess(() -> Component.literal("§a Все навыки игрока "+player.getName()+" были очищены."), true);
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("set_class")
                                .then(Commands.argument("classname", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String className = StringArgumentType.getString(ctx, "classname");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
                                                data.changePlayerClass(className);


                                                Network.syncSkills(player);
                                                Network.syncClasses(player);

                                                source.sendSuccess(() -> Component.literal("Класс игрока "+player.getName()+" успешно изменён на "+className+"."), true);
                                            }

                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("set_subclass")
                                .then(Commands.argument("classname", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String className = StringArgumentType.getString(ctx, "classname");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(ModAttachments.PLAYER_CLASS);
                                                data.changePlayerSubClass(className);


                                                Network.syncSkills(player);
                                                Network.syncClasses(player);

                                                source.sendSuccess(() -> Component.literal("Подкласс игрока "+player.getName()+" успешно изменён на "+className+"."), true);
                                            }

                                            return 1;
                                        })
                                )
                        )

        );

        event.getDispatcher().register(
                Commands.literal("sattributes")
                        .then(Commands.literal("body")
                                .then(Commands.literal("set_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        if (level > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        data.setBodyLevel(level);
                                                        data.setBodyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Тела игрока "+player.getName()+" успешно установлен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("add_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level+data.getBodyLevel() > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }

                                                        data.setBodyLevel(data.getBodyLevel()+level);
                                                        data.setBodyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Тела игрока "+player.getName()+" успешно увеличен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("remove_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        if (data.getBodyLevel()-level < 0) {
                                                            source.sendFailure(Component.literal("§cПри таком значении уровень атрибута будет отрицательным!!"));
                                                            return 0;
                                                        }

                                                        data.setBodyLevel(data.getBodyLevel()-level);
                                                        data.setBodyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Тела игрока "+player.getName()+" успешно уменьшен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("intellect")
                                .then(Commands.literal("set_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        if (level > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        data.setIntellectLevel(level);
                                                        data.setIntellectProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Разума игрока "+player.getName()+" успешно установлен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("add_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level+data.getIntellectLevel() > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }

                                                        data.setIntellectLevel(data.getIntellectLevel()+level);
                                                        data.setIntellectProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Разума игрока "+player.getName()+" успешно увеличен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("remove_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        if (data.getIntellectLevel()-level < 0) {
                                                            source.sendFailure(Component.literal("§cПри таком значении уровень атрибута будет отрицательным!!"));
                                                            return 0;
                                                        }

                                                        data.setIntellectLevel(data.getIntellectLevel()-level);
                                                        data.setIntellectProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Разума игрока "+player.getName()+" успешно уменьшен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("society")
                                .then(Commands.literal("set_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        if (level > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        data.setSocietyLevel(level);
                                                        data.setSocietyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Социума игрока "+player.getName()+" успешно установлен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("add_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level+data.getSocietyLevel() > 50) {
                                                            source.sendFailure(Component.literal("§cУровень любого атрибута не может быть больше 50!"));
                                                            return 0;
                                                        }
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }

                                                        data.setSocietyLevel(data.getSocietyLevel()+level);
                                                        data.setSocietyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Социума игрока "+player.getName()+" успешно увеличен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("remove_level")
                                        .then(Commands.argument("level", IntegerArgumentType.integer())
                                                .executes(ctx -> {
                                                    CommandSourceStack source = ctx.getSource();
                                                    int level = IntegerArgumentType.getInteger(ctx, "level");
                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                        PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                                                        if (level < 0) {
                                                            source.sendFailure(Component.literal("§cЗначение не может быть отрицательным!"));
                                                            return 0;
                                                        }
                                                        if (data.getSocietyLevel()-level < 0) {
                                                            source.sendFailure(Component.literal("§cПри таком значении уровень атрибута будет отрицательным!!"));
                                                            return 0;
                                                        }

                                                        data.setSocietyLevel(data.getSocietyLevel()-level);
                                                        data.setSocietyProgress(0);
                                                        Network.syncSkills(player);

                                                        source.sendSuccess(() -> Component.literal("Уровень Социума игрока "+player.getName()+" успешно уменьшен на "+level+"."), true);
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )


        );


    }
}

