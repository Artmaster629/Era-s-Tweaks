package net.artmaster.era_tweaks.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

import java.util.WeakHashMap;

public class BrewingStandPlayerTracker {
    public static final WeakHashMap<BrewingStandBlockEntity, ServerPlayer> LAST_PLAYER = new WeakHashMap<>();
}
