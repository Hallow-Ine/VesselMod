package hallow.vessel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hallow.vessel.soul.SoulBoundPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SoulBoundPlayer{
    
    @Unique
    private Boolean hasBoundSoul = false;
    
    @Override
    public Boolean isSoulBound() {
        return hasBoundSoul;
    }

    @Override
    public void bindSoul() {
        hasBoundSoul = true;
    }

    @Override
    public void unBindSoul() {
        hasBoundSoul = false;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeUsedItems(NbtCompound nbt, CallbackInfo ci) {

        nbt.putBoolean("HasBoundSoul", hasBoundSoul);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readUsedItems(NbtCompound nbt, CallbackInfo ci) {
        hasBoundSoul = nbt.contains("HasBoundSoul") && nbt.getBoolean("HasBoundSoul");

    }

}
