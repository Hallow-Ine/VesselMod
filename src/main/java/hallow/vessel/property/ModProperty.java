package hallow.vessel.property;

import net.minecraft.state.property.IntProperty;

public class ModProperty {

    public static final int ALTAR_SIZE_X_MIN = 0;
    public static final int ALTAR_SIZE_X_MAX = 2;
    public static final int ALTAR_SIZE_Z_MIN = 0;
    public static final int ALTAR_SIZE_Z_MAX = 2;

    public static final IntProperty ALTAR_SIZE_X = IntProperty.of("altar_pos_x", 0, 2);
    public static final IntProperty ALTAR_SIZE_Z = IntProperty.of("altar_pos_y", 0, 2);

}
