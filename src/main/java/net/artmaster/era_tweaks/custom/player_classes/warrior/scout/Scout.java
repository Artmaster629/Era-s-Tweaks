package net.artmaster.era_tweaks.custom.player_classes.warrior.scout;


import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class Scout {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {
        @SubscribeEvent
        public static void onScoutSkill2_1(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("scout_skill_2_1")) {
                    player.addEffect((new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1)));
                }
            }
        }

        @SubscribeEvent
        public static void onUberAdding(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var uber = player.getData(ModAttachments.UBER);
                if (uber.getUberProgress() < 100 && uber.isSentSignalToStart()) {
                    uber.addUberProgress(0.05);
                    //System.out.println(uber.getUberProgress()+", "+uber.isSentSignalToStart());
                    Network.syncUberWithOverlay(player);
                }

            }
        }

        @SubscribeEvent
        public static void onScoutSkill2_2Death(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("scout_skill_2_2")) {
                    var uber = player.getData(ModAttachments.UBER);
                    if (!uber.isSentSignalToStart()) {
                        uber.addSignal();
                    }

                }
            }
        }

        @SubscribeEvent
        public static void ActionScoutSkill3(LivingDamageEvent.Pre event) {
            var source = event.getSource().getEntity();
            if (source instanceof ServerPlayer player) {
                var target = event.getEntity();
                if (target instanceof Player) {
                    var data = player.getData(ModAttachments.PLAYER_CLASS);
                    if (data.getPlayerSkills().contains("scout_skill_3")) {
                        float newDamage = event.getOriginalDamage()*1.5f;
                        event.setNewDamage(newDamage);
                    }
                }
                //System.out.println(event.getNewDamage());
            }
        }

        public static boolean isTargetBackToPlayer(LivingEntity target, Player player) {

            // Взгляд цели (горизонтально)
            Vec3 targetLook = target.getLookAngle();
            targetLook = new Vec3(targetLook.x, 0, targetLook.z).normalize();

            // Вектор от цели к игроку
            Vec3 toPlayer = player.position()
                    .subtract(target.position());
            toPlayer = new Vec3(toPlayer.x, 0, toPlayer.z).normalize();

            double dot = targetLook.dot(toPlayer);

            // dot < 0 → цель смотрит ОТ игрока
            return dot < -0.3;
        }

        @SubscribeEvent
        public static void onDamage(LivingDamageEvent.Pre event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;
            if (!(event.getEntity() instanceof LivingEntity target)) return;

            var data = player.getData(ModAttachments.PLAYER_CLASS);
            if (data.getPlayerSkills().contains("scout_skill_4")) {
                if (isTargetBackToPlayer(target, player)) {

                    event.setNewDamage(event.getOriginalDamage() * 1.5f); // backstab
                }
            }
        }

        @SubscribeEvent
        public static void onScoutSkill2_2Attack(LivingDamageEvent.Pre event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("scout_skill_2_2")) {
                    var uber = player.getData(ModAttachments.UBER);
                    if (uber.getUberProgress() >= 100) {
                        event.setNewDamage(event.getOriginalDamage()*3);
                        ServerScheduler.schedule(2, () -> {
                            uber.removeSignal();
                            uber.setUberProgress(0);
                            Network.syncUberWithOverlay(player);
                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onScoutSkill5_2Attack(LivingDamageEvent.Pre event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                var target = event.getEntity();
                if (data.getPlayerSkills().contains("scout_skill_5_2")) {
                    if (player.getHealth() <= player.getMaxHealth()/3) {
                        if (data.isActive1Enabled() && !data.isActive1onCooldown()) {
                            data.setActive1(false);
                            data.setActive1Cooldown(true);

                            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 240));
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 240));


                            ServerScheduler.schedule(1200, () -> {
                                data.setActive1Cooldown(false);
                            });

                        }
                    }

                }
            }
        }
    }

    public static class ApplyChanges {
        public static final ResourceLocation SCOUT_SNEAK_SPEED =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "scout_sneak_speed");
        public static final ResourceLocation SCOUT_MOVE_SPEED =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "scout_move_speed");
        public static final ResourceLocation SCOUT_MAX_HEALTH =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "scout_max_health");
        public static final ResourceLocation SCOUT_ATTACK_DAMAGE =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "scout_attack_damage");


        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            if (player_skills.contains("scout_skill_1")) {
                AttributeInstance attributeSneaking = player.getAttribute(Attributes.SNEAKING_SPEED);
                AttributeInstance attributeMovement = player.getAttribute(Attributes.MOVEMENT_SPEED);
                AttributeInstance attributeHealth = player.getAttribute(Attributes.MAX_HEALTH);
                AttributeInstance attributeDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);

                if (attributeSneaking != null && !attributeSneaking.hasModifier(SCOUT_SNEAK_SPEED)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SCOUT_SNEAK_SPEED,
                            0.5,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    attributeSneaking.addPermanentModifier(modifier);
                }
                if (attributeMovement != null && !attributeMovement.hasModifier(SCOUT_MOVE_SPEED)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SCOUT_MOVE_SPEED,
                            0.3,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    attributeMovement.addPermanentModifier(modifier);
                }
                if (attributeHealth != null && !attributeHealth.hasModifier(SCOUT_MAX_HEALTH)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SCOUT_MAX_HEALTH,
                            -4,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeHealth.addPermanentModifier(modifier);
                }
                if (attributeDamage != null && !attributeDamage.hasModifier(SCOUT_ATTACK_DAMAGE)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SCOUT_ATTACK_DAMAGE,
                            0.25,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    attributeDamage.addPermanentModifier(modifier);
                }
            }

        }
    }
}
