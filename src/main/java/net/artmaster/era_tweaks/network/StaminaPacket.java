package net.artmaster.era_tweaks.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StaminaPacket(int stamina) implements CustomPacketPayload {

    public static final Type<StaminaPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "stamita_pkt"));

    public static final StreamCodec<FriendlyByteBuf, StaminaPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeInt(pkt.stamina),
                    buf -> new StaminaPacket(buf.readInt())
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


