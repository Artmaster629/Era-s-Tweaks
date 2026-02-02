package net.artmaster.era_tweaks.registry;

import net.artmaster.era_tweaks.ModMain;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = ModMain.MODID)
public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE, "era_tweaks");


    public static final Holder<Attribute> UPGRADE_POINT_XP = ATTRIBUTES.register("upgrade_point_xp", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.upgrade_point_xp",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> MIN_JUMP_HEIGHT = ATTRIBUTES.register("min_jump_height", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.min_jump_height",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> DOUBLE_DROP_CHANCE = ATTRIBUTES.register("double_drop_chance", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.double_drop_chance",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> EXPLOSION_RESISTANCE = ATTRIBUTES.register("explosion_resistance", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.explosion_resistance",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> PROJECTILE_RESISTANCE = ATTRIBUTES.register("projectile_resistance", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.projectile_resistance",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("fire_resistance", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.fire_resistance",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> ARMOR_DURABILITY = ATTRIBUTES.register("armor_durability", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.explosion_resistance",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));

    public static final Holder<Attribute> SATURATION_BONUS = ATTRIBUTES.register("saturation_bonus", () -> new RangedAttribute(
            // The translation key to use.
            "attributes.era_tweaks.saturation_bonus",
            // The default value.
            0,
            // Min and max values.
            0,
            100
    ));


    @SubscribeEvent // on the mod event bus
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {

        // We can also check if a given EntityType already has a given attribute.
        // In this example, if villagers don't have the armor attribute already, we add it.
        if (!event.has(EntityType.PLAYER, ModAttributes.UPGRADE_POINT_XP)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.UPGRADE_POINT_XP,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.MIN_JUMP_HEIGHT)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.MIN_JUMP_HEIGHT,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.DOUBLE_DROP_CHANCE)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.DOUBLE_DROP_CHANCE,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.EXPLOSION_RESISTANCE)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.EXPLOSION_RESISTANCE,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.FIRE_RESISTANCE)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.FIRE_RESISTANCE,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.PROJECTILE_RESISTANCE)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.PROJECTILE_RESISTANCE,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.ARMOR_DURABILITY)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.ARMOR_DURABILITY,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
        if (!event.has(EntityType.PLAYER, ModAttributes.SATURATION_BONUS)) {
            event.add(
                    // The EntityType to add the attribute for.
                    EntityType.PLAYER,
                    // The Holder<Attribute> to add to the EntityType. Can also be a custom attribute.
                    ModAttributes.SATURATION_BONUS,
                    // The attribute value to add.
                    // Can be omitted, if so, the attribute's default value will be used instead.
                    0
            );
        }
    }

    @EventBusSubscriber(modid = ModMain.MODID)
    public static class PlayerAttributesSync {
        @SubscribeEvent
        public static void playerClone(PlayerEvent.Clone event) {
            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();
            //custom
            newPlayer.getAttribute(UPGRADE_POINT_XP).setBaseValue(oldPlayer.getAttribute(UPGRADE_POINT_XP).getBaseValue());
            newPlayer.getAttribute(MIN_JUMP_HEIGHT).setBaseValue(oldPlayer.getAttribute(MIN_JUMP_HEIGHT).getBaseValue());
            newPlayer.getAttribute(DOUBLE_DROP_CHANCE).setBaseValue(oldPlayer.getAttribute(DOUBLE_DROP_CHANCE).getBaseValue());
            newPlayer.getAttribute(EXPLOSION_RESISTANCE).setBaseValue(oldPlayer.getAttribute(EXPLOSION_RESISTANCE).getBaseValue());
            newPlayer.getAttribute(ARMOR_DURABILITY).setBaseValue(oldPlayer.getAttribute(ARMOR_DURABILITY).getBaseValue());
            newPlayer.getAttribute(SATURATION_BONUS).setBaseValue(oldPlayer.getAttribute(SATURATION_BONUS).getBaseValue());
            newPlayer.getAttribute(PROJECTILE_RESISTANCE).setBaseValue(oldPlayer.getAttribute(PROJECTILE_RESISTANCE).getBaseValue());
            newPlayer.getAttribute(FIRE_RESISTANCE).setBaseValue(oldPlayer.getAttribute(FIRE_RESISTANCE).getBaseValue());

            //vanilla/neoforge
            ////newPlayer.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(oldPlayer.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
            ////newPlayer.getAttribute(Attributes.ARMOR).setBaseValue(oldPlayer.getAttribute(Attributes.ARMOR).getBaseValue());
            ////newPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(oldPlayer.getAttribute(Attributes.MAX_HEALTH).getBaseValue());

        }
    }
}
