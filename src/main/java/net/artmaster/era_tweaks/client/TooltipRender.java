package net.artmaster.era_tweaks.client;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.config.DeniedByClassConfig;
import net.artmaster.era_tweaks.config.DeniedConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
        DeniedByClassConfig.CancelByClassRule classRule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule != null && classRule != null) {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.literal("§7Требования:"));
            if (rule != null) {
                PlayerSAttrubitesData data = player.getData(ModAttachments.PLAYER_SKILLS);
                Map<String, Integer> playerLevels = Map.of(
                        "intellect", data.getIntellectLevel(),
                        "body", data.getBodyLevel(),
                        "society", data.getSocietyLevel()
                );

                List<String> attrs = rule.requiredAttributes();
                List<Integer> levels = rule.requiredLevels();

                //boolean denied = false;


                for (int i = 0; i < attrs.size(); i++) {
                    String attr = attrs.get(i);
                    int requiredLevel = levels.get(i);
                    int playerLevel = playerLevels.getOrDefault(attr, 0);
                    if (playerLevel < requiredLevel) {
                        //denied = true;
                        event.getToolTip().add(Component.literal("• " + Component.translatable("sattribute.era_tweaks." + attr).getString() + ": " + requiredLevel)
                                .withStyle(ChatFormatting.RED));

                    } else {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("sattribute.era_tweaks." + attr).getString() + ": " + requiredLevel)
                                .withStyle(ChatFormatting.GREEN));
                    }
                }

                String requiredSkill = rule.requiredSkill();
                if (!requiredSkill.isEmpty()) {
                    var classData = player.getData(ModAttachments.PLAYER_CLASS);
                    if (!classData.getPlayerSkills().contains(requiredSkill)) {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.skill").getString() + ": " + Component.translatable("skill.era_tweaks." + requiredSkill).getString())
                                .withStyle(ChatFormatting.RED));

                    } else {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.skill").getString() + ": " + Component.translatable("skill.era_tweaks." + requiredSkill).getString())
                                .withStyle(ChatFormatting.GREEN));
                    }
                }

            }
            if (classRule != null) {
                var classData = player.getData(ModAttachments.PLAYER_CLASS);



                String classStr = classRule.classStr();
                if (!classStr.isEmpty()) {
                    if (!classData.getPlayerClass().equals(classStr)) {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.class").getString() + ": " + Component.translatable("text.era_tweaks." + classStr + "_class").getString())
                                .withStyle(ChatFormatting.RED));

                    } else {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.class").getString() + ": " + Component.translatable("text.era_tweaks." + classStr + "_class").getString())
                                .withStyle(ChatFormatting.GREEN));
                    }
                }
                String subclassStr = classRule.subclassStr();
                if (!subclassStr.isEmpty()) {
                    if (!classData.getPlayerSubClass().equals(subclassStr)) {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.subclass").getString() + ": " + Component.translatable("text.era_tweaks." + subclassStr + "_subclass").getString())
                                .withStyle(ChatFormatting.RED));

                    } else {
                        event.getToolTip().add(Component.literal("• " + Component.translatable("text.era_tweaks.subclass").getString() + ": " + Component.translatable("text.era_tweaks." + subclassStr + "_subclass").getString())
                                .withStyle(ChatFormatting.GREEN));
                    }
                }

                List<String> requiredSkills = classRule.requiredSkills();
                if (!requiredSkills.isEmpty()) {

                    MutableComponent base = Component.literal("§7"+Component.translatable("text.era_tweaks.skills").getString() + ":");
                    ChatFormatting color = ChatFormatting.RED;

                    for (String skill1 : requiredSkills) {
                        base.getSiblings().add(Component.literal("\n• " + Component.translatable("skill.era_tweaks." + skill1).getString()));
                    }

                    for (String skill : requiredSkills) {
                        if (classData.getPlayerSkills().contains(skill)) {
                            color = ChatFormatting.GREEN;
                            break;
                        }
                    }
                    event.getToolTip().add(base.withStyle(color));
                }
            }
        }
    }
}
