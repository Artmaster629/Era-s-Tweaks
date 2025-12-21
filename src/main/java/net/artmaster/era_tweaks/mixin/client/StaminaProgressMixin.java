package net.artmaster.era_tweaks.mixin.client;

import com.alrex.parcool.common.attachment.common.ReadonlyStamina;
import com.alrex.parcool.common.stamina.handlers.ParCoolStaminaHandler;
import net.artmaster.era_tweaks.api.container.MyAttachments;
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

            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            double value_set = ((0.035 * (double) value/2)-((double) current.value()/100))/10;
            double value_set_final = (-value_set)/data.getStaminaLevel();
            if (value_set_final < 0) {
                return;
            }





            Network.serverDataStaminaSet(value_set_final);
        }



    }
}
