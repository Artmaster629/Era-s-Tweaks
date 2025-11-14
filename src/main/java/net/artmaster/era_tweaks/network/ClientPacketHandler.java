package net.artmaster.era_tweaks.network;

import net.artmaster.era_tweaks.client.UpgradeManageScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientPacketHandler {
    public static void handleOpenGui(OpenGuiPacket packet) {
        if (packet.resourceId().equals("upgrade_manage_screen")) {
            Minecraft.getInstance().setScreen(new UpgradeManageScreen());
        }
    }



    public static void handleRunCommand(RunCommandPacket packet) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.connection.sendCommand(packet.command());
        }
    }
}
