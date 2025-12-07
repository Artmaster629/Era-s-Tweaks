package net.artmaster.era_tweaks.api.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;


public class PlayerClassData {

    private String player_class = "unknown_class";
    private String player_subclass = "unknown_subclass";

    private List<String> player_skills = new ArrayList<>();

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
        data.player_skills.clear();

        ListTag skillsTag = tag.getList("PlayerSkills", Tag.TAG_STRING);
        for(int i = 0; i < skillsTag.size(); i++) {
            data.player_skills.add(skillsTag.getString(i));
        }
    }

}
