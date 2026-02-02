package net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.PlayerBrewedPotionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

import static net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom.MarkTeam.mark;
import static net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom.MarkTeam.unmark;
import static net.artmaster.era_tweaks.custom.player_classes.wizard.priest.Priest.getEntityPlayerIsLookingAt;
import static net.artmaster.era_tweaks.network.Network.syncClasses;

public class Alchemist {


    public static class Actions {

        public static void onAlchemistSkill4_1_OR_4_2(ServerPlayer player, PlayerClassData data, BrewingStandBlockEntity brewingStandBE) {
            try {
                // Reflection для доступа к приватному полю 'items'
                var itemsField = BrewingStandBlockEntity.class.getDeclaredField("items");
                itemsField.setAccessible(true);
                NonNullList<ItemStack> slots = (NonNullList<ItemStack>) itemsField.get(brewingStandBE);

                for (int i = 0; i < 3; i++) {
                    ItemStack stack = slots.get(i);
                    if (stack.isEmpty()) continue;

                    var contents = stack.get(DataComponents.POTION_CONTENTS);
                    if (contents == null || contents.potion().isEmpty()) continue;

                    ItemStack copy = stack.copy();
                    var effects = contents.getAllEffects();
                    if (data.getPlayerSkills().contains("alchemist_skill_4_1")) {
                        effects.forEach(effect -> {
                            if (effect.getEffect().value().isBeneficial()) {
                                effect.update(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier()+1));
                            }
                        });
                    } else if (data.getPlayerSkills().contains("alchemist_skill_4_2")) {
                        effects.forEach(effect -> {
                            if (!effect.getEffect().value().isBeneficial()) {
                                effect.update(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier()+1));
                            }
                        });
                    }
                    copy.set(DataComponents.POTION_CONTENTS, contents.withEffectAdded(
                            new MobEffectInstance(MobEffects.REGENERATION, 200, 0)
                    ));
                    slots.set(i, copy);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void onAlchemistSkill3(ServerPlayer player, PlayerClassData data) {
                if (data.getPlayerSkills().contains("alchemist_skill_3")) {
                    if (data.isActive1Enabled() && !data.isActive1onCooldown()) {
                        Entity entity = getEntityPlayerIsLookingAt(player, 10);
                        if (entity instanceof LivingEntity living) {
                            var effects = living.getActiveEffects();
                            effects.forEach(effect -> {
                                if (!effect.getEffect().value().isBeneficial()) {
                                    living.removeEffect(effect.getEffect());
                                }
                            });
                            data.setActive1Cooldown(true);
                            syncClasses(player);
                            ServerScheduler.schedule(1200, () -> {
                                data.setActive1Cooldown(false);
                                syncClasses(player);
                            });
                        }


                    }
                }
            }
        }







    public static class ApplyChanges {
        public static final ResourceLocation PALADIN_SKILL_1 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a5a-4e3e-9d3b-1a8c92d6c112")));


        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            if (player_skills.contains("paladin_skill_1")) {
                AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);



                if (attribute != null && !attribute.hasModifier(PALADIN_SKILL_1)) {
                    AttributeModifier modifier = new AttributeModifier(
                            PALADIN_SKILL_1,
                            2,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }

        }
    }
}
