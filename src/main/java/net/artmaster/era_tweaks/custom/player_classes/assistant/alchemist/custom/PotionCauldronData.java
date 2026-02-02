package net.artmaster.era_tweaks.custom.player_classes.assistant.alchemist.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PotionCauldronData extends SavedData {

    public static final String ID = "era_tweaks_potion_cauldron";

    private final Map<BlockPos, PotionContents> potions = new HashMap<>();

    public static final Factory<PotionCauldronData> FACTORY =
            new Factory<>(
                    PotionCauldronData::new,
                    PotionCauldronData::load,
                    null
            );

    public static PotionCauldronData get(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("PotionCauldronData accessed on client");
        }

        return serverLevel.getDataStorage()
                .computeIfAbsent(FACTORY, ID);
    }

    public static void set(Level level, BlockPos pos, @Nullable PotionContents contents) {
        PotionCauldronData data = get(level);

        if (contents == null || contents == PotionContents.EMPTY) {
            data.potions.remove(pos);
        } else {
            data.potions.put(pos, contents);
        }

        data.setDirty();
    }

    public static PotionContents get(Level level, BlockPos pos) {
        return get(level).potions.get(pos);
    }

    /* ===== SAVE / LOAD ===== */

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();

        for (var entry : potions.entrySet()) {
            CompoundTag t = new CompoundTag();
            t.putLong("Pos", entry.getKey().asLong());

            PotionContents.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue())
                        .result()
                        .ifPresent(potionTag -> t.put("Potion", potionTag));
            list.add(t);

        }

        tag.put("Potions", list);
        return tag;
    }

    private static PotionCauldronData load(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        PotionCauldronData data = new PotionCauldronData();

        ListTag list = tag.getList("Potions", Tag.TAG_COMPOUND);
        for (Tag element : list) {
            CompoundTag t = (CompoundTag) element;
            BlockPos pos = BlockPos.of(t.getLong("Pos"));

            PotionContents contents =
                    PotionContents.CODEC.parse(NbtOps.INSTANCE, t.get("Potion"))
                            .result()
                            .orElse(PotionContents.EMPTY);

            data.potions.put(pos, contents);
        }

        return data;
    }

}

