package net.artmaster.era_tweaks.mixin.server.brewing;

import net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist.Alchemist;
import net.artmaster.era_tweaks.network.BrewingStandPlayerTracker;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBEPlayerMixin {

    @Inject(
            method = "doBrew",
            at = @At("TAIL")
    )
    private static void afterBrew(Level level, BlockPos pos, NonNullList<ItemStack> items, CallbackInfo ci) {
        var be = level.getBlockEntity(pos);
        if (!(be instanceof BrewingStandBlockEntity brewingStandBE)) return;

        ServerPlayer player = BrewingStandPlayerTracker.LAST_PLAYER.remove(brewingStandBE);

        if (player == null) return;
        var data = player.getData(ModAttachments.PLAYER_CLASS);
        if (!data.getPlayerSkills().contains("alchemist_skill_4_1") || !data.getPlayerSkills().contains("alchemist_skill_4_2")) return;
        Alchemist.Actions.onAlchemistSkill4_1_OR_4_2(player, data, brewingStandBE);

    }
}


