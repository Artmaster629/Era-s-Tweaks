package net.artmaster.era_tweaks.api.container;

import net.minecraft.nbt.CompoundTag;




public class PlayerSAttrubitesData {
    private int miningLevel = 1;
    private int farmingLevel = 1;
    private int fightLevel = 1;
    private int staminaLevel = 1;
    private int buildingLevel = 1;
    private int craftingLevel = 1;
    private int magicLevel = 1;

    private double miningProgress = 0;
    private double farmingProgress = 0;
    private double fightProgress = 0;
    private double staminaProgress = 0;
    private double buildingProgress = 0;
    private double craftingProgress = 0;
    private double magicProgress = 0;


    public int getMiningLevel() {
        return miningLevel;
    }
    public void setMiningLevel(int newLevel) {
        this.miningLevel = newLevel;
    }

    public int getFarmingLevel() {
        return farmingLevel;
    }
    public void setFarmingLevel(int newLevel) {
        this.farmingLevel = newLevel;
    }

    public int getFightLevel() {
        return fightLevel;
    }
    public void setFightLevel(int newLevel) {
        this.fightLevel = newLevel;
    }

    public int getStaminaLevel() {
        return staminaLevel;
    }
    public void setStaminaLevel(int newLevel) {
        this.staminaLevel = newLevel;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }
    public void setBuildingLevel(int newLevel) {
        this.buildingLevel = newLevel;
    }

    public int getCraftingLevel() {
        return craftingLevel;
    }
    public void setCraftingLevel(int newLevel) {
        this.craftingLevel = newLevel;
    }

    public int getMagicLevel() {
        return magicLevel;
    }
    public void setMagicLevel(int newLevel) {
        this.magicLevel = newLevel;
    }



    public double getMiningProgress() {
        return miningProgress;
    }
    public void setMiningProgress(double newProgress) {
        this.miningProgress = newProgress;
    }

    public double getFarmingProgress() {
        return farmingProgress;
    }
    public void setFarmingProgress(double newProgress) {
        this.farmingProgress = newProgress;
    }

    public double getFightProgress() {
        return fightProgress;
    }
    public void setFightProgress(double newProgress) {
        this.fightProgress = newProgress;
    }

    public double getStaminaProgress() {
        return staminaProgress;
    }
    public void setStaminaProgress(double newProgress) {
        this.staminaProgress = newProgress;
    }

    public double getBuildingProgress() {
        return buildingProgress;
    }
    public void setBuildingProgress(double newProgress) {
        this.buildingProgress = newProgress;
    }

    public double getCraftingProgress() {
        return craftingProgress;
    }
    public void setCraftingProgress(double newProgress) {
        this.craftingProgress = newProgress;
    }

    public double getMagicProgress() {
        return magicProgress;
    }
    public void setMagicProgress(double newProgress) {
        this.magicProgress = newProgress;
    }

    public static CompoundTag save(PlayerSAttrubitesData data) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Mining", data.miningLevel);
        tag.putInt("Farming", data.farmingLevel);
        tag.putInt("Fight", data.fightLevel);
        tag.putInt("Stamina", data.staminaLevel);
        tag.putInt("Building", data.buildingLevel);
        tag.putInt("Crafting", data.craftingLevel);
        tag.putInt("Magic", data.magicLevel);

        tag.putDouble("MiningProgress", data.miningProgress);
        tag.putDouble("FarmingProgress", data.farmingProgress);
        tag.putDouble("FightProgress", data.fightProgress);
        tag.putDouble("StaminaProgress", data.staminaProgress);
        tag.putDouble("BuildingProgress", data.buildingProgress);
        tag.putDouble("CraftingProgress", data.craftingProgress);
        tag.putDouble("MagicProgress", data.magicProgress);
        return tag;
    }

    public static void load(PlayerSAttrubitesData data, CompoundTag tag) {
        data.miningLevel = tag.getInt("Mining");
        data.farmingLevel = tag.getInt("Farming");
        data.fightLevel = tag.getInt("Fight");
        data.staminaLevel = tag.getInt("Stamina");
        data.buildingLevel = tag.getInt("Building");
        data.craftingLevel = tag.getInt("Crafting");
        data.magicLevel = tag.getInt("Magic");

        data.miningProgress = tag.getDouble("MiningProgress");
        data.farmingProgress = tag.getDouble("FarmingProgress");
        data.fightProgress = tag.getDouble("FightProgress");
        data.staminaProgress = tag.getDouble("StaminaProgress");
        data.buildingProgress = tag.getDouble("BuildingProgress");
        data.craftingProgress = tag.getDouble("CraftingProgress");
        data.magicProgress = tag.getDouble("MagicProgress");
    }

}
