package hallow.vessel.item;

import hallow.vessel.Vessel;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item SOUL_CONTRACT = registerItem("soul_contract", new SoulContractItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Vessel.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Vessel.LOGGER.info("Registering Mod Items for" + Vessel.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(SOUL_CONTRACT);
        });
    }
}
