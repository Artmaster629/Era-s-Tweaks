package net.artmaster.era_tweaks.custom.events;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant.CommonAssistant;
import net.artmaster.era_tweaks.custom.player_classes.wizard.necromancer.Necromancer;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.Priest;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.Bowman;
import net.artmaster.era_tweaks.custom.player_classes.warrior.common_warrior.CommonWarrior;
import net.artmaster.era_tweaks.custom.player_classes.warrior.paladin.Paladin;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.Scout;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;


@EventBusSubscriber(modid = ModMain.MODID)
public class CloneHandler {



    @FunctionalInterface
    public interface Applier {
        void apply(PlayerClassData data, ServerPlayer player);
    }



    private static final Map<String, Applier> REGISTRY = new HashMap<>();

    static {
        // warrior
        REGISTRY.put("warrior", CommonWarrior.ApplyChanges::makeChangesToClass);
        REGISTRY.put("paladin", Paladin.ApplyChanges::makeChangesToClass);
        REGISTRY.put("alchemist", Bowman.ApplyChanges::makeChangesToClass);
        REGISTRY.put("scout", Scout.ApplyChanges::makeChangesToClass);

        // wizard
        REGISTRY.put("necromancer", Necromancer.ApplyChanges::makeChangesToClass);
        REGISTRY.put("priest", Priest.ApplyChanges::makeChangesToClass);

        // assistant
        REGISTRY.put("assistant", CommonAssistant.ApplyChanges::makeChangesToClass);
    }

    public static void apply(PlayerClassData data, ServerPlayer player) {
        String classKey = data.getPlayerClass();
        Applier applierClass = REGISTRY.get(classKey);

        if (applierClass != null) {
            applierClass.apply(data, player);
        } else {
            ModMain.LOGGER.warn("No applier registered for {}", classKey);
        }

        String subclassKey = data.getPlayerSubClass();
        Applier applierSubclass = REGISTRY.get(subclassKey);

        if (applierSubclass != null) {
            applierSubclass.apply(data, player);
        } else {
            ModMain.LOGGER.warn("No applier registered for {}", subclassKey);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        ServerPlayer newPlayer = (ServerPlayer) event.getEntity();
        ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
        PlayerClassData data = oldPlayer.getData(ModAttachments.PLAYER_CLASS);


        apply(data, newPlayer);

//        if (data.getPlayerClass().equals("warrior")) {
//            CommonWarrior.ApplyChanges.makeChangesToClass(data, newPlayer);
//
//            if (data.getPlayerSubClass().equals("paladin")) {
//                Paladin.ApplyChanges.makeChangesToClass(data, newPlayer);
//            } else if (data.getPlayerSubClass().equals("alchemist")) {
//                Smith.ApplyChanges.makeChangesToClass(data, newPlayer);
//            } else if (data.getPlayerSubClass().equals("scout")) {
//                Scout.ApplyChanges.makeChangesToClass(data, newPlayer);
//            }
//        } else if (data.getPlayerClass().equals("wizard")) {
//            //CommonWizard
//            if (data.getPlayerSubClass().equals("necromancer")) {
//                Necromancer.ApplyChanges.makeChangesToClass(data, newPlayer);
//            } else if (data.getPlayerSubClass().equals("priest")) {
//                Priest.ApplyChanges.makeChangesToClass(data, newPlayer);
//            }
//            //druid
//        } else if (data.getPlayerClass().equals("assistant")) {
//            CommonAssistant.ApplyChanges.makeChangesToClass(data, newPlayer);
//            //commissar
//            //alchemist
//            //smith
//        }
    }
}
