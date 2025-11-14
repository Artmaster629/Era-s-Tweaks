package net.artmaster.era_tweaks.api.upgrades;

import net.artmaster.era_tweaks.ModMain;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MyAttachments {
    public static final AttachmentType<PlayerSkillsData> PLAYER_SKILLS =
            AttachmentType.builder(PlayerSkillsData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, PlayerSkillsData>() {
                        @Override
                        public CompoundTag write(PlayerSkillsData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public PlayerSkillsData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            PlayerSkillsData data = new PlayerSkillsData();
                            data.load(data, tag);
                            return data;
                        }
                    })
                    .copyOnDeath()
                    .build();

    public static void register() {
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "player_skills"),
                PLAYER_SKILLS
        );
    }
}