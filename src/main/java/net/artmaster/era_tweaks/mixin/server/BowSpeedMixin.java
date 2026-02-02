package net.artmaster.era_tweaks.mixin.server;

import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class BowSpeedMixin {

    @Shadow
    private int useItemRemaining;

    @Inject(
            method = "updateUsingItem",
            at = @At("HEAD")
    )
    private void accelerateBow(ItemStack usingItem, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!(self instanceof ServerPlayer player)) return;

        if (!(self.getUseItem().getItem() instanceof BowItem)) return;

        var data = player.getData(ModAttachments.PLAYER_CLASS);
        if (!data.getPlayerSkills().contains("bowman_skill_2_1")) return;

        // ускорение (2 = в 2 раза быстрее)
        this.useItemRemaining--;
    }
}
