package hallow.vessel.blockEntity.custom;

import hallow.vessel.Magnetic;
import hallow.vessel.blockEntity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectroMagnetBlockEntity extends BlockEntity implements Magnetic {
    public ElectroMagnetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTROMAGNET_BLOCK_ENTITY, pos, state);
    }

    @Override
    public boolean isMagnetActive(World world, BlockPos pos) {
        return world.isReceivingRedstonePower(pos);
    }

    @Override
    public double getStrength() {
        return 0.12;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ElectroMagnetBlockEntity blockEntity) {
        blockEntity.attractItems(world, pos);
    }
}
