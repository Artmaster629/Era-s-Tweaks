package net.artmaster.era_tweaks.custom.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;


public class PlayerClassData {

    private String player_class = "unknown";
    private String player_subclass = "unknown";

    private int upgrades_points = 0;
    private double upgrades_points_progress = 0;

    private int upgrades_count = 0;

    private List<String> player_skills = new ArrayList<>();

    public int getUpgradesCount() {return upgrades_count;}
    public void addUpgradesCount(int count) {this.upgrades_count+=count;}


    public void removeUpgradesPointsProgress(double remPoints) {this.upgrades_points_progress -= remPoints;}

    public double getUpgradesPointsProgress() {return upgrades_points_progress;}
    public void setUpgradesPointsProgress(double newPoints) {this.upgrades_points_progress = newPoints;}
    public void addUpgradesPointsProgress(double addPointsProgress) {this.upgrades_points_progress += addPointsProgress;}

    public int getUpgradesPoints() {return upgrades_points;}
    public void setUpgradesPoints(int newPoints) {this.upgrades_points = newPoints;}
    public void addUpgradesPoints(int addPoints) {this.upgrades_points += addPoints;}
    public void removeUpgradesPoints(int remPoints) {this.upgrades_points -= remPoints;}

    public String getPlayerClass() {
        return player_class;
    }
    public void changePlayerClass(String newSubClassKey) {
        this.player_class = newSubClassKey;
    }

    public String getPlayerSubClass() {
        return player_subclass;
    }
    public void changePlayerSubClass(String newSubClassKey) {
        this.player_subclass = newSubClassKey;
    }

    public List<String> getPlayerSkills() {
        return player_skills;
    }


    private boolean isActive1Enabled = false;
    public boolean isActive1Enabled() {
        return isActive1Enabled;
    }
    public void setActive1(boolean newValue) {
        this.isActive1Enabled = newValue;
    }

    private boolean isActive1onCooldown = false;
    public boolean isActive1onCooldown() {
        return isActive1onCooldown;
    }
    public void setActive1Cooldown(boolean newValue) {
        this.isActive1onCooldown = newValue;
    }

    private boolean isActive2Enabled = false;
    public boolean isActive2Enabled() {
        return isActive2Enabled;
    }
    public void setActive2(boolean newValue) {
        this.isActive2Enabled = newValue;
    }

    private boolean isActive2onCooldown = false;
    public boolean isActive2onCooldown() {
        return isActive2onCooldown;
    }
    public void setActive2Cooldown(boolean newValue) {
        this.isActive2onCooldown = newValue;
    }

    private boolean isActive3Enabled = false;
    public boolean isActive3Enabled() {
        return isActive3Enabled;
    }
    public void setActive3(boolean newValue) {
        this.isActive3Enabled = newValue;
    }

    private boolean isActive3onCooldown = false;
    public boolean isActive3onCooldown() {
        return isActive3onCooldown;
    }
    public void setActive3Cooldown(boolean newValue) {
        this.isActive3onCooldown = newValue;
    }

    private boolean isActive4Enabled = false;
    public boolean isActive4Enabled() {
        return isActive4Enabled;
    }
    public void setActive4(boolean newValue) {
        this.isActive4Enabled = newValue;
    }

    private boolean isActive4onCooldown = false;
    public boolean isActive4onCooldown() {
        return isActive4onCooldown;
    }
    public void setActive4Cooldown(boolean newValue) {
        this.isActive4onCooldown = newValue;
    }


    public static CompoundTag save(PlayerClassData data) {
        CompoundTag tag = new CompoundTag();
        tag.putString("PlayerClass", data.player_class);
        tag.putString("PlayerSubClass", data.player_subclass);
        tag.putInt("PlayerUpgradesPoints", data.upgrades_points);
        tag.putInt("PlayerUpgradesCount", data.upgrades_count);
        tag.putDouble("PlayerUpgradesPointsProgress", data.upgrades_points_progress);
        tag.putBoolean("IsActive1Enabled", data.isActive1Enabled);
        tag.putBoolean("IsActive1onCooldown", data.isActive1onCooldown);
        tag.putBoolean("IsActive2Enabled", data.isActive2Enabled);
        tag.putBoolean("IsActive2onCooldown", data.isActive2onCooldown);
        tag.putBoolean("IsActive3Enabled", data.isActive3Enabled);
        tag.putBoolean("IsActive3onCooldown", data.isActive3onCooldown);
        tag.putBoolean("IsActive4Enabled", data.isActive4Enabled);
        tag.putBoolean("IsActive4onCooldown", data.isActive4onCooldown);
        ListTag skillsTag = new ListTag();
        for(String s : data.player_skills) {
            skillsTag.add(StringTag.valueOf(s));
        }
        tag.put("PlayerSkills", skillsTag);

        return tag;
    }

    public static void load(PlayerClassData data, CompoundTag tag) {
        data.player_class = tag.getString("PlayerClass");
        data.player_subclass = tag.getString("PlayerSubClass");
        data.upgrades_points = tag.getInt("PlayerUpgradesPoints");
        data.upgrades_points_progress = tag.getDouble("PlayerUpgradesPointsProgress");
        data.upgrades_count = tag.getInt("PlayerUpgradesCount");
        data.isActive1Enabled = tag.getBoolean("IsActive1Enabled");
        data.isActive1onCooldown = tag.getBoolean("IsActive1onCooldown");
        data.isActive2Enabled = tag.getBoolean("IsActive2Enabled");
        data.isActive2onCooldown = tag.getBoolean("IsActive2onCooldown");
        data.isActive3Enabled = tag.getBoolean("IsActive3Enabled");
        data.isActive3onCooldown = tag.getBoolean("IsActive3onCooldown");
        data.isActive4Enabled = tag.getBoolean("IsActive4Enabled");
        data.isActive4onCooldown = tag.getBoolean("IsActive4onCooldown");
        data.player_skills.clear();

        ListTag skillsTag = tag.getList("PlayerSkills", Tag.TAG_STRING);
        for(int i = 0; i < skillsTag.size(); i++) {
            data.player_skills.add(skillsTag.getString(i));
        }
    }

}
