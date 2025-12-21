package net.artmaster.era_tweaks.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
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
                Commands.literal("upgrade")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            if (source.getEntity() instanceof ServerPlayer player) {
                                Network.syncSkills(player);
                                Network.syncClasses(player);
                                Network.sendOpenGui(player, "upgrade_manage_screen");
                            }
                            return 1;
                        })

        );
        event.getDispatcher().register(
                Commands.literal("player_class")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            if (source.getEntity() instanceof ServerPlayer player) {
                                Network.syncSkills(player);
                                Network.syncClasses(player);
                                Network.sendOpenGui(player, "class_manage_screen");
                            }
                            return 1;
                        })

        );

        event.getDispatcher().register(
                Commands.literal("player_class")
                        .then(Commands.literal("setclass")
                                .then(Commands.argument("classname", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String className = StringArgumentType.getString(ctx, "classname");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(MyAttachments.PLAYER_CLASS);
                                                data.changePlayerClass(className);


                                                Network.syncSkills(player);
                                                Network.syncClasses(player);

                                                source.sendSuccess(() -> Component.literal("Класс игрока "+player.getName()+" успешно изменён на "+className+"."), true);
                                            }

                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("setsubclass")
                                .then(Commands.argument("classname", StringArgumentType.string())
                                        .executes(ctx -> {
                                            CommandSourceStack source = ctx.getSource();
                                            String className = StringArgumentType.getString(ctx, "classname");

                                            if (source.getEntity() instanceof ServerPlayer player) {

                                                PlayerClassData data = player.getData(MyAttachments.PLAYER_CLASS);
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
    }
}

