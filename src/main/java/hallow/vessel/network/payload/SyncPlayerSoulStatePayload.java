package hallow.vessel.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SyncPlayerSoulStatePayload(boolean bound) implements CustomPayload {
    public static final Id<SyncPlayerSoulStatePayload> ID =
            new Id<>(Identifier.of("vessel", "sync_player_sould_state"));

    public static final PacketCodec<RegistryByteBuf, SyncPlayerSoulStatePayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, SyncPlayerSoulStatePayload::bound, SyncPlayerSoulStatePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
