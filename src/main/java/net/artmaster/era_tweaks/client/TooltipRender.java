package net.artmaster.era_tweaks.client;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.config.DeniedConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Map;


@EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT)
public class TooltipRender {

    @SubscribeEvent
    public static void onTooltipRender(ItemTooltipEvent event) {
        var player = event.getEntity();
        if (player == null) return;
        var item = event.getItemStack().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedConfig.CancelRule rule = DeniedConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        PlayerSAttrubitesData data = player.getData(MyAttachments.PLAYER_SKILLS);
        Map<String, Integer> playerLevels = Map.of(
                "fight", data.getFightLevel(),
                "magic", data.getMagicLevel(),
                "crafting", data.getCraftingLevel(),
                "building", data.getBuildingLevel(),
                "mining", data.getMiningLevel(),
                "stamina", data.getStaminaLevel(),
                "farming", data.getFarmingLevel()
        );

        List<String> attrs = rule.requiredAttributes();
        List<Integer> levels = rule.requiredLevels();

        //boolean denied = false;
        event.getToolTip().add(Component.literal(""));
        event.getToolTip().add(Component.literal("§7Требования:"));


        for (int i = 0; i < attrs.size(); i++) {
            String attr = attrs.get(i);
            int requiredLevel = levels.get(i);
            int playerLevel = playerLevels.getOrDefault(attr, 0);
            if (playerLevel < requiredLevel) {
                //denied = true;
                event.getToolTip().add(Component.literal(Component.translatable("sattribute.era_tweaks."+attr).getString()+": "+requiredLevel)
                        .withStyle(ChatFormatting.RED));

            } else {
                event.getToolTip().add(Component.literal(Component.translatable("sattribute.era_tweaks."+attr).getString()+": "+requiredLevel)
                        .withStyle(ChatFormatting.GREEN));
            }
        }

        String requiredSkill = rule.requiredSkill();
        if (requiredSkill.isEmpty()) return;
        var classData = player.getData(MyAttachments.PLAYER_CLASS);
        if (classData.getPlayerSkills().contains(requiredSkill)) {
            event.getToolTip().add(Component.literal(Component.translatable("text.era_tweaks.skill").getString()+":"+Component.translatable("sattribute.era_tweaks."+requiredSkill).getString())
                    .withStyle(ChatFormatting.RED));

        } else {
            event.getToolTip().add(Component.literal(Component.translatable("text.era_tweaks.skill").getString()+":"+Component.translatable("sattribute.era_tweaks."+requiredSkill).getString())
                    .withStyle(ChatFormatting.GREEN));
        }


    }
}
