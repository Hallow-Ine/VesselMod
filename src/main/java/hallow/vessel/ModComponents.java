package hallow.vessel;

import static hallow.vessel.Vessel.*;
import java.util.UUID;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

public class ModComponents {
    protected static void registerModComponents() {
        LOGGER.info("registering {} components", MOD_ID);
    }

    // String because making it an actual UUID seemed compilcated
    public static final ComponentType<UUID> SOUL_UUID_TYPE = Registry.register(
    		Registries.DATA_COMPONENT_TYPE,
    		Identifier.of(MOD_ID, "soul_uuid"),
    		ComponentType.<UUID>builder().codec(Uuids.CODEC).build()
			);
            
}
