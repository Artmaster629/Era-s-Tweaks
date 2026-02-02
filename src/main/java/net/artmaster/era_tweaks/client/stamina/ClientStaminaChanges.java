package net.artmaster.era_tweaks.client.stamina;

import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
public class ClientStaminaChanges {
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            Stamina stamina = Stamina.get(player);
            if (stamina.isExhausted()) {
                Network.toServerAction("", 8);
            }
        }
    }


    private static boolean sent = false;
    @SubscribeEvent
    public static void onStaminaExhausted(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LocalPlayer player)) return;
        if (Stamina.get(player).isExhausted() && player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
            if (!sent) {
                Network.toServerAction("exhausted", 5);
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
        if (!event.getPosition().isPresent()) return;

        BlockPos pos = event.getPosition().get();
        BlockState state = player.level().getBlockState(pos);

        float hardness = state.getDestroySpeed(player.level(), pos);
        if (hardness <= 0) return;

        var stamina = Stamina.get(player);
        if (stamina.isExhausted()) {
            event.setCanceled(true);
            return;
        }

        float speed = event.getNewSpeed(); // уже с эффектами
        if (speed <= 0) return;

        // прогресс ломания за тик
        float progressPerTick = speed / hardness;
        float baseCost = 1.0f;
        // множитель за неправильный инструмент
        float toolMultiplier = player.getItemInHand(InteractionHand.MAIN_HAND)
                .isCorrectToolForDrops(state) ? 1.0f : 3.0f;

        float staminaCost =
                baseCost
                        * hardness
                        * progressPerTick
                        * toolMultiplier;
        float armorPenalty = player.getArmorValue() * 0.05f;
        staminaCost *= (1.0f + armorPenalty);

        stamina.consume(Math.max(1, Math.round(staminaCost)));
    }
}