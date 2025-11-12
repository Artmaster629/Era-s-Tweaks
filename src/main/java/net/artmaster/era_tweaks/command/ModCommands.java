package net.artmaster.era_tweaks.command;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.parties.party.api.IPartyManagerAPI;
import xaero.pac.common.server.parties.party.api.IServerPartyAPI;


@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class ModCommands {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("guild")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            if (source.getEntity() instanceof ServerPlayer player) {
                                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

                                if (server == null) return 0;

                                IPartyManagerAPI partyManager = OpenPACServerAPI.get(server).getPartyManager();
                                IServerPartyAPI party = partyManager.getPartyByMember(player.getUUID());
                                if (party == null) {
                                    Network.sendAllyParties(player, party);
                                    player.displayClientMessage(Component.translatable("text.era_tweaks.no_party_pm"), false);
                                    return 0;
                                }
                                Network.sendOpenGui(player);
                            }
                            return 1;
                        })

        );
    }
}

