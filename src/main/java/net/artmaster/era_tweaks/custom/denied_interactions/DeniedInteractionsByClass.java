package net.artmaster.era_tweaks.custom.denied_interactions;

import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.config.DeniedByClassConfig;
import net.artmaster.era_tweaks.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import top.theillusivec4.curios.api.event.CurioCanEquipEvent;

import java.util.List;

@EventBusSubscriber(modid = ModMain.MODID)
public class DeniedInteractionsByClass {



    @SubscribeEvent
    public static void onRightClickBlock(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var block = event.getPlacedBlock().getBlock();
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
        if (blockId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(blockId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);



        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }



    }






    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getItemStack().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void onLeftClickBlockByItem(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getHand() != event.getEntity().getUsedItemHand())
            return;



        var item = event.getItemStack().getItem();

        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }
    }


    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getHand() != event.getEntity().getUsedItemHand())
            return;
        var level = event.getLevel();
        double x = event.getPos().getX();
        double y = event.getPos().getY();
        double z = event.getPos().getZ();
        var block = level.getBlockState(BlockPos.containing(x, y, z)).getBlock();


        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
        if (blockId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(blockId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }

    }

    //      ////    //      /////////      /////////     /////////     /////////     /////////      /////////
    //      // //   //         //          //            //     //     //     //     //                //
    //      //  //  //         //          /////////     ///////       /////////     //                //
    //      //   // //         //          //            //   ///      //     //     //                //
    //      //    ////         //          /////////     //     //     //     //     /////////         //


    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getItemStack().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }
    }


    ///////      /////////      /////////      /////////      /////////      //     //
    //         //             //          //     //      //             //    //

    /// ////         //             //          /////////      //             ///////
    //         //             //          //     //      //             //    //
    //         //             //          //     //      /////////      //     //
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {


        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getEntity().getWeaponItem().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }
    }

    ///////      //     //      /////////     ////      /////////      //     //      /////////
    //     //      //     //     ////      //     //      //     //      //
    //     //      ///////       ////      //     //      //     //      /////////
    //     //      //   ///      ////      //     //      //     //             //

    /// ////      /////////      //     //     ////      /////////      /////////      /////////

    @SubscribeEvent
    public static void onCuriousEquipped(CurioCanEquipEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getStack().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setEquipResult(TriState.FALSE);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setEquipResult(TriState.FALSE);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setEquipResult(TriState.FALSE);
        }


    }
    ///////     /////////     //////////     //////////     /////////
    //     //     //     //  //  //     //      //     //     //

    /// ////     ///////       //  //  //     //      //     ///////
    //     //   ///      //  //  //     //      //     //   ///
    //     //     //     //  //  //     //////////     //     //
    @SubscribeEvent
    public static void onPlayerArmored(LivingEquipmentChangeEvent event) {
        if (!event.getSlot().isArmor()) {
            return;
        }


        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getTo().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);

        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                if (!classdata.getPlayerClass().equals(requiredClass)) {
                    //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                    if (player.getInventory().getItem(36).getItem().equals(item)) {
                        player.getInventory().getItem(36).setCount(0);
                    } else if (player.getInventory().getItem(37).getItem().equals(item)) {
                        player.getInventory().getItem(37).setCount(0);
                    } else if (player.getInventory().getItem(38).getItem().equals(item)) {
                        player.getInventory().getItem(38).setCount(0);
                    } else if (player.getInventory().getItem(39).getItem().equals(item)) {
                        player.getInventory().getItem(39).setCount(0);
                    }
                }
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                if (player.getInventory().getItem(36).getItem().equals(item)) {
                    player.getInventory().getItem(36).setCount(0);
                } else if (player.getInventory().getItem(37).getItem().equals(item)) {
                    player.getInventory().getItem(37).setCount(0);
                } else if (player.getInventory().getItem(38).getItem().equals(item)) {
                    player.getInventory().getItem(38).setCount(0);
                } else if (player.getInventory().getItem(39).getItem().equals(item)) {
                    player.getInventory().getItem(39).setCount(0);
                }
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            if (player.getInventory().getItem(36).getItem().equals(item)) {
                player.getInventory().getItem(36).setCount(0);
            } else if (player.getInventory().getItem(37).getItem().equals(item)) {
                player.getInventory().getItem(37).setCount(0);
            } else if (player.getInventory().getItem(38).getItem().equals(item)) {
                player.getInventory().getItem(38).setCount(0);
            } else if (player.getInventory().getItem(39).getItem().equals(item)) {
                player.getInventory().getItem(39).setCount(0);
            }
        }
    }


    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var item = event.getCrafting().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                player.closeContainer();
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                player.closeContainer();
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            player.closeContainer();
        }
    }




    //Кастомные ивенты магов

    @SubscribeEvent
    public static void onAnvilEnchant(AnvilUpdateEvent event) {
        //String item = event.getLeft().getDescriptionId();
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;
        var item = event.getOutput().getItem();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(itemId.toString());
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();
        var classdata = player.getData(ModAttachments.PLAYER_CLASS);
        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }




    }

    @SubscribeEvent
    public static void onCast(SpellPreCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        var spellId = event.getSpellId();
        if (spellId == null) return;
        DeniedByClassConfig.CancelByClassRule rule = DeniedByClassConfig.getList().get(spellId);
        if (rule == null) return; // нет запрета

        Network.syncSkills(player);
        Network.syncClasses(player);


        String requiredClass = rule.classStr();

        var classdata = player.getData(ModAttachments.PLAYER_CLASS);

        String requiredSubclass = rule.subclassStr();
        if (!requiredSubclass.isEmpty()) {
            if (!classdata.getPlayerSubClass().equals(requiredSubclass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        if (!requiredClass.isEmpty()) {
            if (!classdata.getPlayerClass().equals(requiredClass)) {
                //Network.serverDataAction("Вы не можете использовать это заклинание, так как оно требует навык "+requiredSkills, 7);
                event.setCanceled(true);
            }
        }
        List<String> requiredSkills = rule.requiredSkills();
        boolean found = false;
        if (!requiredSkills.isEmpty()) {
            for (String skill : requiredSkills) {
                if (classdata.getPlayerSkills().contains(skill)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            event.setCanceled(true);
        }
    }

}

