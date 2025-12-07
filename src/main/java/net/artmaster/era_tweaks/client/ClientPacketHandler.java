package net.artmaster.era_tweaks.client;

import net.artmaster.era_tweaks.client.screen.ClassManageScreen;
import net.artmaster.era_tweaks.client.screen.UpgradeManageScreen;
import net.artmaster.era_tweaks.network.OpenGuiPacket;
import net.artmaster.era_tweaks.network.RunCommandPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
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
    }



    public static void handleRunCommand(RunCommandPacket packet) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.connection.sendCommand(packet.command());
        }
    }
}
