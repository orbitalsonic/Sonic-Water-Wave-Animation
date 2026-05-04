package com.orbitalsonic.waterwave.shape;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Strategy for building a single shape outline. Implementations must not retain the {@link Path}.
 */
public interface ShapeGenerator {

    /**
     * Appends geometry to {@code path}. Caller is responsible for {@link Path#rewind()} before calling.
     */
    void appendShape(Path path, ShapeDimensions dimensions);
}
