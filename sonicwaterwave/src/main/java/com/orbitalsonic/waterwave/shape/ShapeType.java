package com.orbitalsonic.waterwave.shape;

/**
 * All supported clip shapes for {@link com.orbitalsonic.waterwave.view.WaterWaveView}.
 * Legacy XML integer values are preserved for 1–9; new shapes use 10–12.
 */
public enum ShapeType {
    CIRCLE(1),
    WATER_DROP(2),
    GLASS(3),
    HEART(4),
    STAR(5),
    SQUARE(6),
    RECTANGLE(7),
    TRIANGLE(8),
    DIAMOND(9),
    ROUNDED_RECTANGLE(10),
    CAPSULE(11),
    BLOB(12);

    public final int legacyXmlValue;

    ShapeType(int legacyXmlValue) {
        this.legacyXmlValue = legacyXmlValue;
    }

    public static ShapeType fromLegacyXmlValue(int value) {
        for (ShapeType type : values()) {
            if (type.legacyXmlValue == value) {
                return type;
            }
        }
        return CIRCLE;
    }
}
