package net.artmaster.era_tweaks.event;


import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.api.upgrades.MyAttachments;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static net.artmaster.era_tweaks.network.Network.syncSkills;

@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class ModEvents {




    @SubscribeEvent
    public static void onTest(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel() instanceof ServerLevel && event.getEntity() instanceof ServerPlayer player) {

            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            System.out.println("Build level: "+data.getBuildingLevel());
            data.setBuildingLevel(data.getBuildingLevel()+1);
            System.out.println("NEW Build level: "+data.getBuildingLevel());
            syncSkills(player);

        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            syncSkills(sp);
        }
    }

//    @SubscribeEvent
//    public static void onPlayerClone(PlayerEvent.Clone event) {
//        if (event.isWasDeath()) {
//            var oldData = event.getOriginal().getData(MyAttachments.PLAYER_SKILLS);
//            var newData = event.getEntity().getData(MyAttachments.PLAYER_SKILLS);
//            newData.copyFrom(oldData);
//        }
//    }
}
