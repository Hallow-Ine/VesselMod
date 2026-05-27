package hallow.vessel.block.custom;

import hallow.vessel.property.ModProperties;
import hallow.vessel.property.enumeration.AltarPart;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.ArrayList;

public class AltarBlock extends Block {
    public static final EnumProperty<AltarPart> ALTAR_PART = ModProperties.ALTAR_PART;

    public AltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(ALTAR_PART, AltarPart.CORE)
        );
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        for (int i = ModProperties.ALTAR_POS_X_MIN; i < ModProperties.ALTAR_POS_X_MAX+1; i++) {
            for (int j = ModProperties.ALTAR_POS_Y_MIN; j < ModProperties.ALTAR_POS_Y_MAX+1; j++) {
                for (int k = ModProperties.ALTAR_POS_Z_MIN; k < ModProperties.ALTAR_POS_Z_MAX+1; k++) {
                    BlockPos blockPos = pos.add(i, j, k);
                    BlockState blockState = state.with(ALTAR_PART, AltarPart.CORE);
                    if(i == ModProperties.ALTAR_POS_X_MIN || i == ModProperties.ALTAR_POS_X_MAX) {
                        blockState = state.with(ALTAR_PART, AltarPart.EDGE);
                        if(k == ModProperties.ALTAR_POS_Z_MIN || k == ModProperties.ALTAR_POS_Z_MAX) {
                            blockState = state.with(ALTAR_PART, AltarPart.CORNER);
                        }
                    } else if(k == ModProperties.ALTAR_POS_Z_MIN || k == ModProperties.ALTAR_POS_Z_MAX) {
                        blockState = state.with(ALTAR_PART, AltarPart.EDGE);
                    }
                    world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
                }

            }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (int i = ModProperties.ALTAR_POS_X_MIN; i < ModProperties.ALTAR_POS_X_MAX+1; i++) {
            for (int j = ModProperties.ALTAR_POS_Y_MIN; j < ModProperties.ALTAR_POS_Y_MAX+1; j++) {
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
        builder.add(ALTAR_PART);
    }
}
