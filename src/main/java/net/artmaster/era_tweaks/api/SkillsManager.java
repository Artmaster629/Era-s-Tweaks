package net.artmaster.era_tweaks.api;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class SkillsManager {


    public static void makeChangesToClass(PlayerClassData data, ServerPlayer player) {
        var player_skills = data.getPlayerSkills();
        System.out.println(player_skills);
        if (player_skills.contains("maxicka13")) {
            AttributeInstance spellPower = player.getAttribute(AttributeRegistry.BLOOD_SPELL_POWER);


            AttributeModifier modifier = new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                    1.0,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
            spellPower.addPermanentModifier(modifier);
        }
    }

}
