package hallow.vessel.network.handler;

import hallow.vessel.component.ModComponents;
import hallow.vessel.item.ModItems;
import hallow.vessel.network.payload.SignSoulContractPayload;
import hallow.vessel.soul.SoulManager;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ServerPayloadHandlers {

    public static void handleSignSoulContract(SignSoulContractPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();

        ItemStack item = player.getStackInHand(payload.hand());

        if (!item.isOf(ModItems.SOUL_CONTRACT)) return;
        if (SoulManager.isSoulBound(player)) return;
        if (item.contains(ModComponents.SOUL_UUID)) return;

        SoulManager.bindSoul(player, item);
    }

    private ServerPayloadHandlers() {}
}