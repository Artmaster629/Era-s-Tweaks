package net.artmaster.era_tweaks.mixin.server;


import net.artmaster.era_tweaks.custom.player_classes.assistant.smith.Smith;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin {

    @Shadow
    @Final
    private ResultContainer resultSlots;

    @Inject(
            method = "slotChangedCraftingGrid",
            at = @At("TAIL")
    )
    private static void eraTweaks$afterCraft(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftSlots, ResultContainer resultSlots, RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = resultSlots.getItem(0);
            if (!stack.isEmpty()) {
                resultSlots.setItem(0,
                        Smith.Actions.applyChangesToItem(serverPlayer, stack.copy())
                );
            }
        }
    }
}
