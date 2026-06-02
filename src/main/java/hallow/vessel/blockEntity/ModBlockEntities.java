package hallow.vessel.blockEntity;

import hallow.vessel.Vessel;
import hallow.vessel.block.ModBlocks;
import hallow.vessel.blockEntity.custom.ElectroMagnetBlockEntity;
import hallow.vessel.blockEntity.custom.MagnetBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<MagnetBlockEntity> MAGNET_BLOCK_ENTITY =
        register("magnet_block_entity",
                MagnetBlockEntity::new,
                ModBlocks.MAGNET_BLOCK);

    public static final BlockEntityType<ElectroMagnetBlockEntity> ELECTROMAGNET_BLOCK_ENTITY =
        register("electromagnet_block_entity",
                ElectroMagnetBlockEntity::new,
                ModBlocks.ELECTROMAGNET_BLOCK);
 
    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
		Identifier id = Identifier.of(Vessel.MOD_ID, name);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
	}

    public static void registerModBlockEntities(){}
}
