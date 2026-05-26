package hallow.vessel.block;

import hallow.vessel.Vessel;
import hallow.vessel.block.custom.ElectroMagnetBlock;
import hallow.vessel.block.custom.MagnetBlock;
import hallow.vessel.block.custom.PointedMagnetiteBlock;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block POINTED_MAGNETITE = registerBlock(
            "pointed_magnetite",
            new PointedMagnetiteBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DEEPSLATE_GRAY)
                            .solid()
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .nonOpaque()
                            .sounds(BlockSoundGroup.POINTED_DRIPSTONE)
                            .strength(1.5F, 3.0F)
                            .dynamicBounds()
                            .offset(AbstractBlock.OffsetType.XZ)
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .solidBlock(Blocks::never)
            )
    );

    public static final Block MAGNETITE_BLOCK = registerBlock(
            "magnetite_block",
            new Block(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DEEPSLATE_GRAY)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                            .requiresTool()
                            .strength(1.5F, 1.0F)
            )
    );

    public static final Block MAGNET_BLOCK = registerBlock(
            "magnet_block", 
            new MagnetBlock(
                AbstractBlock.Settings.create()
                            .mapColor(MapColor.DEEPSLATE_GRAY)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                            .requiresTool()
                            .strength(1.5F, 1.0F)
            ));

    public static final Block ELECTROMAGNET_BLOCK = registerBlock(
            "electromagnet_block", 
            new ElectroMagnetBlock(
                AbstractBlock.Settings.create()
                            .mapColor(MapColor.DEEPSLATE_GRAY)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                            .requiresTool()
                            .strength(1.5F, 1.0F)
            ));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Vessel.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Vessel.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Vessel.LOGGER.info("Registering Mod Blocks for" + Vessel.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(MAGNETITE_BLOCK);
            fabricItemGroupEntries.add(POINTED_MAGNETITE);
            fabricItemGroupEntries.add(MAGNET_BLOCK);
            fabricItemGroupEntries.add(ELECTROMAGNET_BLOCK);
        });
    }
}
