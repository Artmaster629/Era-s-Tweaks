package net.artmaster.era_tweaks.network.main_packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ClientActionPacket(String key, int actionType) implements CustomPacketPayload {

    public static final Type<ClientActionPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "client_action_packet"));

    public static final StreamCodec<FriendlyByteBuf, ClientActionPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeUtf(pkt.key);
                        buf.writeInt(pkt.actionType);
                        },
                    buf -> new ClientActionPacket(
                            buf.readUtf(),
                            buf.readInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


