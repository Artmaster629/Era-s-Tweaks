package net.artmaster.era_tweaks.mixin.server;

import net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist.custom.PotionCauldronData;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(CauldronInteraction.class)
public interface CauldronMixin {

    @Inject(
            method = "bootStrap",
            at = @At("TAIL")
    )
    private static void eraTweaks$addPotionInteraction(CallbackInfo ci) {

        CauldronInteraction potionInsert = (state, level, pos, player, hand, stack) -> {

            if (level.isClientSide) return ItemInteractionResult.CONSUME;
            if (!(level instanceof ServerLevel serverLevel)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            PotionContents incoming = stack.get(DataComponents.POTION_CONTENTS);
            if (incoming == null || incoming.potion().isEmpty()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            PotionContents existing = PotionCauldronData.get(serverLevel, pos);

            PotionContents result;
            if (existing == null || existing == PotionContents.EMPTY) {
                result = incoming;
                serverLevel.setBlockAndUpdate(
                        pos,
                        Blocks.WATER_CAULDRON.defaultBlockState()
                );
            } else {
                if (!serverPlayer.getData(ModAttachments.PLAYER_CLASS)
                        .getPlayerSkills().contains("alchemist_skill_5")) {
                    return ItemInteractionResult.FAIL;
                }

                result = mix(existing, incoming);
                serverLevel.setBlockAndUpdate(
                        pos,
                        Blocks.WATER_CAULDRON.defaultBlockState()
                                .setValue(net.minecraft.world.level.block.LayeredCauldronBlock.LEVEL, 3)
                );
            }

            PotionCauldronData.set(serverLevel, pos, result);



            if (!serverPlayer.isCreative()) {
                stack.shrink(1);
                serverPlayer.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }

            return ItemInteractionResult.SUCCESS;
        };

        CauldronInteraction.EMPTY.map().put(Items.POTION, potionInsert);
        CauldronInteraction.WATER.map().put(Items.POTION, potionInsert);



        CauldronInteraction.WATER.map().put(Items.GLASS_BOTTLE,
                (state, level, pos, player, hand, stack) -> {


                    if (level.isClientSide) {
                        return ItemInteractionResult.CONSUME;
                    }

                    if (!(level instanceof ServerLevel serverLevel)) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }
                    if (!(player instanceof ServerPlayer serverPlayer)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

                    var contents = PotionCauldronData.get(serverLevel, pos);
                    if (contents == null) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION; // обычная вода
                    }

                    ItemStack potion = new ItemStack(Items.POTION);
                    potion.set(DataComponents.POTION_CONTENTS, contents);

                    if (!serverPlayer.isCreative()) {
                        stack.shrink(1);
                    }

                    if (!serverPlayer.addItem(potion)) {
                        serverPlayer.drop(potion, false);
                    }

                    // уменьшаем уровень котла
                    serverLevel.setBlockAndUpdate(
                            pos,
                            Blocks.CAULDRON.defaultBlockState()
                    );

                    PotionCauldronData.set(serverLevel, pos, null);

                    return ItemInteractionResult.SUCCESS;
                });





//        CauldronInteraction.EMPTY.map().put(Items.POTION,
//                (state, level, pos, player, hand, stack) -> {
//
//                    if (level.isClientSide) {
//                        return ItemInteractionResult.SUCCESS;
//                    }
//                    System.out.println("isn't clientside");
//
//                    if (!(level instanceof ServerLevel serverLevel)) {
//                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//                    }
//                    System.out.println("is serverlevel");
//                    if (!(player instanceof ServerPlayer serverPlayer)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//                    //if (!serverPlayer.getData(ModAttachments.PLAYER_CLASS).getPlayerSkills().contains("alchemist_skill_5")) return ItemInteractionResult.FAIL;
//                    System.out.println("is serverplayer");
//
//                    var contents = stack.get(DataComponents.POTION_CONTENTS);
//                    if (contents == null || contents.potion().isEmpty()) {
//                        System.out.println(contents==null);
//                        System.out.println(contents.potion().isEmpty());
//                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//
//                    }
//                    System.out.println("contents");
//
//                    PotionContents existing = PotionCauldronData.get(level, pos);
//                    PotionContents incoming = stack.get(DataComponents.POTION_CONTENTS);
//
//                    if (incoming == null || incoming.potion().isEmpty()) {
//                        System.out.println(incoming==null);
//                        System.out.println(incoming.potion().isEmpty());
//                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//                    }
//                    System.out.println("incoming");
//
//                    PotionContents result;
//
//                    if (existing == null || existing == PotionContents.EMPTY) {
//                        // Котёл пустой → просто ставим новое зелье
//                        result = incoming;
//                        System.out.println("existing null or potion empty");
//                    } else {
//                        // Смешиваем с существующим
//                        if (!serverPlayer.getData(ModAttachments.PLAYER_CLASS).getPlayerSkills().contains("alchemist_skill_5")) {
//                            System.out.println("hasn't skill");
//                            return ItemInteractionResult.FAIL;
//                        }
//                        System.out.println("mixing...");
//                        result = mix(existing, incoming);
//                    }
//
//                    PotionCauldronData.set(serverLevel, pos, result);
//
//                    serverLevel.setBlockAndUpdate(pos, Blocks.WATER_CAULDRON.defaultBlockState());
//
//                    if (!serverPlayer.isCreative()) {
//                        stack.shrink(1);
//                        serverPlayer.addItem(new ItemStack(Items.GLASS_BOTTLE));
//                    }
//
//                    return ItemInteractionResult.SUCCESS;
//                });

    }


    private static PotionContents mix(PotionContents a, PotionContents b) {
        Map<Holder<MobEffect>, MobEffectInstance> map = new HashMap<>();

        // эффекты A (только custom + potion один раз)
        a.getAllEffects().forEach(e ->
                map.merge(
                        e.getEffect(),
                        new MobEffectInstance(e),
                        (oldE, newE) -> merge(oldE, newE)
                )
        );

        // эффекты B
        b.getAllEffects().forEach(e ->
                map.merge(
                        e.getEffect(),
                        new MobEffectInstance(e),
                        (oldE, newE) -> merge(oldE, newE)
                )
        );

        return new PotionContents(
                Optional.empty(), // без базового зелья
                Optional.empty(),
                List.copyOf(map.values())
        );
    }

    private static MobEffectInstance merge(
            MobEffectInstance a,
            MobEffectInstance b
    ) {
        int duration = Math.max(a.getDuration(), b.getDuration());
        int amplifier = Math.max(a.getAmplifier(), b.getAmplifier());
        if (a.equals(b)) {
            duration = a.getDuration()+b.getDuration();
            amplifier = a.getAmplifier()+b.getAmplifier();
        }
        return new MobEffectInstance(
                a.getEffect(),
                duration,
                amplifier,
                a.isAmbient() || b.isAmbient(),
                a.isVisible() || b.isVisible(),
                a.showIcon() || b.showIcon()
        );
    }
}
