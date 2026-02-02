package net.artmaster.era_tweaks.custom.player_classes.warrior.paladin;


import io.redspace.ironsspellbooks.api.events.CounterSpellEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class Paladin {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {
        @SubscribeEvent
        public static void ActionPaladinSkill5(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                MagicData magicData = MagicData.getPlayerMagicData(player);
                if (!data.getPlayerSkills().contains("paladin_skill_5_1") && !data.getPlayerSkills().contains("paladin_skill_5_2")) return;
                if (data.isActive2Enabled() && magicData.getMana() > 10) {

                    magicData.setMana(magicData.getMana()-0.18F);
                    AABB box = player.getBoundingBox().inflate(12);
                    List<Entity> entities = player.level().getEntities(player, box);



                    for (Entity entity : entities) {
                        if (entity instanceof LivingEntity living) {
                            if (living instanceof ServerPlayer) {
                                Holder<MobEffect> effect = null;

                                if (data.getPlayerSkills().contains("paladin_skill_5_1")) {
                                    effect = MobEffects.DAMAGE_BOOST;
                                }
                                if (data.getPlayerSkills().contains("paladin_skill_5_2")) {
                                    effect = MobEffects.DAMAGE_RESISTANCE;
                                }
                                if (effect != null) {
                                    living.addEffect(new MobEffectInstance(
                                            effect,
                                            120,
                                            0,
                                            true,
                                            false // не показывать частицы
                                    ));
                                }


                            }

                        }
                    }
                    if (magicData.getMana() <= 10) {
                        data.setActive1(false);
                    }

                }
            }
        }

        @SubscribeEvent
        public static void ActionPaladinSkill4_1(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                MagicData magicData = MagicData.getPlayerMagicData(player);
                if (data.getPlayerSkills().contains("paladin_skill_4_1") && data.isActive1Enabled() && magicData.getMana() > 10) {

                    magicData.setMana(magicData.getMana()-0.12F);
                    AABB box = player.getBoundingBox().inflate(6);
                    List<Entity> entities = player.level().getEntities(player, box);

                    for (Entity entity : entities) {
                        if (!(entity instanceof LivingEntity living)) continue;
                        if (!(entity instanceof Monster)) continue;

                        living.addEffect(new MobEffectInstance(
                                MobEffects.GLOWING,
                                20,
                                0,
                                true,
                                false // не показывать частицы
                        ));


                    }
                    if (magicData.getMana() <= 10) {
                        data.setActive1(false);
                    }

                }
            }
        }

        @SubscribeEvent
        public static void ActionPaladinSkill4_2(PlayerInteractEvent.RightClickBlock event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);

                if (data.getPlayerSkills().contains("paladin_skill_4_2") && data.isActive1Enabled() && !data.isActive1onCooldown()) {
                    AABB box = player.getBoundingBox().inflate(3);
                    List<Entity> entities = player.level().getEntities(player, box);

                    for (Entity entity : entities) {
                        if (!(entity instanceof LivingEntity living)) continue;
                        if (!(entity instanceof Monster)) continue;
                        if (player.level() instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(
                                    ParticleTypes.SWEEP_ATTACK,
                                    player.getX(), player.getY() + 1, player.getZ(),
                                    10,
                                    1, 0.2, 1,
                                    0
                            );
                            serverLevel.playSound(
                                    null,
                                    player.blockPosition(),
                                    SoundEvents.LIGHTNING_BOLT_IMPACT,
                                    SoundSource.PLAYERS,
                                    1.0F,
                                    1.0F
                            );
                            living.hurt(
                                    player.damageSources().playerAttack(player),
                                    1.0F
                            );
                            living.igniteForTicks(60);
                        }
                    }
                    data.setActive1Cooldown(true);
                    ServerScheduler.schedule(160, () -> data.setActive1Cooldown(false));
                }
            }
        }

        @SubscribeEvent
        public static void ActionPaladinSkill3(LivingDamageEvent.Pre event) {
            var source = event.getSource().getEntity();
            if (source instanceof ServerPlayer player) {
                var target = event.getEntity();
                if (target instanceof Monster) {
                    var data = player.getData(ModAttachments.PLAYER_CLASS);
                    if (data.getPlayerSkills().contains("paladin_skill_3")) {
                        float newDamage = event.getOriginalDamage()*1.5f;
                        event.setNewDamage(newDamage);
                    }
                }
                //System.out.println(event.getNewDamage());
            }
        }

        @SubscribeEvent
        public static void ActionPaladinSkill2_2(CounterSpellEvent event) {

            if (event.caster instanceof Player player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (!data.getPlayerSkills().contains("paladin_skill_2_2")) event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void ActionPaladinSkill2_1(SpellPreCastEvent event) {
            if (event.getEntity() instanceof Player player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (event.getSchoolType().getId().toString().equals("irons_spellbooks:holy")) {
                    if (!data.getPlayerSkills().contains("paladin_skill_2_1")) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    public static class ApplyChanges {
        public static final ResourceLocation PALADIN_MAX_HEALTH =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "paladin_max_health");

        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            if (player_skills.contains("paladin_skill_1")) {
                AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);

                if (attribute != null && !attribute.hasModifier(PALADIN_MAX_HEALTH)) {
                    AttributeModifier modifier = new AttributeModifier(
                            PALADIN_MAX_HEALTH,
                            2,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }

        }
    }
}
