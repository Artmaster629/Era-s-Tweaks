//package net.artmaster.era_tweaks.mixin.server.brewing;
//
//import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
//import org.spongepowered.asm.mixin.Mixin;
//
//
//@Mixin(BrewingStandBlockEntity.class)
//public abstract class PotionBrewingMixin {

//    @ModifyArg(
//            method = "doBrew",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/core/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;"
//            ),
//            index = 1 // ItemStack, который кладётся в слот
//    )
//    private static Object eraTweaks$modifyBrewedPotion(Object obj) {
//        ItemStack stack = (ItemStack) obj;
//
//        if (stack.isEmpty()) return stack;
//
//        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
//        if (contents == null) return stack;
//
//        ItemStack copy = stack.copy();
//        copy.set(
//                DataComponents.POTION_CONTENTS,
//                contents.withEffectAdded(
//                        new MobEffectInstance(
//                                MobEffects.REGENERATION,
//                                200,
//                                0
//                        )
//                )
//        );
//
//        return copy;
//    }


//}