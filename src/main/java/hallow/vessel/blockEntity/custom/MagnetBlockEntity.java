package hallow.vessel.blockEntity.custom;

import java.util.List;

import hallow.vessel.Magnetic;
import hallow.vessel.blockEntity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagnetBlockEntity extends BlockEntity implements Magnetic {

    public MagnetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGNET_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, MagnetBlockEntity blockEntity) {
        blockEntity.attractItems(world, pos);
    }
}
