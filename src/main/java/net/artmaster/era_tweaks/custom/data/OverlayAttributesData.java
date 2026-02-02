package net.artmaster.era_tweaks.custom.data;

import net.minecraft.nbt.CompoundTag;


public class OverlayAttributesData {
    private int type = 0;

    public int getSendGUIType() {
        return type;
    }

    public void setSendGUIType(int sendGUIType) {
        this.type = sendGUIType;
    }

    private double commonProgress = 0;

    public double getCommonProgress() {
        return commonProgress;
    }

    public void setCommonProgress(double commonProgress) {
        this.commonProgress = commonProgress;
    }

    private double progress = 0;

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }


    private int hideTicks = 0;

    public int getHideTicks() {
        return hideTicks;
    }

    public void setHideTicks(int hideTicks) {
        this.hideTicks = hideTicks;
    }

    public static CompoundTag save(OverlayAttributesData data) {
        CompoundTag tag = new CompoundTag();

        tag.putInt("Type", data.type);
        tag.putInt("HideTicks", data.hideTicks);
        tag.putDouble("CommonProgress", data.commonProgress);
        tag.putDouble("Progress", data.progress);
        return tag;
    }

    public static void load(OverlayAttributesData data, CompoundTag tag) {
        data.type = tag.getInt("Type");
        data.commonProgress = tag.getDouble("CommonProgress");
        data.progress = tag.getDouble("Progress");
        data.hideTicks = tag.getInt("HideTicks");
    }

}
