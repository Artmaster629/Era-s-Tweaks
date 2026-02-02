package net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class PlayerTotemData {

    private BlockPos pos; // может быть null

    public BlockPos getPos() {
        return pos;
    }

    public void setNewPos(BlockPos newPos) {
        this.pos = newPos;
    }

    public static CompoundTag save(PlayerTotemData data) {
        CompoundTag tag = new CompoundTag();

        if (data.pos != null) {
            tag.putLong("pos", data.pos.asLong());
        }

        return tag;
    }

    public static void load(PlayerTotemData data, CompoundTag tag) {
        if (tag.contains("pos")) {
            data.pos = BlockPos.of(tag.getLong("pos"));
        } else {
            data.pos = null;
        }
    }
}


