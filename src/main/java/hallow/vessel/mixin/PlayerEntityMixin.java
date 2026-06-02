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
    private Boolean isSoulBound = false;
    
    @Override
    public Boolean isSoulBound() {
        return isSoulBound;
    }

    @Override
    public void bindSoul() {
        isSoulBound = true;
    }

    @Override
    public void unBindSoul() {
        isSoulBound = false;
    }

    @Override
    public void setSoulBound(boolean bound) {
        isSoulBound = bound;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeUsedItems(NbtCompound nbt, CallbackInfo ci) {

        nbt.putBoolean("HasBoundSoul", isSoulBound);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readUsedItems(NbtCompound nbt, CallbackInfo ci) {
        isSoulBound = nbt.contains("HasBoundSoul") && nbt.getBoolean("HasBoundSoul");

    }

}
