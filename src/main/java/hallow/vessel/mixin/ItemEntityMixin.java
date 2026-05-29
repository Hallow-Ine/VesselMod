package hallow.vessel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hallow.vessel.ModComponents;
import hallow.vessel.PlayerCurseExecutioner;
import hallow.vessel.item.ModItems;
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

        if(!self.isOnFire()) return;

        var item_components = stack.getComponents();

        if(!item_components.contains((ModComponents.SOUL_UUID_TYPE))) return;

        var uuid = item_components.get(ModComponents.SOUL_UUID_TYPE);

        var player = self.getServer().getPlayerManager().getPlayer(uuid);

        var is_cursed_player_online = player != null;

        if(is_cursed_player_online){
            PlayerCurseExecutioner.cursePlayer(player);
            self.getStack().onItemEntityDestroyed(self);
			self.discard();
            ci.setReturnValue(true);
        }
        else ci.setReturnValue(false);
    }
}
