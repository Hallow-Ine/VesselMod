package hallow.vessel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hallow.vessel.component.ModComponents;
import hallow.vessel.item.ModItems;
import hallow.vessel.soul.PlayerCurseExecutioner;
import hallow.vessel.soul.SoulBoundPlayer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        ItemEntity self = ((ItemEntity)(Object)this);

        if (self.getWorld().isClient()) return;

        var stack = self.getStack();

        if(!stack.isOf(ModItems.SOUL_CONTRACT)) return;

        var item_components = stack.getComponents();

        if(!item_components.contains((ModComponents.SOUL_UUID))) return;

        var uuid = item_components.get(ModComponents.SOUL_UUID);

        var player = self.getServer().getPlayerManager().getPlayer(uuid);

        var is_cursed_player_online = player != null;

        if(!is_cursed_player_online){
            ci.setReturnValue(false);
            return;
        }

        if(self.isOnFire()){
            PlayerCurseExecutioner.cursePlayer(player);
        } else {
            ((SoulBoundPlayer) player).unBindSoul();
        }

        self.getStack().onItemEntityDestroyed(self);
		self.discard();
        ci.setReturnValue(true);

    }
}
