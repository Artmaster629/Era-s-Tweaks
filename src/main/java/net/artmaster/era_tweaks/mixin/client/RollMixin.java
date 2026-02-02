package net.artmaster.era_tweaks.mixin.client;

import com.alrex.parcool.common.action.impl.Dodge;
import com.alrex.parcool.common.attachment.common.Parkourability;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;

@Mixin(Dodge.class)
public class RollMixin {



    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void onStartInLocalClient(Player player, Parkourability parkourability, ByteBuffer startInfo, CallbackInfoReturnable<Boolean> cir) {
        if (player != null) {
            var data = player.getData(ModAttachments.PLAYER_CLASS);
            //System.out.println("testing...");
            if (!data.getPlayerSkills().contains("scout_skill_5_1")) {
                //System.out.println("canceled");
                cir.setReturnValue(false);
            } else {
                //Network.serverDataAction("", 12);
                cir.setReturnValue(true);
            }
        }


        //System.out.println("not canceled");

    }
}
