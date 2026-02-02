package net.artmaster.era_tweaks.custom.events;


import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;

@EventBusSubscriber(modid = ModMain.MODID)
public class AttributesActions {
//
//    public static CompoundTag createBlessedFood(int bonusFood, float bonusSaturation) {
//
//
//
//        return tag;
//    }
    @SubscribeEvent
    public static void onItemEaten(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack stack = event.getItem();
        if (!stack.has(DataComponents.FOOD)) return;

        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return;

        CompoundTag tag = data.copyTag(); // важно: copy!

        int bonusFood = tag.getInt("BonusFood");
        float bonusSaturation = tag.getFloat("BonusSaturation");

        if (bonusFood != 0 || bonusSaturation != 0) {
            player.getFoodData().eat(bonusFood, bonusSaturation);
        }
    }

    @SubscribeEvent
    public static void onProjectile(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.PROJECTILE_RESISTANCE);

                float reduction = (float) (attribute.getValue() / 100.0);
                float newDamage = event.getOriginalDamage() * (1.0f - reduction);

                event.setNewDamage(newDamage);
                System.out.println("OLD DAMAGE: "+event.getOriginalDamage()+", NEW DAMAGE: "+event.getNewDamage());
            }
        }
    }

    @SubscribeEvent
    public static void onFire(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
                AttributeInstance attribute = player.getAttribute(ModAttributes.FIRE_RESISTANCE);

                float reduction = (float) (attribute.getValue() / 100.0);
                float newDamage = event.getOriginalDamage() * (1.0f - reduction);

                event.setNewDamage(newDamage);
                System.out.println("OLD DAMAGE: "+event.getOriginalDamage()+", NEW DAMAGE: "+event.getNewDamage());
            }
        }
    }


//    @SubscribeEvent
//    public static void onFoodCrafting(PlayerInteractEvent.LeftClickBlock event) {
//
//        if (!(event.getEntity() instanceof ServerPlayer player)) return;
//
//        player.getFoodData().setFoodLevel(0);
//        player.getFoodData().setSaturation(0);
//    }




    @SubscribeEvent
    public static void onArmorHurt(ArmorHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            double chance = player.getAttributeValue(ModAttributes.ARMOR_DURABILITY);
            if (chance > 0) {
                if (!(player.getRandom().nextDouble() > chance/100.0)) {
                    EquipmentSlot[] slots = {
                            EquipmentSlot.HEAD,
                            EquipmentSlot.CHEST,
                            EquipmentSlot.LEGS,
                            EquipmentSlot.FEET
                    };

                    EquipmentSlot randomSlot = slots[player.getRandom().nextInt(slots.length)];
                    ItemStack armor = player.getItemBySlot(randomSlot);

                    if (!armor.isEmpty()) {
                        event.setNewDamage(randomSlot, 0);

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            var source = event.getSource().typeHolder();

            if (source.is(DamageTypes.PLAYER_EXPLOSION) || source.is(DamageTypes.EXPLOSION)) {
                double resist = player.getAttributeValue(ModAttributes.EXPLOSION_RESISTANCE);
                float multiplier = (float) Math.max(0.0, 1.0 - resist / 100.0);
                event.setNewDamage(event.getOriginalDamage() * multiplier);
            }

        }

    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            double bonus = player.getAttributeValue(ModAttributes.MIN_JUMP_HEIGHT);
            float safe = 3.0F + (float) bonus;

            event.setDistance(Math.max(0, event.getDistance() - safe));
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        DamageSource source = event.getSource();

        if (source.getEntity() instanceof Player player) {
            double value = player.getAttributeValue(ModAttributes.DOUBLE_DROP_CHANCE);
            RandomSource random = event.getEntity().level().getRandom();

            if (random.nextDouble() < value / 100.0) {
                event.getDrops().forEach(itemEntity -> {
                    ItemStack stack = itemEntity.getItem();
                    int newCount = Math.min(
                            stack.getCount() * 2,
                            stack.getMaxStackSize()
                    );
                    stack.setCount(newCount);
                });
            }
        }


    }
}
