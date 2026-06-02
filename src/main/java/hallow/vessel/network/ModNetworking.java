package hallow.vessel.network;

import hallow.vessel.network.handler.ClientPayloadHandlers;
import hallow.vessel.network.handler.ServerPayloadHandlers;
import hallow.vessel.network.payload.SignSoulContractPayload;
import hallow.vessel.network.payload.SyncPlayerSoulStatePayload;
import hallow.vessel.soul.SoulManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModNetworking {

    public static void register() {
        registerC2SPackets();
        registerS2CPackets();
        registerS2CReceivers();
        registerServerEvents();
    }

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(SignSoulContractPayload.ID, SignSoulContractPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(SignSoulContractPayload.ID, ServerPayloadHandlers::handleSignSoulContract);
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(SyncPlayerSoulStatePayload.ID, SyncPlayerSoulStatePayload.CODEC);
    }

    public static void registerS2CReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(SyncPlayerSoulStatePayload.ID,ClientPayloadHandlers::handleSyncSoulState);
    }

    public static void registerServerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            syncClientSoul(handler.player);
        });

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            SoulManager.setSoulBound(newPlayer, SoulManager.isSoulBound(oldPlayer));
        });
    }

    public static void syncClientSoul(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new SyncPlayerSoulStatePayload(SoulManager.isSoulBound(player)));
    }

    private ModNetworking() {}
}
