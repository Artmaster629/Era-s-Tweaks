package net.artmaster.era_tweaks.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenGuiPacket(String resourceId) implements CustomPacketPayload {

    public static final Type<OpenGuiPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "open_gui"));

    public static final StreamCodec<FriendlyByteBuf, OpenGuiPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.resourceId),
                    buf -> new OpenGuiPacket(buf.readUtf())
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
