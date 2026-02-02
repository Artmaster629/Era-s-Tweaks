package net.artmaster.era_tweaks.custom.player_classes.assistant.smith;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.artmaster.era_tweaks.utils.ServerScheduler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

public class Smith {


    @EventBusSubscriber(modid = ModMain.MODID)
    public static class Actions {

        public static final ResourceLocation BLOCK_REACH_MODIFIER_SMITH =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a5a-4e3e-9d3b-4a8c92d6c112")));



        public static final ResourceLocation ARMOR_MODIFIER_SMITH =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.fromString("a3c4f52b-2a5a-4e3e-9d3b-3a8c92d6c112")));






        public static ItemStack applyChangesToItem(ServerPlayer player, ItemStack stack) {
            if (!stack.has(DataComponents.ATTRIBUTE_MODIFIERS)) return stack;
            if (!(stack.getItem() instanceof DiggerItem)) return stack;
            System.out.println("digger");

            var data = player.getData(ModAttachments.PLAYER_CLASS);
            if (data.getPlayerSkills().contains("smith_skill_1")) {
                var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (modifiers == null) return stack;

                ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

                    for (var entry : modifiers.modifiers()) {
                        var attribute = entry.attribute();
                        var modifier = entry.modifier();
                        var slot = entry.slot();

                        if (attribute.equals(Attributes.ATTACK_DAMAGE)
                                && modifier.id().equals(ResourceLocation.withDefaultNamespace("base_attack_damage"))) {
                            double newDamage = modifier.amount() + 1;

                            builder.add(
                                    attribute,
                                    new AttributeModifier(
                                            modifier.id(),
                                            newDamage,
                                            modifier.operation()
                                    ),
                                    slot
                            );
                        } else {
                            // остальные модификаторы как есть
                            builder.add(attribute, modifier, slot);
                        }
                    }

                ///CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean("smith_skill_1", true));
                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());

            }
            if (data.getPlayerSkills().contains("smith_skill_2_1")) {
                var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (modifiers == null) return stack;

                ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

                for (var entry : modifiers.modifiers()) {
                    var attribute = entry.attribute();
                    var modifier = entry.modifier();
                    var slot = entry.slot();

                    if (attribute.equals(Attributes.ATTACK_DAMAGE)
                            && modifier.id().equals(ResourceLocation.withDefaultNamespace("base_attack_damage"))) {
                        double newDamage = modifier.amount() * 1.25;

                        builder.add(
                                attribute,
                                new AttributeModifier(
                                        modifier.id(),
                                        newDamage,
                                        modifier.operation()
                                ),
                                slot
                        );
                    }
                    if (attribute.equals(Attributes.ATTACK_SPEED)
                            && modifier.id().equals(ResourceLocation.withDefaultNamespace("base_attack_speed"))) {
                        double newDamage = modifier.amount() * 1.3;

                        builder.add(
                                attribute,
                                new AttributeModifier(
                                        modifier.id(),
                                        newDamage,
                                        modifier.operation()
                                ),
                                slot
                        );
                    }
                    else {
                        // остальные модификаторы как есть
                        builder.add(attribute, modifier, slot);
                    }
                }

                ///CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean("smith_skill_1", true));
                stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());

            }
            if (data.getPlayerSkills().contains("smith_skill_2_2")) {


                var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (modifiers == null) return stack;
                if (!(stack.getItem() instanceof DiggerItem)) return stack;

                ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

                var attribute_range = Attributes.BLOCK_INTERACTION_RANGE;

                builder.add(
                        attribute_range,
                        new AttributeModifier(
                                BLOCK_REACH_MODIFIER_SMITH,
                                2,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                );



                for (var entry : modifiers.modifiers()) {
                    var attribute = entry.attribute();
                    var modifier = entry.modifier();
                    var slot = entry.slot();

                    // остальные модификаторы как есть
                    builder.add(attribute, modifier, slot);
                }

                ///CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean("smith_skill_2", true));
                stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
            }
            if (data.getPlayerSkills().contains("smith_skill_4")) {
                var modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (modifiers == null) return stack;
                if (!stack.isDamageableItem()) return stack;

                Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
                if (maxDamage != null) {
                    int newMax = (int) (maxDamage * 1.1);

                    stack.set(DataComponents.MAX_DAMAGE, newMax);
                }


            }
            return stack;

        }

        @SubscribeEvent
        public static void onSmithSkill5(PlayerInteractEvent.EntityInteract event) {
            if (event.getTarget() instanceof ServerPlayer target && event.getEntity() instanceof ServerPlayer player) {
               var data = player.getData(ModAttachments.PLAYER_CLASS);
               if (player.isShiftKeyDown()) {
                   if (data.getPlayerSkills().contains("smith_skill_5")) {
                       ItemStack head  = target.getItemBySlot(EquipmentSlot.HEAD);
                       ItemStack chest = target.getItemBySlot(EquipmentSlot.CHEST);
                       ItemStack legs  = target.getItemBySlot(EquipmentSlot.LEGS);
                       ItemStack feet  = target.getItemBySlot(EquipmentSlot.FEET);



                       int currentHeadDamage = head.getDamageValue();
                       int currentChestDamage = chest.getDamageValue();
                       int currentLegsDamage = legs.getDamageValue();
                       int currentFeetDamage = feet.getDamageValue();


                       for (ItemStack stack : player.getInventory().items) {
                           if (stack.getItem().equals(Items.IRON_INGOT) && stack.getCount() >= 4) {
                               if (target.distanceToSqr(player) <= 2) {
                                   ServerScheduler.schedule(100, () -> {
                                       if (target.distanceToSqr(player) <= 2) {
                                           head.setDamageValue((int) (currentHeadDamage*0.75));
                                           chest.setDamageValue((int) (currentChestDamage*0.75));
                                           legs.setDamageValue((int) (currentLegsDamage*0.75));
                                           feet.setDamageValue((int) (currentFeetDamage*0.75));
                                       }
                                   });

                               }

                           }
                       }



                   }
               }

            }
        }

        @SubscribeEvent
        public static void onSmithSkill1(LivingEquipmentChangeEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                if (data.getPlayerSkills().contains("smith_skill_1")) {
                    AttributeInstance attribute = player.getAttribute(Attributes.ARMOR);
                    AttributeModifier modifier = new AttributeModifier(
                            ARMOR_MODIFIER_SMITH,
                            1,
                            AttributeModifier.Operation.ADD_VALUE
                    );

                    if (event.getFrom().getItem().equals(Items.AIR)) {
                        if (attribute == null) {
                            attribute.addTransientModifier(modifier);
                        }

                    }
                    else if (event.getTo().getItem().equals(Items.AIR)) {
                        if (attribute != null) {
                            attribute.removeModifier(modifier);
                        }

                    }
                }
            }
        }

    }

    public static class ApplyChanges {
        public static final ResourceLocation SMITH_FIRE_RESISTANCE =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "smith_fire_resistance");

        public static final ResourceLocation SMITH_PROJECTILE_RESISTANCE =
                ResourceLocation.fromNamespaceAndPath("era_tweaks", "smith_projectile_resistance");


        public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
            var player_skills = data.getPlayerSkills();
            if (player_skills.contains("smith_skill_3_1")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.PROJECTILE_RESISTANCE);



                if (attribute != null && !attribute.hasModifier(SMITH_PROJECTILE_RESISTANCE)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SMITH_PROJECTILE_RESISTANCE,
                            30,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }

            if (player_skills.contains("smith_skill_3_2")) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.FIRE_RESISTANCE);



                if (attribute != null && !attribute.hasModifier(SMITH_FIRE_RESISTANCE)) {
                    AttributeModifier modifier = new AttributeModifier(
                            SMITH_FIRE_RESISTANCE,
                            30,
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    attribute.addPermanentModifier(modifier);
                }
            }

        }
    }
}
