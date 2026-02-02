package net.artmaster.era_tweaks.custom.gui;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.server.level.ServerBossEvent;

import java.util.HashMap;
import java.util.Map;

public class BossbarManager {
    private static final Map<ServerPlayer, ServerBossEvent> bossbars = new HashMap<>();

    public static void updateBossbarUber(ServerPlayer player, double progress, double progress_addition) {

        Component titleAttribute = Component.translatable("text.era_tweaks.uber");

        ServerBossEvent bossbar = bossbars.computeIfAbsent(player, p -> {
            ServerBossEvent newBar = new ServerBossEvent(
                    titleAttribute,
                    BossBarColor.YELLOW,
                    BossBarOverlay.PROGRESS
            );
            newBar.setCreateWorldFog(false);
            newBar.setVisible(true);
            newBar.addPlayer(player);
            return newBar;
        });


        double visualProgress = progress;
        if (progress > 100) {
            visualProgress=100;
        }
        bossbar.setProgress((float) (visualProgress/100)); // от 0.0 до 1.0


        bossbar.setName(Component.literal(titleAttribute.getString()+" +"+(double) Math.round(progress_addition*100)/100+" ("+ (int) visualProgress + "%)"));
    }

    public static void updateBossbar(ServerPlayer player, double progress, double progress_addition, String attribute) {

        Component titleAttribute = Component.translatable("sattribute.era_tweaks."+attribute);

        ServerBossEvent bossbar = bossbars.computeIfAbsent(player, p -> {
            ServerBossEvent newBar = new ServerBossEvent(
                    titleAttribute,
                    BossBarColor.BLUE,
                    BossBarOverlay.PROGRESS
            );
            newBar.setCreateWorldFog(false);
            newBar.setVisible(true);
            newBar.addPlayer(player);
            return newBar;
        });


        double visualProgress = progress;
        if (progress > 100) {
            visualProgress=100;
        }
        bossbar.setProgress((float) (visualProgress/100)); // от 0.0 до 1.0


        bossbar.setName(Component.literal(titleAttribute.getString()+" +"+(double) Math.round(progress_addition*100)/100+" ("+ (int) visualProgress + "%)"));
    }

    public static void removeBossbar(ServerPlayer player) {
        ServerBossEvent bar = bossbars.remove(player);
        if (bar != null) {
            bar.removePlayer(player);
        }
    }
}
