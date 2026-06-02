package hallow.vessel.network.handler;

import hallow.vessel.network.payload.SyncPlayerSoulStatePayload;
import hallow.vessel.soul.SoulManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ClientPayloadHandlers {

    public static void handleSyncSoulState(SyncPlayerSoulStatePayload payload, ClientPlayNetworking.Context context) {
        SoulManager.setSoulBound(context.player(), payload.bound());
    }

    private ClientPayloadHandlers() {}
}