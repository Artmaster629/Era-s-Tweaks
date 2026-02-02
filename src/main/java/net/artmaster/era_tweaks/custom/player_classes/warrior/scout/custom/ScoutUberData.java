package net.artmaster.era_tweaks.custom.player_classes.warrior.scout.custom;

import net.minecraft.nbt.CompoundTag;

public class ScoutUberData {

    private double uberProgress = 0.0;

    private boolean isSentSignalToStart = false;

    public boolean isSentSignalToStart() {
        return isSentSignalToStart;
    }

    public void removeSignal() {
        this.isSentSignalToStart = false;
    }

    public void addSignal() {
        this.isSentSignalToStart = true;
    }

    public double getUberProgress() {
        return uberProgress;
    }
    public void setUberProgress(double uberProgress) {
        this.uberProgress = uberProgress;
    }
    public void addUberProgress(double uberProgress) {
        this.uberProgress += uberProgress;
    }

    public static CompoundTag save(ScoutUberData data) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("uberProgress", data.uberProgress);
        tag.putBoolean("signal", data.isSentSignalToStart);
        return tag;
    }

    public static void load(ScoutUberData data, CompoundTag tag) {
        data.uberProgress = tag.getDouble("uberProgress");
        data.isSentSignalToStart = tag.getBoolean("signal");
    }
}
