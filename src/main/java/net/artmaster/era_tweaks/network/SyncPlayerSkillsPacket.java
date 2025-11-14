package net.artmaster.era_tweaks.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncPlayerSkillsPacket(CompoundTag data) implements CustomPacketPayload {

    public static final Type<SyncPlayerSkillsPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "sync_player_skills"));

    public static final StreamCodec<FriendlyByteBuf, SyncPlayerSkillsPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeNbt(pkt.data),
                    buf -> new SyncPlayerSkillsPacket(buf.readNbt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


