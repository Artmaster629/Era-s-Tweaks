package net.artmaster.era_tweaks.registry;

import net.artmaster.era_tweaks.ModMain;
import net.artmaster.era_tweaks.custom.data.OverlayAttributesData;
import net.artmaster.era_tweaks.custom.data.PlayerClassData;
import net.artmaster.era_tweaks.custom.data.PlayerSAttrubitesData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.bowman.custom.DeathPointData;
import net.artmaster.era_tweaks.custom.player_classes.warrior.scout.custom.ScoutUberData;
import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.data.PlayerTotemData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
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

    public static final AttachmentType<DeathPointData> DEATH_POINT =
            AttachmentType.builder(DeathPointData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, DeathPointData>() {
                        @Override
                        public CompoundTag write(DeathPointData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public DeathPointData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            DeathPointData data = new DeathPointData();
                            data.load(data, tag);
                            return data;
                        }
                    })
                    .copyOnDeath()
                    .build();

    public static final AttachmentType<ScoutUberData> UBER =
            AttachmentType.builder(ScoutUberData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, ScoutUberData>() {
                        @Override
                        public CompoundTag write(ScoutUberData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public ScoutUberData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            ScoutUberData data = new ScoutUberData();
                            data.load(data, tag);
                            return data;
                        }
                    })
                    .copyOnDeath()
                    .build();

    public static final AttachmentType<OverlayAttributesData> OVERLAYS_DATA =
            AttachmentType.builder(OverlayAttributesData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, OverlayAttributesData>() {
                        @Override
                        public CompoundTag write(OverlayAttributesData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public OverlayAttributesData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            OverlayAttributesData data = new OverlayAttributesData();
                            data.load(data, tag);
                            return data;
                        }
                    })
                    .copyOnDeath()
                    .build();


    public static final AttachmentType<PlayerTotemData> TOTEM_DATA =
            AttachmentType.builder(PlayerTotemData::new)
                    .serialize(new IAttachmentSerializer<CompoundTag, PlayerTotemData>() {
                        @Override
                        public CompoundTag write(PlayerTotemData attachment, HolderLookup.Provider provider) {
                            return attachment.save(attachment);
                        }

                        @Override
                        public PlayerTotemData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                            PlayerTotemData data = new PlayerTotemData();
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
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "overlays_data"),
                OVERLAYS_DATA
        );

        //Под/классовые особенности
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "death_point"),
                DEATH_POINT
        );
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "uber_data"),
                UBER
        );
        Registry.register(
                NeoForgeRegistries.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath(ModMain.MODID, "totem_data"),
                TOTEM_DATA
        );

    }
}