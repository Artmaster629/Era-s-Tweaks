package net.artmaster.era_tweaks.custom.sattributes;


import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import it.crystalnest.harvest_with_ease.api.event.HarvestEvents;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttributes;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.config.BlockXpConfig;
import net.artmaster.era_tweaks.config.HarvestXpConfig;
import net.artmaster.era_tweaks.config.ItemXpConfig;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


import java.util.UUID;

import static net.artmaster.era_tweaks.network.Network.*;

//@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class SAttrubitesAdding {

    @SubscribeEvent
    public static void onTickingForOverlay(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
            if (overlayData.getHideTicks() > 0) {
                overlayData.setHideTicks(overlayData.getHideTicks() - 1);
                if (overlayData.getHideTicks() == 0) {
                    overlayData.setSendGUIType(0);
                    syncWithOverlay(player);
                }
            }
        }
    }




    public static void addXpProgress(ServerPlayer player) {
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);

        int count = classdata.getUpgradesCount();
        if (count == 0) count=1;

        if (classdata.getUpgradesPointsProgress() >= 4*count) {
            classdata.removeUpgradesPointsProgress(4);
            classdata.addUpgradesCount(1);
            classdata.addUpgradesPoints(1);
            Network.toClientAction(player, "Вы получили очко прокачки!", 2);
            return;
        }


        double value = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);
        double multiplier_xp = 1.0 + (value / 100.0);
        classdata.addUpgradesPointsProgress(multiplier_xp);





    }

    @SubscribeEvent
    public static void onMiningAddXp(BlockEvent.BreakEvent event) {

        if (event.getLevel() instanceof ServerLevel level && event.getPlayer() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);

            var getblock = level.getBlockState(BlockPos.containing(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ())).getBlock();
            String blockId = BuiltInRegistries.BLOCK.getKey(getblock).toString();

            double base_multiplier = BlockXpConfig.getMultiplier(blockId);
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;

            BlockPos pos = event.getPos();
            BlockState state = player.level().getBlockState(pos);

            float hardness = state.getDestroySpeed(player.level(), pos);

            if (hardness < 1.5) return;


            //Начисление прогресса
            if (data.getBodyLevel() < 50) {
                data.setBodyProgress(data.getBodyProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(2);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getBodyProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getBodyProgress() >= 100) {
                data.setBodyProgress(0);
                data.setBodyLevel(data.getBodyLevel()+1);
                int oldLevel = data.getBodyLevel()-1;
                Network.toServerAction("Ваш уровень Тела повышен с "+oldLevel+" на "+data.getBodyLevel(), 7);

                AttributeInstance miningSpeed = player.getAttribute(Attributes.SUBMERGED_MINING_SPEED);
                AttributeInstance miningEfficiency = player.getAttribute(Attributes.MINING_EFFICIENCY);
                AttributeModifier modifier1 = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.02,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                AttributeModifier modifier2 = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.02,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                miningSpeed.addPermanentModifier(modifier1);
                miningEfficiency.addPermanentModifier(modifier2);

                addXpProgress(player);





            }


            syncSkills(player);
            syncClasses(player);
        }
    }

    @SubscribeEvent
    public static void onFarmingAddXp(HarvestEvents.HarvestDropsEvent event) {

        Block block = event.getCrop().getBlock();
        BlockState state = event.getCrop();


        if (block instanceof CropBlock && event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);



            String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();

            double multiplier_block = HarvestXpConfig.getMultiplier(blockId);
            double base_multiplier = (state.getValue(CropBlock.AGE)*5)*multiplier_block;
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;


            //Начисление прогресса
            if (data.getBodyLevel() < 50) {
                data.setBodyProgress(data.getBodyProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(2);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getBodyProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getBodyProgress() >= 100) {
                data.setBodyProgress(0);
                data.setBodyLevel(data.getBodyLevel()+1);
                int oldLevel = data.getBodyLevel()-1;
                Network.toServerAction("Ваш уровень Тела повышен с "+oldLevel+" на "+data.getBodyLevel(), 7);

                AttributeInstance harvest = player.getAttribute(Attributes.LUCK);
                AttributeModifier modifier = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.02,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                harvest.addPermanentModifier(modifier);

                addXpProgress(player);

            }




            syncSkills(player);
            syncClasses(player);
        }
    }

    @SubscribeEvent
    public static void onInteract(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);
            String itemId = BuiltInRegistries.ITEM.getKey(event.getItem().getItem()).toString();

            double base_multiplier = ItemXpConfig.getMultiplier(itemId);
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;


            //Начисление прогресса
            if (data.getIntellectLevel() < 50) {
                data.setIntellectProgress(data.getIntellectProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(1);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getIntellectProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getIntellectProgress() >= 100) {
                data.setIntellectProgress(0);
                data.setIntellectLevel(data.getIntellectLevel()+1);
                int oldLevel = data.getIntellectLevel()-1;
                Network.toServerAction("Ваш уровень Интеллекта повышен с "+oldLevel+" на "+data.getIntellectLevel(), 7);
                addXpProgress(player);
            }
            syncSkills(player);
            syncClasses(player);
        }
    }

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        var data = player.getData(ModAttachments.PLAYER_SKILLS);

        double base_multiplier = (double) event.getRawText().length() / 10;
        double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

        double multiplier = (1.0 + (attribute/100)) * base_multiplier;


        //Начисление прогресса
        if (data.getSocietyLevel() < 50) {
            data.setSocietyProgress(data.getSocietyProgress()+ multiplier);
            //BossbarManager.updateBossbar(player, data.getSocietyProgress(), progress, "society");
            var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
            overlayData.setSendGUIType(3);
            overlayData.setProgress(multiplier);
            overlayData.setCommonProgress(data.getSocietyProgress());
            overlayData.setHideTicks(120);
            syncWithOverlay(player);
        }
        if (data.getSocietyProgress() >= 100) {
            data.setSocietyProgress(0);
            data.setSocietyLevel(data.getSocietyLevel()+1);
            int oldLevel = data.getSocietyLevel()-1;
            Network.toServerAction("Ваш уровень Социума повышен с "+oldLevel+" на "+data.getSocietyLevel(), 7);
            addXpProgress(player);
        }
        syncSkills(player);
        syncClasses(player);
    }

    @SubscribeEvent
    public static void onCraftingAddXp(PlayerEvent.ItemCraftedEvent event) {

        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);
            String itemId = BuiltInRegistries.ITEM.getKey(event.getCrafting().getItem()).toString();
            double base_multiplier = ItemXpConfig.getMultiplier(itemId);
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;


            //Начисление прогресса
            if (data.getIntellectLevel() < 50) {
                data.setIntellectProgress(data.getIntellectProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(1);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getIntellectProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getIntellectProgress() >= 100) {
                data.setIntellectProgress(0);
                data.setIntellectLevel(data.getIntellectLevel()+1);
                int oldLevel = data.getIntellectLevel()-1;
                Network.toClientAction(player, "Ваш уровень Интеллекта повышен с "+oldLevel+" на "+data.getIntellectLevel(), 2);
                addXpProgress(player);
            }




            syncSkills(player);
            syncClasses(player);
        }
    }



    @SubscribeEvent
    public static void onSmeltingAddXp(PlayerEvent.ItemSmeltedEvent event) {

        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);
            String itemId = BuiltInRegistries.ITEM.getKey(event.getSmelting().getItem()).toString();
            double base_multiplier = ItemXpConfig.getMultiplier(itemId);
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;


            //Начисление прогресса
            if (data.getIntellectLevel() < 50) {
                data.setIntellectProgress(data.getIntellectProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(1);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getIntellectProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getIntellectProgress() >= 100) {
                data.setIntellectProgress(0);
                data.setIntellectLevel(data.getIntellectLevel()+1);
                int oldLevel = data.getIntellectLevel()-1;
                Network.toClientAction(player, "Ваш уровень Интеллекта повышен с "+oldLevel+" на "+data.getIntellectLevel(), 2);
                addXpProgress(player);
            }




            syncSkills(player);
            syncClasses(player);
        }
    }



    @SubscribeEvent
    public static void onAttackAddXp(SpellPreCastEvent event) {


        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);

            double base_multiplier = event.getSpellLevel();
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;

            //Начисление прогресса
            if (data.getIntellectLevel() < 50) {
                data.setIntellectProgress(data.getIntellectProgress()+multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(1);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getIntellectProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getIntellectProgress() >= 100) {
                data.setIntellectProgress(0);
                data.setIntellectLevel(data.getIntellectLevel()+1);
                int oldLevel = data.getIntellectLevel()-1;
                Network.toClientAction(player, "Ваш уровень Интеллекта повышен с "+oldLevel+" на "+data.getIntellectLevel(), 2);
                AttributeInstance spellPower = player.getAttribute(AttributeRegistry.SPELL_POWER);
                AttributeInstance spellResist = player.getAttribute(AttributeRegistry.SPELL_RESIST);
                AttributeInstance castTime = player.getAttribute(AttributeRegistry.CAST_TIME_REDUCTION);


                AttributeModifier modifier = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.01,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                spellPower.addPermanentModifier(modifier);
                spellResist.addPermanentModifier(modifier);
                castTime.addPermanentModifier(modifier);

                addXpProgress(player);
            }


            syncSkills(player);
            syncClasses(player);
        }
    }

    @SubscribeEvent
    public static void onAttackAddXp(LivingDamageEvent.Pre event) {

        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            var data = player.getData(ModAttachments.PLAYER_SKILLS);

            double base_multiplier = event.getOriginalDamage();
            double attribute = player.getAttributeValue(ModAttributes.UPGRADE_POINT_XP);

            double multiplier = (1.0 + (attribute/100)) * base_multiplier;




            //Начисление прогресса
            if (data.getBodyLevel() < 50) {
                data.setBodyProgress(data.getBodyProgress()+ multiplier);
                var overlayData = player.getData(ModAttachments.OVERLAYS_DATA);
                overlayData.setSendGUIType(2);
                overlayData.setProgress(multiplier);
                overlayData.setCommonProgress(data.getBodyProgress());
                overlayData.setHideTicks(120);
                syncWithOverlay(player);
            }
            if (data.getBodyProgress() >= 100) {
                data.setBodyProgress(0);
                data.setBodyLevel(data.getBodyLevel()+1);
                int oldLevel = data.getBodyLevel()-1;
                Network.toClientAction(player, "Ваш уровень Тела повышен с "+oldLevel+" на "+data.getBodyLevel(), 2);


                AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
                AttributeModifier modifier = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.0135,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                attackDamage.addPermanentModifier(modifier);
                addXpProgress(player);
            }


            syncSkills(player);
            syncClasses(player);
        }
    }






    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity() instanceof ServerPlayer player) {
            syncSkills(player);
            syncClasses(player);
            var data = player.getData(ModAttachments.PLAYER_CLASS);
            player.sendSystemMessage(Component.literal("F - открыть меню выбора класса \nG - открыть меню прокачки атрибутов \nH - меня прокачки навыков\nИ помни - ты "+ Component.translatable("text.era_tweaks."+data.getPlayerClass()+"_class").getString()+" типа "+Component.translatable("text.era_tweaks."+data.getPlayerSubClass()+"_subclass").getString()));
            if (data.getPlayerClass().equals("unknown")) {
                Network.sendOpenGui(player, "class_select_screen");
            }
        }
    }
}
