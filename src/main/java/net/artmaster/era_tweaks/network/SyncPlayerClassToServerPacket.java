package net.artmaster.era_tweaks.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncPlayerClassToServerPacket(String skill) implements CustomPacketPayload {

    public static final Type<SyncPlayerClassToServerPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "sync_player_class_to_server"));

    public static final StreamCodec<FriendlyByteBuf, SyncPlayerClassToServerPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.skill),
                    buf -> new SyncPlayerClassToServerPacket(buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


