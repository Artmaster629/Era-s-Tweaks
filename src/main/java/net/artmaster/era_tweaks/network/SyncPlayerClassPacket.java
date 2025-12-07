package net.artmaster.era_tweaks.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncPlayerClassPacket(CompoundTag data) implements CustomPacketPayload {

    public static final Type<SyncPlayerClassPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "sync_player_class"));

    public static final StreamCodec<FriendlyByteBuf, SyncPlayerClassPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeNbt(pkt.data),
                    buf -> new SyncPlayerClassPacket(buf.readNbt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


