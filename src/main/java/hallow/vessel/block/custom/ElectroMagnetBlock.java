package hallow.vessel.block.custom;

import com.mojang.serialization.MapCodec;

import hallow.vessel.blockEntity.ModBlockEntities;
import hallow.vessel.blockEntity.custom.ElectroMagnetBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectroMagnetBlock extends BlockWithEntity {
     public static final MapCodec<hallow.vessel.block.custom.ElectroMagnetBlock> CODEC = createCodec(hallow.vessel.block.custom.ElectroMagnetBlock::new);

    @Override
    public MapCodec<hallow.vessel.block.custom.ElectroMagnetBlock> getCodec() {
        return CODEC;
    }

    public ElectroMagnetBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ElectroMagnetBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.ELECTROMAGNET_BLOCK_ENTITY, ElectroMagnetBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
