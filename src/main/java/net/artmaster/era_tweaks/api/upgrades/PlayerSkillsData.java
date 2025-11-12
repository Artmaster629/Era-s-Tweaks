package net.artmaster.era_tweaks.api.upgrades;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;


public class PlayerSkillsData {
    private final SimpleContainer container;
    private int miningLevel = 0;
    private int farmingLevel = 0;
    private int fightLevel = 0;
    private int staminaLevel = 0;
    private int buildingLevel = 0;
    private int craftingLevel = 0;
    private int magicLevel = 0;

    private int miningProgress = 0;
    private int farmingProgress = 0;
    private int fightProgress = 0;
    private int staminaProgress = 0;
    private int buildingProgress = 0;
    private int craftingProgress = 0;
    private int magicProgress = 0;

    public PlayerSkillsData(int size) {
        this.container = new SimpleContainer(size);
    }

    public SimpleContainer getContainer() {
        return container;
    }

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



    public int getMiningProgress() {
        return miningProgress;
    }
    public void setMiningProgress(int newProgress) {
        this.miningProgress = newProgress;
    }

    public int getFarmingProgress() {
        return farmingProgress;
    }
    public void setFarmingProgress(int newProgress) {
        this.farmingProgress = newProgress;
    }

    public int getFightProgress() {
        return fightProgress;
    }
    public void setFightProgress(int newProgress) {
        this.fightProgress = newProgress;
    }

    public int getStaminaProgress() {
        return staminaProgress;
    }
    public void setStaminaProgress(int newProgress) {
        this.staminaProgress = newProgress;
    }

    public int getBuildingProgress() {
        return buildingProgress;
    }
    public void setBuildingProgress(int newProgress) {
        this.buildingProgress = newProgress;
    }

    public int getCraftingProgress() {
        return craftingProgress;
    }
    public void setCraftingProgress(int newProgress) {
        this.craftingProgress = newProgress;
    }

    public int getMagicProgress() {
        return magicProgress;
    }
    public void setMagicProgress(int newProgress) {
        this.magicProgress = newProgress;
    }

    public static CompoundTag save(PlayerSkillsData data, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        tag.put("Items", data.container.createTag(registries));
        tag.putInt("Mining", data.miningLevel);
        tag.putInt("Farming", data.farmingLevel);
        tag.putInt("Fight", data.fightLevel);
        tag.putInt("Stamina", data.staminaLevel);
        tag.putInt("Building", data.buildingLevel);
        tag.putInt("Crafting", data.craftingLevel);
        tag.putInt("Magic", data.magicLevel);

        tag.putInt("MiningProgress", data.miningProgress);
        tag.putInt("FarmingProgress", data.farmingProgress);
        tag.putInt("FightProgress", data.fightProgress);
        tag.putInt("StaminaProgress", data.staminaProgress);
        tag.putInt("BuildingProgress", data.buildingProgress);
        tag.putInt("CraftingProgress", data.craftingProgress);
        tag.putInt("MagicProgress", data.magicProgress);
        return tag;
    }

    public static void load(PlayerSkillsData data, CompoundTag tag, HolderLookup.Provider registries) {
        data.container.fromTag(tag.getList("Items", 10), registries);
        data.miningLevel = tag.getInt("Mining");
        data.farmingLevel = tag.getInt("Farming");
        data.fightLevel = tag.getInt("Fight");
        data.staminaLevel = tag.getInt("Stamina");
        data.buildingLevel = tag.getInt("Building");
        data.craftingLevel = tag.getInt("Crafting");
        data.magicLevel = tag.getInt("Magic");

        data.miningProgress = tag.getInt("MiningProgress");
        data.farmingProgress = tag.getInt("FarmingProgress");
        data.fightProgress = tag.getInt("FightProgress");
        data.staminaProgress = tag.getInt("StaminaProgress");
        data.buildingProgress = tag.getInt("BuildingProgress");
        data.craftingProgress = tag.getInt("CraftingProgress");
        data.magicProgress = tag.getInt("MagicProgress");
    }
}
