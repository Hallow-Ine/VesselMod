package hallow.vessel.property;

import hallow.vessel.property.enumeration.AltarPart;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {


    public static final int ALTAR_POS_X_MIN = -1;
    public static final int ALTAR_POS_X_MAX = 1;
    public static final int ALTAR_POS_Z_MIN = -1;
    public static final int ALTAR_POS_Z_MAX = 1;

    public static final EnumProperty<AltarPart> ALTAR_PART = EnumProperty.of("part", AltarPart.class);

    public static final int ALTAR_LAYER_8_MAX = 8;

    public static final IntProperty ALTAR_LAYER_8 = IntProperty.of("layer", 0, 7);

}
