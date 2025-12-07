package net.artmaster.era_tweaks.mixin;

import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import com.alrex.parcool.common.stamina.handlers.ParCoolStaminaHandler;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParCoolStaminaHandler.class)
public class StaminaProgressMixin {

    @Inject(method = "consume", at = @At("HEAD"))
    private void consume(LocalPlayer player, ReadonlyStamina current, int value, CallbackInfoReturnable<ReadonlyStamina> cir) {


        if (current.value() < current.max()/5) {
            double value_set = (0.035 * (double) value/2)-((double) current.value()/100);
            Network.serverDataStaminaSet(value_set);
        }



    }
}
