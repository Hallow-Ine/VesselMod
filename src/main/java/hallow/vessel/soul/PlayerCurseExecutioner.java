package hallow.vessel.soul;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerCurseExecutioner {

    public static void cursePlayer(ServerPlayerEntity player){
        if (player == null) return;
        ((SoulBoundPlayer) player).unBindSoul();
        player.sendMessage(Text.literal("test message: cursed"));
    }
    
}