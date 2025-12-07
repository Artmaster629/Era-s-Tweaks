package net.artmaster.era_tweaks.api.event;


import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import it.crystalnest.harvest_with_ease.api.event.HarvestEvents;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.config.BlockXpConfig;
import net.artmaster.era_tweaks.config.HarvestXpConfig;
import net.artmaster.era_tweaks.config.ItemXpConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;


import java.util.List;
import java.util.UUID;

import static net.artmaster.era_tweaks.network.Network.syncClasses;
import static net.artmaster.era_tweaks.network.Network.syncSkills;

@Mod("era_tweaks")
@EventBusSubscriber(modid = ModMain.MODID)
public class SAttrubitesAdding {
    @SubscribeEvent
    public static void onMiningAddXp(BlockEvent.BreakEvent event) {

        if (event.getLevel() instanceof ServerLevel level && event.getPlayer() instanceof ServerPlayer player) {
            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            var getblock = level.getBlockState(BlockPos.containing(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ())).getBlock();
            String blockId = BuiltInRegistries.BLOCK.getKey(getblock).toString();
            double multiplier = BlockXpConfig.getMultiplier(blockId);


            //Начисление прогресса
            if (data.getMiningLevel() < 100) {
                double progress = multiplier/data.getMiningLevel();
                data.setMiningProgress(data.getMiningProgress()+progress);
            }
            if (data.getMiningProgress() >= 100) {
                data.setMiningProgress(0);
                data.setMiningLevel(data.getMiningLevel()+1);
                int oldLevel = data.getMiningLevel()-1;
                player.displayClientMessage(Component.literal("Ваш уровень Добычи повышен с "+oldLevel+" на "+data.getMiningLevel()), false);


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

            }


            syncSkills(player);
        }
    }

    @SubscribeEvent
    public static void onFarmingAddXp(HarvestEvents.HarvestDropsEvent event) {

        Block block = event.getCrop().getBlock();
        BlockState state = event.getCrop();


        if (block instanceof CropBlock && event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(MyAttachments.PLAYER_SKILLS);



            String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();
            double multiplier_block = HarvestXpConfig.getMultiplier(blockId);
            double multiplier = state.getValue(CropBlock.AGE)*multiplier_block;


            //Начисление прогресса
            if (data.getFarmingLevel() < 100) {
                double progress = multiplier/data.getFarmingLevel();
                data.setFarmingProgress(data.getFarmingProgress()+progress);
            }
            if (data.getFarmingProgress() >= 100) {
                data.setFarmingProgress(0);
                data.setFarmingLevel(data.getFarmingLevel()+1);
                int oldLevel = data.getFarmingLevel()-1;
                player.displayClientMessage(Component.literal("Ваш уровень Фермерства повышен с "+oldLevel+" на "+data.getFarmingLevel()), false);


                AttributeInstance harvest = player.getAttribute(Attributes.LUCK);
                AttributeModifier modifier = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.02,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                harvest.addPermanentModifier(modifier);

            }




            syncSkills(player);
        }
    }

    @SubscribeEvent
    public static void onCraftingAddXp(PlayerEvent.ItemCraftedEvent event) {

        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            String itemId = BuiltInRegistries.ITEM.getKey(event.getCrafting().getItem()).toString();
            double multiplier = ItemXpConfig.getMultiplier(itemId);


            //Начисление прогресса
            if (data.getCraftingLevel() < 100) {
                double progress = multiplier/data.getCraftingLevel();
                data.setCraftingProgress(data.getCraftingProgress()+progress);
            }
            if (data.getCraftingProgress() >= 100) {
                data.setCraftingProgress(0);
                data.setCraftingLevel(data.getCraftingLevel()+1);
                int oldLevel = data.getCraftingLevel()-1;
                player.displayClientMessage(Component.literal("Ваш уровень Создания повышен с "+oldLevel+" на "+data.getCraftingLevel()), false);
            }




            syncSkills(player);
        }
    }

    @SubscribeEvent
    public static void onCropAddXp(HarvestEvents.HarvestDropsEvent event) {
        List<ItemStack> drops = event.getDrops();
        ItemStack cropItem = drops.getFirst();

        var data = event.getEntity().getData(MyAttachments.PLAYER_SKILLS);
        double multiplier = data.getFarmingLevel();
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

    @SubscribeEvent
    public static void onAttackAddXp(SpellPreCastEvent event) {

        System.out.println("preCast!");

        if (event.getEntity() instanceof ServerPlayer player) {
            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            double multiplier = event.getSpellLevel();






            //Начисление прогресса
            if (data.getMagicProgress() < 100) {
                double progress = multiplier/data.getMagicLevel();
                System.out.println(progress);
                data.setMagicProgress(data.getMagicProgress()+progress);

            }
            if (data.getMagicProgress() >= 100) {
                data.setMagicProgress(0);
                data.setMagicLevel(data.getMagicLevel()+1);
                int oldLevel = data.getMagicLevel()-1;
                player.displayClientMessage(Component.literal("Ваш уровень Магии повышен с "+oldLevel+" на "+data.getMagicLevel()), false);

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
            }


            syncSkills(player);
        }
    }

    @SubscribeEvent
    public static void onAttackAddXp(LivingDamageEvent.Pre event) {

        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            var data = player.getData(MyAttachments.PLAYER_SKILLS);
            double multiplier = event.getOriginalDamage();




            //Начисление прогресса
            if (data.getFightLevel() < 100) {
                double progress = multiplier/data.getFightLevel();
                System.out.println(progress);
                data.setFightProgress(data.getFightProgress()+progress);

            }
            if (data.getFightProgress() >= 100) {
                data.setFightProgress(0);
                data.setFightLevel(data.getFightLevel()+1);
                int oldLevel = data.getFightLevel()-1;
                player.displayClientMessage(Component.literal("Ваш уровень Боевого искусства повышен с "+oldLevel+" на "+data.getFightLevel()), false);

                AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
                AttributeModifier modifier = new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("era_tweaks", String.valueOf(UUID.randomUUID())),
                        0.02,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                attackDamage.addPermanentModifier(modifier);
            }


            syncSkills(player);
        }
    }






    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncSkills(player);
            syncClasses(player);
        }
    }
}
