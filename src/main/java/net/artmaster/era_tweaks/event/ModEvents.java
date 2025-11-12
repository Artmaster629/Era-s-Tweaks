package net.artmaster.era_tweaks.event;


import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.ServerChatEvent;

@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class ModEvents {




    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {



        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString();
        if (!message.isEmpty()) {
            String prefix = message.substring(0, 1);
            if (prefix.equals("!")) {
                event.setCanceled(true);
                String command = "chat common "+message.substring(1);
                Network.sendCommand(player, command);
            }
            if (!prefix.equals("!")) {
                event.setCanceled(true);
                String command = "chat local "+message;
                Network.sendCommand(player, command);
            }
        }
    }
}
