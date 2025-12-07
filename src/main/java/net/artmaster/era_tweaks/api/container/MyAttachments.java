package net.artmaster.era_tweaks.api.container;

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
    public static final AttachmentType<PlayerSAttrubitesData> PLAYER_SKILLS =
            AttachmentType.builder(PlayerSAttrubitesData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, PlayerSAttrubitesData>() {
                        @Override
                        public CompoundTag write(PlayerSAttrubitesData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public PlayerSAttrubitesData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            PlayerSAttrubitesData data = new PlayerSAttrubitesData();
                            data.load(data, tag);
                            return data;
                        }
                    })
                    .copyOnDeath()
                    .build();

    public static final AttachmentType<PlayerClassData> PLAYER_CLASS =
            AttachmentType.builder(PlayerClassData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, PlayerClassData>() {
                        @Override
                        public CompoundTag write(PlayerClassData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public PlayerClassData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            PlayerClassData data = new PlayerClassData();
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
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "player_class"),
                PLAYER_CLASS
        );
    }
}