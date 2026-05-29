package hallow.vessel;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCurseExecutioner {

    public static void cursePlayer(ServerPlayerEntity player){
        if (player == null) return;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 4000, 1, true, true));
    }
    
}