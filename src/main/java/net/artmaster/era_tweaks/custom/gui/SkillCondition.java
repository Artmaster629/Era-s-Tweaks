package net.artmaster.era_tweaks.custom.gui;

import java.util.List;

public class SkillCondition {

    private final boolean isRequiredAll;
    private final List<String> skills;

    public SkillCondition(List<String> skills, boolean isRequiredAll) {
        this.skills = skills;
        this.isRequiredAll = isRequiredAll;
    }

    public boolean isRequiredAll() {
        return isRequiredAll;
    }

    public List<String> skills() {
        return skills;
    }
}
