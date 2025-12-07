package net.artmaster.era_tweaks.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StaminaDataPacket(double stamina) implements CustomPacketPayload {

    public static final Type<StaminaDataPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("era_tweaks", "stamita_data_pkt"));

    public static final StreamCodec<FriendlyByteBuf, StaminaDataPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeDouble(pkt.stamina),
                    buf -> new StaminaDataPacket(buf.readDouble())
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


