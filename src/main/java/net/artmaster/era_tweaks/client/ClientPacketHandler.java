package net.artmaster.era_tweaks.client;

import com.alrex.parcool.api.Stamina;
import net.artmaster.era_tweaks.api.container.MyAttachments;
import net.artmaster.era_tweaks.api.container.PlayerClassData;
import net.artmaster.era_tweaks.api.container.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.client.screen.ClassManageScreen;
import net.artmaster.era_tweaks.client.screen.ClassSelectScreen;
import net.artmaster.era_tweaks.client.screen.UpgradeManageScreen;
import net.artmaster.era_tweaks.network.*;
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
        if (packet.resourceId().equals("class_select_screen")) {
            Minecraft.getInstance().setScreen(new ClassSelectScreen());
        }
    }



    public static void handleSyncPlayerSkills(SyncPlayerSkillsPacket packet) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        var data = player.getData(MyAttachments.PLAYER_SKILLS);
        PlayerSAttrubitesData.load(data, packet.data());
    }

    public static void handleSyncPlayerClass(SyncPlayerClassPacket packet) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        var data = player.getData(MyAttachments.PLAYER_CLASS);
        PlayerClassData.load(data, packet.data());
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
}
