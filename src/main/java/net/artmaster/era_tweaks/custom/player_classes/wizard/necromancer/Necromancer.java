package net.artmaster.era_tweaks.custom.player_classes.wizard.necromancer;


import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import team.creative.playerrevive.client.ReviveEventClient;
import team.creative.playerrevive.server.ReviveEventServer;

public class Necromancer {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {
        @SubscribeEvent
        public static void onNecromancerSkill2(LivingChangeTargetEvent event) {
            if (event.getEntity() instanceof Mob mob) {
                if (event.getNewAboutToBeSetTarget() instanceof Player player) {
                    var data = player.getData(ModAttachments.PLAYER_CLASS);
                    if (data.getPlayerSkills().contains("necromancer_skill_2")) {
                        if (mob.getLastHurtByMob() != player) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }



        @SubscribeEvent
        public static void onNecromancerSkill4_1(LivingDamageEvent.Post event) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("necromancer_skill_4_1")) {
                    var target = event.getEntity();
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 50));
                }
            }
        }
        @SubscribeEvent
        public static void onNecromancerSkill4_2(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("necromancer_skill_4_2")) {
                    if (player.hasEffect(MobEffects.WITHER)) {
                        player.removeEffect(MobEffects.WITHER);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onNecromancerSkill3(PlayerTickEvent.Post event) {
            if (event.getEntity() instanceof ServerPlayer player) {
               var data = player.getData(ModAttachments.PLAYER_CLASS);
               if (data.getPlayerSkills().contains("necromancer_skill_3")) {
                   MagicData magicData = MagicData.getPlayerMagicData(player);
                   if (magicData.getMana() >= 75) {
                       if (data.isActive1Enabled() && !data.isActive1onCooldown()) {
                           Network.toClientAction(player, "", 1);
                           data.setActive1Cooldown(true);




                           player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (100*magicData.getMana()), 2));
                           player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, (int) (100*magicData.getMana()), 2));
                           player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, (int) (100*magicData.getMana()), 1));

                           magicData.setMana(magicData.getMana()-75);

                           ServerScheduler.schedule(12000, () -> {
                               data.setActive1Cooldown(false);
                           });
                       }
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
