package net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntity.getLookHit;


@EventBusSubscriber(modid = ModMain.MODID)
public class TotemEvents {


    @SubscribeEvent
    public static void onPlaceEvent(PlayerTickEvent.Post event) {
        if (!(event.getEntity().level() instanceof ServerLevel level)) return;




        if (event.getEntity() instanceof ServerPlayer player) {




        }
    }

    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer().level() instanceof ServerLevel level)) return;

        var pos = event.getPos();
        var state = level.getBlockState(pos);
        var block = state.getBlock();

        var be = level.getBlockEntity(pos);
        System.out.println(be);

        if (event.getPlayer() instanceof ServerPlayer player) {
            if (be instanceof TotemBlockEntity totemBE) {

                HitResult hit = getLookHit(player, 50);


                BlockPos placePos = ((BlockHitResult) hit).getBlockPos().relative(((BlockHitResult) hit).getDirection());

                if (!level.getBlockState(placePos).isAir()) return;

                if (totemBE.getOwner() == null || totemBE.getOwner().equals(player.getUUID())) {
                    player.sendSystemMessage(Component.literal("Вы успешно удалили тотем!"));
                } else {
                    player.sendSystemMessage(Component.literal("Вы не можете ломать чужой тотем!"));
                    event.setCanceled(true);
                }

            }
        }
    }
}
