package hallow.vessel.property.enumeration;

import net.minecraft.util.StringIdentifiable;

public enum AltarPart implements StringIdentifiable {
    CORE("core"),
    EDGE("edge"),
    CORNER("corner");

    private final String name;

    private AltarPart(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this.name;
    }
}
