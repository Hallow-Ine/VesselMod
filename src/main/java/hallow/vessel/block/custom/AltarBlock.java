package hallow.vessel.block.custom;

import hallow.vessel.property.ModProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class AltarBlock extends Block {
    public static final IntProperty POS_X = ModProperty.ALTAR_SIZE_X;
    public static final IntProperty POS_Z = ModProperty.ALTAR_SIZE_Z;
    public AltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(POS_X, 1)
                        .with(POS_Z, 1)
        );
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.north(), state.with(POS_X, 2), Block.NOTIFY_ALL);
        world.setBlockState(pos.south(), state.with(POS_X, 0), Block.NOTIFY_ALL);
        world.setBlockState(pos.east(), state.with(POS_Z, 2), Block.NOTIFY_ALL);
        world.setBlockState(pos.west(), state.with(POS_Z, 0), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().east(), state.with(POS_X, 2).with(POS_Z, 2), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().west(), state.with(POS_X, 2).with(POS_Z, 0), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().east(), state.with(POS_X, 0).with(POS_Z, 2), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().west(), state.with(POS_X, 0).with(POS_Z, 0), Block.NOTIFY_ALL);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (state.get(POS_X) == 1 && state.get(POS_Z) == 1) {
            boolean canPlace = true;
            for (int i = ModProperty.ALTAR_SIZE_X_MIN; i <  ModProperty.ALTAR_SIZE_X_MAX; i++) {
                for (int j = ModProperty.ALTAR_SIZE_Z_MIN; j < ModProperty.ALTAR_SIZE_Z_MAX; j++) {
                    canPlace = canPlace && blockState.isSideSolidFullSquare(world, blockPos, Direction.fromVector(i, 0, j));
                }
            }
            return canPlace;
        } else {
            return blockState.isOf(this);
        }
    }
}
