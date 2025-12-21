package net.artmaster.era_tweaks.api.container;

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
    private int upgrades_points_progress = 0;

    private List<String> player_skills = new ArrayList<>();


    public int getUpgradesPointsProgress() {return upgrades_points_progress;}
    public void setUpgradesPointsProgress(int newPoints) {this.upgrades_points_progress = newPoints;}
    public void addUpgradesPointsProgress(int addPointsProgress) {this.upgrades_points_progress += addPointsProgress;}

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

    public static CompoundTag save(PlayerClassData data) {
        CompoundTag tag = new CompoundTag();
        tag.putString("PlayerClass", data.player_class);
        tag.putString("PlayerSubClass", data.player_subclass);
        tag.putInt("PlayerUpgradesPoints", data.upgrades_points);
        tag.putInt("PlayerUpgradesPointsProgress", data.upgrades_points_progress);
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
        data.upgrades_points_progress = tag.getInt("PlayerUpgradesPointsProgress");
        data.player_skills.clear();

        ListTag skillsTag = tag.getList("PlayerSkills", Tag.TAG_STRING);
        for(int i = 0; i < skillsTag.size(); i++) {
            data.player_skills.add(skillsTag.getString(i));
        }
    }

}
