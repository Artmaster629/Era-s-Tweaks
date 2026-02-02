package net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class MarkTeam {

    public static final String TEAM_ID = "death_point_mark";

    public static PlayerTeam get(ServerLevel level) {
        Scoreboard sb = level.getScoreboard();
        PlayerTeam team = sb.getPlayerTeam(TEAM_ID);

        if (team == null) {
            team = sb.addPlayerTeam(TEAM_ID);
            team.setPlayerPrefix(Component.literal("💀").withStyle(ChatFormatting.WHITE));
            team.setSeeFriendlyInvisibles(true);
        }

        return team;
    }

    public static void mark(ServerLevel level, Entity e) {
        PlayerTeam team = MarkTeam.get(level);

        e.setCustomNameVisible(true);

        if (e.getCustomName() == null) {
            e.setCustomName(Component.literal(""));
        }

        level.getScoreboard().addPlayerToTeam(e.getStringUUID(), team);
    }

    public static void unmark(ServerLevel level, Entity e) {
        Scoreboard sb = level.getScoreboard();
        sb.removePlayerFromTeam(e.getStringUUID());

        e.setCustomNameVisible(false);
        e.setCustomName(null);
    }
}
