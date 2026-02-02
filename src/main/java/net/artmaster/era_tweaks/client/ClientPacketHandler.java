package net.artmaster.era_tweaks.client;

import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.network.main_packets.ClientActionPacket;
import net.artmaster.era_tweaks.network.main_packets.ServerActionPacket;
import net.artmaster.era_tweaks.network.main_packets.DataPacket;
import net.artmaster.era_tweaks.registry.ModAttachments;
import net.artmaster.era_tweaks.custom.data.OverlayAttributesData;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.custom.ScoutUberData;
import net.artmaster.era_tweaks.client.screen.ClassManageScreen;
import net.artmaster.era_tweaks.client.screen.ClassSelectScreen;
import net.artmaster.era_tweaks.client.screen.UpgradeManageScreen;
import net.artmaster.era_tweaks.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ClientPacketHandler {



    public static void handleOpenGui(OpenGuiPacket packet) {
        if (packet.resourceId().equals("upgrade_manage_screen")) {
            Minecraft.getInstance().setScreen(new UpgradeManageScreen());
        }
        if (packet.resourceId().equals("class_manage_screen")) {
            Minecraft.getInstance().setScreen(new ClassManageScreen());
        }
        if (packet.resourceId().equals("class_select_screen")) {
            Minecraft.getInstance().setScreen(new ClassSelectScreen());
        }
    }

    public static void handleSyncPlayerClass(DataPacket packet) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        switch (packet.dataType()) {
            case 1 -> {
                var data = player.getData(ModAttachments.PLAYER_SKILLS);
                PlayerSAttrubitesData.load(data, packet.data());
                Screen screen = Minecraft.getInstance().screen;
                if (screen instanceof UpgradeManageScreen upgradeScreen) {
                    upgradeScreen.refresh();
                }
            }
            case 2 -> {
                var data = player.getData(ModAttachments.PLAYER_CLASS);
                PlayerClassData.load(data, packet.data());
                Screen screen = Minecraft.getInstance().screen;
                if (screen instanceof UpgradeManageScreen upgradeScreen) {
                    upgradeScreen.refresh();
                }
            }
            case 3 -> {var data = player.getData(ModAttachments.OVERLAYS_DATA); OverlayAttributesData.load(data, packet.data());}
            case 4 -> {var data = player.getData(ModAttachments.UBER); ScoutUberData.load(data, packet.data());}
        }

        // 1 - атрибуты
        // 2 - класс
        // 3 - оверлей
        // 4 - убер

    }

    public static void handleStaminaConsume(StaminaPacket packet) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;


        var stamina = Stamina.get(player);
        stamina.consume(packet.stamina());
    }


    public static void handleRunCommand(RunCommandPacket packet) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.connection.sendCommand(packet.command());
        }
    }

    public static void handleClientAction(ClientActionPacket packet) {
        if (packet.actionType() == 1) {
            ItemStack item = new ItemStack(Items.TOTEM_OF_UNDYING);
            Minecraft.getInstance().gameRenderer.displayItemActivation(item);
        }

        if (packet.actionType() == 2) {
            LocalPlayer player = Minecraft.getInstance().player;
            player.displayClientMessage(Component.literal(packet.key()), false);
        }
    }
}
