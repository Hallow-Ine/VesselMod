package hallow.vessel;

import hallow.vessel.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VesselDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        System.out.println("BlockTags");
        pack.addProvider(ModItemTagProvider::new);
        System.out.println("ItemTags");
        pack.addProvider(ModLootTableProvider::new);
        System.out.println("LootTables");
        pack.addProvider(ModModelProvider::new);
        System.out.println("Models");
        pack.addProvider(ModRecipeProvider::new);
        System.out.println("Recipes");
	}
}
