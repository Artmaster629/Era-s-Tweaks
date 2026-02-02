package net.artmaster.era_tweaks.custom.player_classes.warrior.bowman;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import java.util.UUID;

import static net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom.MarkTeam.mark;
import static net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom.MarkTeam.unmark;

public class Bowman {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {



        @SubscribeEvent
        public static void ActionBowmanSkill1(LivingDamageEvent.Pre event) {
            var source = event.getSource().getEntity();
            if (event.getSource().getDirectEntity() instanceof AbstractArrow) {
                if (source instanceof ServerPlayer player) {
                    var data = player.getData(ModAttachments.PLAYER_CLASS);
                    var deathPointData = player.getData(ModAttachments.DEATH_POINT);
                    if (data.getPlayerSkills().contains("bowman_skill_1")) {
                        Entity marked = deathPointData.getEntity(player.level());
                        Entity target = event.getEntity();
                        //System.out.println("targeting...");

                        if (marked == null) {
                            //System.out.println("marked = null");
                            deathPointData.setEntity(target);
                            mark((ServerLevel) player.level(), target);
                            return;
                        }

                        if (marked == target) {
                            //System.out.println("marked = target");
                            event.setNewDamage(event.getOriginalDamage() * 1.5f);
                        } else {
                            //System.out.println("marked != target");
                            unmark((ServerLevel) player.level(), marked);
                            deathPointData.setEntity(target);
                            mark((ServerLevel) player.level(), target);
                        }
                    }
                }
            }

        }

        @SubscribeEvent
        public static void ActionBowmanSkill3(LivingDamageEvent.Pre event) {
            var source = event.getSource().getEntity();
            if (source instanceof ServerPlayer player && event.getSource().getDirectEntity() instanceof AbstractArrow) {
                var target = event.getEntity();
                if (target instanceof Animal) {
                    var data = player.getData(ModAttachments.PLAYER_CLASS);
                    if (data.getPlayerSkills().contains("bowman_skill_3")) {
                        float newDamage = event.getOriginalDamage()*(1f + 0.35f);
                        event.setNewDamage(newDamage);
                    }
                }
            }

        }

        @SubscribeEvent
        public static void ActionBowmanSkill5(LivingDamageEvent.Post event) {
            var source = event.getSource().getEntity();
            if (source instanceof ServerPlayer player && event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
                var target = event.getEntity();
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("bowman_skill_5")) {
                    var level = player.level();
                    if (player.getHealth() <= player.getMaxHealth()/3 && data.isActive1Enabled()) {
                        CompoundTag tag = new CompoundTag();
                        arrow.saveWithoutId(tag);

                        tag.remove("UUID");
                        tag.remove("Id");

                        ServerScheduler.schedule(4, () -> {
                            AbstractArrow newArrow = (AbstractArrow) arrow.getType().create(level);
                            if (newArrow == null) return;

                            newArrow.load(tag);

                            newArrow.setPos(arrow.getX(), arrow.getY(), arrow.getZ());

                            level.addFreshEntity(newArrow);
                        });
                    }




                }
            }

        }

        @SubscribeEvent
        public static void ActionBowmanSkill4(LivingDamageEvent.Post event) {
            var source = event.getSource().getEntity();
            if (source instanceof ServerPlayer player && event.getSource().getDirectEntity() instanceof AbstractArrow arrow) {
                var target = event.getEntity();
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("bowman_skill_4")) {



                    for (ItemStack itemStack : player.getInventory().items) {
                        if (itemStack.getItem() instanceof LingeringPotionItem) {
                            PotionContents contents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                            if (!contents.hasEffects()) return;
                            var level = target.level();

                            double radius = 4.0;

                            var aabb = target.getBoundingBox().inflate(radius);
                            var entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
                            for (LivingEntity entity : entities) {
                                if (entity == player) continue;
                                double distance = entity.distanceTo(target);
                                if (distance > 4.0) continue;

                                double power = 1.0 - (distance / 4.0);
                                if (power <= 0) continue;

                                contents.forEachEffect(effect -> {
                                    Holder<MobEffect> mobEffectHolder = effect.getEffect();
                                    MobEffect mobEffect = effect.getEffect().value();

                                    if (mobEffect.isInstantenous()) {
                                        mobEffect.applyInstantenousEffect(
                                                player,     // стрелявший
                                                player,
                                                entity,
                                                effect.getAmplifier(),
                                                power
                                        );
                                    } else {
                                        int duration = (int)(power * effect.getDuration() + 0.5D);

                                        if (duration > 20) { // меньше 1 секунды — не накладывается
                                            entity.addEffect(new MobEffectInstance(
                                                    mobEffectHolder,
                                                    duration,
                                                    effect.getAmplifier(),
                                                    effect.isAmbient(),
                                                    effect.isVisible(),
                                                    effect.showIcon()
                                            ));
                                        }
                                    }
                                });
                            }
                            itemStack.shrink(1);
                            break;
                        }
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
