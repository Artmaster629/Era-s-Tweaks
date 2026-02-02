package net.artmaster.era_tweaks.mixin.server.brewing;

import net.artmaster.era_tweaks.network.BrewingStandPlayerTracker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandMenu.class)
public abstract class BrewingStandMenuPlayerMixin {

    @Shadow @Final private Container brewingStand;

    @Inject(
            method = "quickMoveStack",
            at = @At("HEAD")
    )
    private void eraTweaks$rememberPlayer(Player player, int index, CallbackInfoReturnable<?> cir) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!(this.brewingStand instanceof BrewingStandBlockEntity be)) return;

        BrewingStandPlayerTracker.LAST_PLAYER.put(be, serverPlayer);
    }
}
