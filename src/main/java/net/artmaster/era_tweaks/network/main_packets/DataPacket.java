package net.artmaster.era_tweaks.network.main_packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DataPacket(CompoundTag data, int dataType) implements CustomPacketPayload {

    public static final Type<DataPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "sync_player_class"));

    public static final StreamCodec<FriendlyByteBuf, DataPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {buf.writeNbt(pkt.data); buf.writeInt(pkt.dataType);},
                    buf -> new DataPacket(buf.readNbt(), buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


