package net.artmaster.era_tweaks.custom.player_classes.wizard.priest;


import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntity;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.registry.ModBlocks;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import javax.annotation.Nullable;

import static net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntity.getLookHit;

public class Priest {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {

        public static void onPriestSkill1_And_4(ServerPlayer player, ServerLevel level, PlayerClassData data) {
            Entity entity = getEntityPlayerIsLookingAt(player, 10);
            if (entity instanceof LivingEntity living) {
                int ticks = 1800;
                if (data.getPlayerSkills().contains("priest_skill_4")) {
                    living.heal(4);
                    var effects = living.getActiveEffects();

                    effects.forEach(effect -> {
                        if (!effect.getEffect().value().isBeneficial()) {
                            living.removeEffect(effect.getEffect());
                        }
                    });

                    living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 4));
                    living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 500, 3));

                    ticks=ticks/2;
                } else {
                    living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 2));
                    living.addEffect(new MobEffectInstance(MobEffects.HEAL, 140));
                }

                data.setActive1Cooldown(true);

                ServerScheduler.schedule(ticks, () -> {
                    data.setActive1Cooldown(false);
                });
            }
        }




        public static void onPriestSkill2(ServerPlayer player, ServerLevel level) {
            HitResult hit = getLookHit(player, 50);

            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) hit).getBlockPos().above();
                var classData = player.getData(ModAttachments.PLAYER_CLASS);
                var totemData = player.getData(ModAttachments.TOTEM_DATA);
                if (totemData.getPos() != null) {
                    level.removeBlock(totemData.getPos(), false);
                    level.removeBlockEntity(totemData.getPos());
                }

                level.setBlock(pos, ModBlocks.TOTEM_BLOCK.get().defaultBlockState(), 3);
                if (level.getBlockEntity(pos) instanceof TotemBlockEntity totemBE) {

                    classData.setActive2(false);

                    totemBE.setOwner(player.getUUID());
                    player.sendSystemMessage(Component.literal("Вы установили тотем!"));
                    totemData.setNewPos(pos);
                }
            }
        }

        public static void onPriestSkill5_1(ServerPlayer player, ServerLevel level, PlayerClassData data) {
            HitResult hit = getLookHit(player, 50);

            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = ((BlockHitResult) hit).getBlockPos().above();

                LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(pos));
                level.addFreshEntity(entityToSpawn);

                var aabb = player.getBoundingBox().inflate(5);
                var entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
                DamageSource damageSourceHolder = new DamageSource(level.holderOrThrow(DamageTypes.PLAYER_ATTACK));
                for (LivingEntity entity : entities) {
                    entity.hurt(damageSourceHolder, 7);
                    if (!(entity == player)) continue;
                    entity.igniteForTicks(120);
                }





                data.setActive4Cooldown(true);
                ServerScheduler.schedule(60, () -> {
                    data.setActive4Cooldown(false);
                });
            }
        }

        @SubscribeEvent
        public static void onPriestSkill5_2(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("priest_skill_5_2") && data.isActive4Enabled() && !data.isActive4onCooldown()) {
                    Entity target = getEntityPlayerIsLookingAt(player, 10.0D);
                    if (target == null) {
                        data.setActive4Cooldown(true);
                        ServerScheduler.schedule(1600, () -> {
                            data.setActive4Cooldown(false);
                        });
                    }
                    if (target instanceof LivingEntity living) { //потом заменить на игрока


                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4));

                        living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2));
                        living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4));

                    }
                }
            }
        }



       @SubscribeEvent
        public static void onPriestSkill3(PlayerTickEvent.Post event) {
           if (event.getEntity() instanceof ServerPlayer player) {
               var data = player.getData(ModAttachments.PLAYER_CLASS);
               if (data.getPlayerSkills().contains("priest_skill_3") && data.isActive3Enabled() && !data.isActive3onCooldown()) {
                   Entity target = getEntityPlayerIsLookingAt(player, 10.0D);
                   if (target == null) {
                       data.setActive3Cooldown(true);
                       ServerScheduler.schedule(1600, () -> {
                           data.setActive3Cooldown(false);
                       });
                   }
                   if (target instanceof LivingEntity living) { //потом заменить на игрока


                       player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2));
                       player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4));

                       living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2));
                       living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 4));

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



    @Nullable
    public static Entity getEntityPlayerIsLookingAt(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = eyePos.add(lookVec.scale(distance));

        AABB searchBox = player.getBoundingBox()
                .expandTowards(lookVec.scale(distance))
                .inflate(1.0D); // чуть шире, чтобы не промахиваться

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable()
        );

        return result != null ? result.getEntity() : null;
    }
}
