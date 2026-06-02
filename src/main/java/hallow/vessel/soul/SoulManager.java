package hallow.vessel.soul;

import hallow.vessel.component.ModComponents;
import hallow.vessel.item.ModItems;
import hallow.vessel.network.ModNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SoulManager {
    /**
     * 
     * @param player
     * @return Whether the player has used their soul or not
     */
    public static Boolean isSoulBound(PlayerEntity player) {
        return ((SoulBoundPlayer)player).isSoulBound();
    }

    /**
     * binds the soul to a specific contract, use only under normal player interaction with a contract
     * @param player the victim
     * @param contract the item in the victim's hand, if it's not a soul contract (in a legal state) we return
     */
    public static void bindSoul(PlayerEntity player, ItemStack contract) {
        if(!contract.isOf(ModItems.SOUL_CONTRACT)) return;

        if(contract.contains(ModComponents.SOUL_UUID)) return;


        player.sendMessage(Text.literal("player clicked with uuid: " + player.getUuidAsString()));
        
        ((SoulBoundPlayer) player).bindSoul();
        if(!player.getWorld().isClient()) ModNetworking.syncClientSoul((ServerPlayerEntity)player);

        contract.set(ModComponents.SOUL_UUID, player.getUuid());
        contract.set(ModComponents.SOUL_NAME, player.getNameForScoreboard());
    }

    /**
     * Frees the victim's soul, used mostly when the contract is destroyed
     * @param player the victim
     */
    public static void unBindSoul(PlayerEntity player){
        ((SoulBoundPlayer)player).unBindSoul();
        if(!player.getWorld().isClient()) ModNetworking.syncClientSoul((ServerPlayerEntity)player);
    }
    
    /**
     * sets whether the player's soul is bound without doing anything else except syncing, to be used under circumstances outside of direct player interaction with a contract
     * @param player the victim
     * @param bound true if they don't have a soul
     */
    public static void setSoulBound(PlayerEntity player, boolean bound) {
        ((SoulBoundPlayer)player).setSoulBound(bound);
        if(!player.getWorld().isClient()) ModNetworking.syncClientSoul((ServerPlayerEntity)player);
    }
}
