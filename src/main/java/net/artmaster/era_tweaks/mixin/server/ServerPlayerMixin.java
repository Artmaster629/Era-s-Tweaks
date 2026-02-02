package net.artmaster.era_tweaks.mixin.server;

import net.artmaster.era_tweaks.custom.player_classes.assistant.smith.Smith;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {



    @Inject(method = "closeContainer", at = @At("TAIL"))
    private void eraTweaks$assemble(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        System.out.println(player);
    }

}