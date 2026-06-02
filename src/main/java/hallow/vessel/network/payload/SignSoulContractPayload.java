package hallow.vessel.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public record SignSoulContractPayload(Hand hand) implements CustomPayload {

    public static final Id<SignSoulContractPayload> ID =
            new Id<>(Identifier.of("vessel", "sign_soul_contract"));

    public static final PacketCodec<RegistryByteBuf, SignSoulContractPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.indexed(
            index -> Hand.values()[index],
            Hand::ordinal
        ), SignSoulContractPayload::hand, SignSoulContractPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
