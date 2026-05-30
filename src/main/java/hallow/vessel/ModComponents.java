package hallow.vessel;

import static hallow.vessel.Vessel.*;
import java.util.UUID;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

public class ModComponents {
    protected static void registerModComponents() {
        LOGGER.info("registering {} components", MOD_ID);
    }

    public static final ComponentType<UUID> SOUL_UUID = Registry.register(
    		Registries.DATA_COMPONENT_TYPE,
    		Identifier.of(MOD_ID, "soul_uuid"),
    		ComponentType.<UUID>builder().codec(Uuids.CODEC).build()
			);
            
    public static final ComponentType<String> SOUL_NAME = Registry.register(
    		Registries.DATA_COMPONENT_TYPE,
    		Identifier.of(MOD_ID, "soul_name"),
    		ComponentType.<String>builder().codec(Codec.STRING).build()
			);

    public static final ComponentType<Boolean> ACTIVE =
            Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MOD_ID, "active"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build()
            );
}
