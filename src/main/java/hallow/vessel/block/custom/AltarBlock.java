package hallow.vessel.block.custom;

import hallow.vessel.block.ModBlocks;
import hallow.vessel.property.ModProperties;
import hallow.vessel.property.enumeration.AltarPart;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.openal.AL;

import java.util.ArrayList;

public class AltarBlock extends Block {
    public static final EnumProperty<AltarPart> PART = ModProperties.ALTAR_PART;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty LAYER = ModProperties.ALTAR_LAYER_8;

    public AltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(PART, AltarPart.CORE)
                        .with(FACING, Direction.NORTH)
                        .with(LAYER, 0)
        );
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (world.getBlockState(blockPos).canPlaceAt(world, blockPos)) {
            return this.getDefaultState()
                    .with(FACING, ctx.getHorizontalPlayerFacing());
        } else {
            return null;
        }
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Direction facing = state.get(FACING);
        BlockPos basePos = pos;
        if (state.get(PART) != AltarPart.CORE) {
            basePos = basePos.offset(facing.getOpposite());
            if (state.get(PART) == AltarPart.CORNER) {
                switch (facing) {
                    case WEST -> basePos = basePos.offset(Direction.SOUTH);
                    case EAST -> basePos = basePos.offset(Direction.NORTH);
                    case NORTH -> basePos = basePos.offset(Direction.WEST);
                    case SOUTH -> basePos = basePos.offset(Direction.EAST);
                }
            }
        }
        basePos = basePos.offset(Direction.DOWN, state.get(LAYER));

        for (int i = ModProperties.ALTAR_POS_X_MIN; i < ModProperties.ALTAR_POS_X_MAX+1; i++) {
            for (int j = 0; j < ModProperties.ALTAR_LAYER_8_MAX; j++) {
                for (int k = ModProperties.ALTAR_POS_Z_MIN; k < ModProperties.ALTAR_POS_Z_MAX+1; k++) {
                    BlockPos blockPos = basePos.add(i, j, k);
                    if (world.getBlockState(blockPos).isOf(ModBlocks.ALTAR_BLOCK)) {
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                    }
                }

            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public Direction getDirection(int i, int j) {
        Direction blockDirection = Direction.UP;
        if (i == ModProperties.ALTAR_POS_X_MIN) {
            blockDirection = Direction.WEST;
        } else if (i == ModProperties.ALTAR_POS_X_MAX) {
            blockDirection = Direction.EAST;
        } else if (j == ModProperties.ALTAR_POS_Z_MIN) {
            blockDirection = Direction.NORTH;
        } else if (j == ModProperties.ALTAR_POS_Z_MAX) {
            blockDirection = Direction.SOUTH;
        }

        if (j == ModProperties.ALTAR_POS_Z_MIN && i == ModProperties.ALTAR_POS_X_MAX) {
            blockDirection = Direction.NORTH;
        } else if (j == ModProperties.ALTAR_POS_Z_MAX && i == ModProperties.ALTAR_POS_X_MIN) {
            blockDirection = Direction.SOUTH;
        }

        return blockDirection;
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        for (int i = ModProperties.ALTAR_POS_X_MIN; i < ModProperties.ALTAR_POS_X_MAX+1; i++) {
            for (int j = 0; j < ModProperties.ALTAR_LAYER_8_MAX; j++) {
                for (int k = ModProperties.ALTAR_POS_Z_MIN; k < ModProperties.ALTAR_POS_Z_MAX+1; k++) {
                    BlockPos blockPos = pos.add(i, j, k);
                    Direction blockDirection = getDirection(i, k);
                    BlockState blockState = state.with(PART, AltarPart.CORE);
                    if(i == ModProperties.ALTAR_POS_X_MIN || i == ModProperties.ALTAR_POS_X_MAX) {
                        blockState = state.with(PART, AltarPart.EDGE).with(FACING, blockDirection);
                        if(k == ModProperties.ALTAR_POS_Z_MIN || k == ModProperties.ALTAR_POS_Z_MAX) {
                            blockState = state.with(PART, AltarPart.CORNER).with(FACING, blockDirection);;
                        }
                    } else if(k == ModProperties.ALTAR_POS_Z_MIN || k == ModProperties.ALTAR_POS_Z_MAX) {
                        blockState = state.with(PART, AltarPart.EDGE).with(FACING, blockDirection);;
                    }
                    world.setBlockState(blockPos, blockState.with(LAYER, j), Block.NOTIFY_ALL);
                }

            }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (int i = ModProperties.ALTAR_POS_X_MIN; i < ModProperties.ALTAR_POS_X_MAX+1; i++) {
            for (int j = 0; j < ModProperties.ALTAR_LAYER_8_MAX; j++) {
                for (int k = ModProperties.ALTAR_POS_Z_MIN; k < ModProperties.ALTAR_POS_Z_MAX+1; k++) {
                    BlockPos blockPos = pos.add(i, j, k);
                    if(!world.getBlockState(blockPos).isIn(BlockTags.REPLACEABLE)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART)
                .add(FACING)
                .add(LAYER);
    }
}
