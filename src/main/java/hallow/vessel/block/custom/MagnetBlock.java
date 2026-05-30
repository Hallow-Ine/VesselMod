package hallow.vessel.block.custom;

import com.mojang.serialization.MapCodec;

import hallow.vessel.blockEntity.ModBlockEntities;
import hallow.vessel.blockEntity.custom.MagnetBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagnetBlock extends BlockWithEntity {

    public static final MapCodec<hallow.vessel.block.custom.MagnetBlock> CODEC = createCodec(hallow.vessel.block.custom.MagnetBlock::new);

    @Override
    public MapCodec<hallow.vessel.block.custom.MagnetBlock> getCodec() {
        return CODEC;
    }

    public MagnetBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MagnetBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.MAGNET_BLOCK_ENTITY, MagnetBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}