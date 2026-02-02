package net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class DeathPointData {
    private UUID entityWithPoint;

    @Nullable
    public UUID getEntityUUID() {
        return entityWithPoint;
    }

    public void setEntity(Entity entity) {
        this.entityWithPoint = entity.getUUID();
    }

    public void clearEntity() {
        this.entityWithPoint = null;
    }

    @Nullable
    public Entity getEntity(Level level) {
        if (entityWithPoint == null) return null;
        if (!(level instanceof ServerLevel server)) return null;
        return server.getEntity(entityWithPoint);
    }

    public static CompoundTag save(DeathPointData data) {
        CompoundTag tag = new CompoundTag();

        if (data.entityWithPoint != null) {
            tag.putUUID("EntityWithPoint", data.entityWithPoint);
        }

        return tag;
    }

    public static void load(DeathPointData data, CompoundTag tag) {
        if (tag.hasUUID("EntityWithPoint")) {
            data.entityWithPoint = tag.getUUID("EntityWithPoint");
        } else {
            data.entityWithPoint = null;
        }
    }
}
