package com.orbitalsonic.waterwave.shape.factory;

import androidx.annotation.NonNull;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.ShapeType;
import com.orbitalsonic.waterwave.shape.impl.BlobShape;
import com.orbitalsonic.waterwave.shape.impl.CapsuleShape;
import com.orbitalsonic.waterwave.shape.impl.CircleShape;
import com.orbitalsonic.waterwave.shape.impl.DiamondShape;
import com.orbitalsonic.waterwave.shape.impl.GlassShape;
import com.orbitalsonic.waterwave.shape.impl.HeartShape;
import com.orbitalsonic.waterwave.shape.impl.RectangleShape;
import com.orbitalsonic.waterwave.shape.impl.RoundedRectShape;
import com.orbitalsonic.waterwave.shape.impl.RoundedSquareShape;
import com.orbitalsonic.waterwave.shape.impl.StarShape;
import com.orbitalsonic.waterwave.shape.impl.TriangleShape;
import com.orbitalsonic.waterwave.shape.impl.WaterDropShape;

import java.util.EnumMap;
import java.util.Map;

/**
 * Resolves a {@link ShapeGenerator} for a {@link ShapeType}. Add new shapes by registering here.
 */
public final class ShapeFactory {

    private static final Map<ShapeType, ShapeGenerator> REGISTRY = new EnumMap<>(ShapeType.class);

    static {
        REGISTRY.put(ShapeType.CIRCLE, new CircleShape());
        REGISTRY.put(ShapeType.WATER_DROP, new WaterDropShape());
        REGISTRY.put(ShapeType.GLASS, new GlassShape());
        REGISTRY.put(ShapeType.HEART, new HeartShape());
        REGISTRY.put(ShapeType.STAR, new StarShape());
        REGISTRY.put(ShapeType.SQUARE, new RoundedSquareShape());
        REGISTRY.put(ShapeType.RECTANGLE, new RectangleShape());
        REGISTRY.put(ShapeType.TRIANGLE, new TriangleShape());
        REGISTRY.put(ShapeType.DIAMOND, new DiamondShape());
        REGISTRY.put(ShapeType.ROUNDED_RECTANGLE, new RoundedRectShape());
        REGISTRY.put(ShapeType.CAPSULE, new CapsuleShape());
        REGISTRY.put(ShapeType.BLOB, new BlobShape());
    }

    private ShapeFactory() {
    }

    @NonNull
    public static ShapeGenerator create(@NonNull ShapeType type) {
        ShapeGenerator generator = REGISTRY.get(type != null ? type : ShapeType.CIRCLE);
        return generator != null ? generator : REGISTRY.get(ShapeType.CIRCLE);
    }
}
