package net.artmaster.era_tweaks.mixin.server;

import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.creative.playerrevive.cap.Bleeding;
import team.creative.playerrevive.server.ReviveEventServer;

import java.util.List;

@Mixin(Bleeding.class)
public abstract class SpeedRevivesMixin {


    @Shadow
    private float progress;
    @Shadow public abstract List<Player> revivingPlayers();

    @Inject(method = "tick", at = @At("TAIL"))
    private void eraTweaks$extraProgress(Player player, CallbackInfo ci) {


        if (player instanceof ServerPlayer serverPlayer) {
            var data = serverPlayer.getData(ModAttachments.PLAYER_CLASS);
            if (data.getPlayerSkills().contains("common_assistant_skill_6")) {
                if (!this.revivingPlayers().isEmpty()) {
                    this.progress += 1.25F; // плоский бонус
                }
            }

        }
    }
}

