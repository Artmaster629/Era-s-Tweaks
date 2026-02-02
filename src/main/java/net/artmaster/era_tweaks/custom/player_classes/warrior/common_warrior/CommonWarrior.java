package net.artmaster.era_tweaks.custom.player_classes.warrior.common_warrior;

import it.crystalnest.harvest_with_ease.api.event.HarvestEvents;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.List;
import java.util.UUID;

public class CommonWarrior {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {





        @SubscribeEvent
        public static void onPlayerDead(LivingDeathEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS).getPlayerSkills();

                if (data.contains("common_warrior_skill_9")) {
                    double random = Math.random()*100;
                    if (random > 50) {
                        event.setCanceled(true);
                        event.getEntity().setHealth(event.getEntity().getMaxHealth()/2);

                        if (player.level() instanceof ServerLevel serverLevel) {
                            Network.toClientAction(player, "", 1);
                            serverLevel.playSound(
                                    null,
                                    player.blockPosition(),
                                    SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                                    SoundSource.PLAYERS,
                                    1.0F,
                                    1.0F
                            );
                        }
                    }
                }
            }
        }



        @SubscribeEvent
        public static void onCropAddXp(HarvestEvents.HarvestDropsEvent event) {
            List<ItemStack> drops = event.getDrops();
            ItemStack cropItem = drops.getFirst();

            var data = event.getEntity().getData(ModAttachments.PLAYER_SKILLS);
            double multiplier = data.getBodyLevel();
            if (multiplier >= 1) {
                long random = Math.round(Math.random()*100);
                if (random < multiplier) {
                    cropItem.setCount(cropItem.getCount()+1);
                    long random_2 = Math.round(Math.random()*100);
                    if (random_2 <= 40 && random_2 > 15) {
                        cropItem.setCount(cropItem.getCount()+1);
                    }
                    if (random_2 <= 15 && random_2 > 0) {
                        cropItem.setCount(cropItem.getCount()+2);
                    }
                }

            }


        }

    }

    public static class ApplyChanges {
        public static final ResourceLocation COMMON_WARRIOR_SKILL1 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a5a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL2 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a6a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL3 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a7a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL4 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a8a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL5 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a9a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL6 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-3a1a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL7 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-3a2a-4e3e-9d3b-1a8c92d6c111")));
        public static final ResourceLocation COMMON_WARRIOR_SKILL8 =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-3a3a-4e3e-9d3b-1a8c92d6c111")));



        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            if (player_skills.contains("common_warrior_skill_1")) {
                AttributeInstance attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL1)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL1,
                            1.5,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_2")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.MIN_JUMP_HEIGHT);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL2)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL2,
                            1,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }

            }
            if (player_skills.contains("common_warrior_skill_3")) {
                AttributeInstance attribute = player.getAttribute(Attributes.ARMOR);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL3)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL3,
                            4,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_4")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.DOUBLE_DROP_CHANCE);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL4)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL4,
                            10,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_5")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.EXPLOSION_RESISTANCE);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL5)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL5,
                            10,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_6")) {
                AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL6)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL6,
                            4,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_7")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.ARMOR_DURABILITY);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL7)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL7,
                            50,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_warrior_skill_8")) {
                AttributeInstance attribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

                if (attribute != null && !attribute.hasModifier(COMMON_WARRIOR_SKILL8)) {
                    AttributeModifier modifier = new AttributeModifier(
                            COMMON_WARRIOR_SKILL8,
                            20,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }
        }
    }
}
