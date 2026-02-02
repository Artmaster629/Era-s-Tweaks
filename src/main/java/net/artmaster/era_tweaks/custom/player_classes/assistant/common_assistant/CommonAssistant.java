package net.artmaster.era_tweaks.custom.player_classes.assistant.common_assistant;

import it.crystalnest.harvest_with_ease.api.event.HarvestEvents;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.network.Network;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.registry.ModAttributes;
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
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.UUID;

public class CommonAssistant {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {

        @SubscribeEvent
        public static void onWaterBreaking(PlayerEvent.BreakSpeed event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("common_assistant_skill_5")) {
                    event.setNewSpeed(event.getOriginalSpeed()/5);
                }
            }
        }



    }

    public static class ApplyChanges {

        public static final ResourceLocation ASSISTANT_HEALTH =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "assistant_health");

        public static final ResourceLocation ASSISTANT_BREAK_SPEED =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "assistant_break_speed");

        public static final ResourceLocation ASSISTANT_OXYGEN_BONUS =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "assistant_oxygen_bonus");

        public static final ResourceLocation ASSISTANT_XP_ATTRIBUTE =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a4c4f52b-2a6a-4e3e-9d3b-1a8c92d6c111")));



        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            AttributeInstance attributeHealth = player.getAttribute(Attributes.MAX_HEALTH);
            AttributeInstance attributeXP = player.getAttribute(ModAttributes.UPGRADE_POINT_XP);
            AttributeInstance blockBreakSpeed = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);


            if (data.getPlayerClass().equals("assistant")) {
                if (attributeHealth != null && !attributeHealth.hasModifier(ASSISTANT_HEALTH)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_HEALTH,
                            -2,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeHealth.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_assistant_skill_1")) {
                if (attributeXP != null && !attributeXP.hasModifier(ASSISTANT_XP_ATTRIBUTE)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_XP_ATTRIBUTE,
                            5,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeXP.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_assistant_skill_2")) {
                if (blockBreakSpeed != null && !blockBreakSpeed.hasModifier(ASSISTANT_BREAK_SPEED)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_BREAK_SPEED,
                            0.1,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    blockBreakSpeed.addPermanentModifier(modifier);
                }
            }
            if (player_skills.contains("common_assistant_skill_3")) {
                if (attributeHealth != null && !attributeHealth.hasModifier(ASSISTANT_HEALTH)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_HEALTH,
                            1.5,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeHealth.addPermanentModifier(modifier);
                }
            }

            if (player_skills.contains("common_assistant_skill_4")) {
                if (blockBreakSpeed != null && !blockBreakSpeed.hasModifier(ASSISTANT_BREAK_SPEED)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_BREAK_SPEED,
                            0.15,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    blockBreakSpeed.addPermanentModifier(modifier);
                }
            }

            if (player_skills.contains("common_assistant_skill_5")) {
                AttributeInstance oxygenBonus = player.getAttribute(Attributes.OXYGEN_BONUS);
                if (oxygenBonus != null && !oxygenBonus.hasModifier(ASSISTANT_OXYGEN_BONUS)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_OXYGEN_BONUS,
                            0.25,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    );
                    oxygenBonus.addPermanentModifier(modifier);
                }
            }




            if (player_skills.contains("common_assistant_skill_10")) {
                if (attributeXP != null && !attributeXP.hasModifier(ASSISTANT_XP_ATTRIBUTE)) {
                    AttributeModifier modifier = new AttributeModifier(
                            ASSISTANT_XP_ATTRIBUTE,
                            10,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attributeXP.addPermanentModifier(modifier);
                }
            }

        }
    }
}
