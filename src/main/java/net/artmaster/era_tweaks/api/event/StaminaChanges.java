package net.artmaster.era_tweaks.api.event;

import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class StaminaChanges {
    //Стамина-ивенты
    @SubscribeEvent
    public static void onBreakingBlockStaminaExhausted(PlayerEvent.BreakSpeed event) {
        if (event.getEntity() instanceof ServerPlayer player && Stamina.get(player).isExhausted()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onStaminaExhausted(PlayerTickEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer player && Stamina.get(player).isExhausted()) {
            player.addEffect((new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3)));
        }
    }

    @SubscribeEvent
    public static void onStaminaSpending(BlockEvent.BreakEvent event) {

        if (event.getLevel() instanceof ServerLevel level && event.getPlayer() instanceof ServerPlayer player) {

            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            float hardness = (state.getDestroySpeed(level, pos)*10*2);


            Network.clientStaminaSet(player, (int) (10*hardness));
        }
    }
}
