package net.artmaster.era_tweaks.mixin.server;

import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.creative.playerrevive.api.IBleeding;
import team.creative.playerrevive.cap.Bleeding;
import team.creative.playerrevive.server.PlayerReviveServer;
import team.creative.playerrevive.server.ReviveEventServer;

@Mixin(ReviveEventServer.class)
public class NecromancerRevivesMixin {



    @Inject(
            method = "playerInteract",
            at = @At("HEAD"),
            cancellable = true)
    private void onReviveCheck(PlayerInteractEvent.EntityInteract event, CallbackInfo ci) {




        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_CLASS);
            if (!data.getPlayerSubClass().equals("necromancer")) {
                //player.sendSystemMessage(Component.literal("Воскрешать других игроков могут только игроки с подклассом Некромант класса Мага!"));
                ci.cancel();
                event.setCanceled(true);
            }
        }

    }
}

