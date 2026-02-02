package net.artmaster.era_tweaks.custom.stamina;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ServerStaminaChanges {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class ServerChanges {
        @SubscribeEvent
        public static void onStaminaSpendingByAttack(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var target = event.getEntity();
                int final_value = (int)(event.getOriginalDamage()+target.getArmorValue())*10;

                Network.clientStaminaSet(player, final_value);
            }
        }
    }
}

