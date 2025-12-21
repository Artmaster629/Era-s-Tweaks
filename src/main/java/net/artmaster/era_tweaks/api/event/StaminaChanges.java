package net.artmaster.era_tweaks.api.event;

import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class StaminaChanges {


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


    @EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
    public static class ClientChanges {
        @SubscribeEvent
        public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
            if (event.getEntity() instanceof LocalPlayer player) {
                Stamina stamina = Stamina.get(player);
                if (stamina.isExhausted()) {
                    Network.serverDataAction("", 8);
                }
            }
        }



        private static boolean sent = false;

        @SubscribeEvent
        public static void onStaminaExhausted(PlayerTickEvent.Pre event) {
            if (!(event.getEntity() instanceof LocalPlayer player)) return;

            if (Stamina.get(player).isExhausted() && player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                if (!sent) {
                    Network.serverDataAction("exhausted", 5);
                    sent = true;
                }
            } else {
                sent = false;
            }
        }

        @SubscribeEvent
        public static void onStaminaExhaustedByAttack(AttackEntityEvent event) {
            if (!(event.getEntity() instanceof LocalPlayer player)) return;






            var stamina = Stamina.get(player);
            if (stamina.isExhausted()) {
                event.setCanceled(true);
            }
        }



        @SubscribeEvent
        public static void onStaminaSpending(PlayerEvent.BreakSpeed event) {
            if (!(event.getEntity() instanceof LocalPlayer player)) return;

            BlockPos pos = event.getPosition().get();
            BlockState state = event.getEntity().level().getBlockState(pos);
            float hardness = state.getDestroySpeed(event.getEntity().level(), pos);
            if (hardness == 0) hardness+= 0.1F;




            var stamina = Stamina.get(player);
            if (stamina.isExhausted()) {
                event.setCanceled(true);
                return;
            }



            float speed = event.getOriginalSpeed();
            int final_value = (int)(speed*9 * hardness);
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isCorrectToolForDrops(state)) {
                final_value= (int) (final_value/speed);
                if (hardness > 35) {
                    final_value/=38;
                }
            }
            stamina.consume(final_value);
        }
    }


}

