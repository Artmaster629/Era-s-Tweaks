package net.artmaster.era_tweaks.custom.data;

import net.minecraft.nbt.CompoundTag;




public class PlayerSAttrubitesData {
    private int intellectLevel = 1;
    private int bodyLevel = 1;
    private int societyLevel = 1;

    private double intellectProgress = 0;
    private double bodyProgress = 0;
    private double societyProgress = 0;


    public int getIntellectLevel() {
        return intellectLevel;
    }
    public void setIntellectLevel(int newLevel) {
        this.intellectLevel = newLevel;
    }

    public int getBodyLevel() {
        return bodyLevel;
    }
    public void setBodyLevel(int newLevel) {
        this.bodyLevel = newLevel;
    }

    public int getSocietyLevel() {
        return societyLevel;
    }
    public void setSocietyLevel(int newLevel) {
        this.societyLevel = newLevel;
    }



    public double getIntellectProgress() {
        return intellectProgress;
    }
    public void setIntellectProgress(double newProgress) {
        this.intellectProgress = newProgress;
    }

    public double getBodyProgress() {
        return bodyProgress;
    }
    public void setBodyProgress(double newProgress) {
        this.bodyProgress = newProgress;
    }

    public double getSocietyProgress() {
        return societyProgress;
    }
    public void setSocietyProgress(double newProgress) {
        this.societyProgress = newProgress;
    }


    private int sendGUIType = 0;

    public int getSendGUIType() {
        return sendGUIType;
    }

    public void setSendGUIType(int sendGUIType) {
        this.sendGUIType = sendGUIType;
    }

    public static CompoundTag save(PlayerSAttrubitesData data) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Intellect", data.intellectLevel);
        tag.putInt("Body", data.bodyLevel);
        tag.putInt("Society", data.societyLevel);

        tag.putDouble("IntellectProgress", data.intellectProgress);
        tag.putDouble("BodyProgress", data.bodyProgress);
        tag.putDouble("SocietyProgress", data.societyProgress);

        tag.putInt("SendGUIType", data.sendGUIType);
        return tag;
    }

    public static void load(PlayerSAttrubitesData data, CompoundTag tag) {
        data.intellectLevel = tag.getInt("Intellect");
        data.bodyLevel = tag.getInt("Body");
        data.societyLevel = tag.getInt("Society");

        data.intellectProgress = tag.getDouble("IntellectProgress");
        data.bodyProgress = tag.getDouble("BodyProgress");
        data.societyProgress = tag.getDouble("SocietyProgress");

        data.sendGUIType = tag.getInt("SendGUIType");
    }

}
