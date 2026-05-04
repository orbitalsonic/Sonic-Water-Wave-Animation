package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;
import android.graphics.RectF;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Centered square with rounded corners (maps to {@link com.orbitalsonic.waterwave.shape.ShapeType#SQUARE}).
 */
public final class RoundedSquareShape implements ShapeGenerator {

    private static final float DEFAULT_RADIUS_RATIO = 0.08f;

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        int min = d.minSide();
        float cx = (d.viewWidth - min) / 2f;
        float cy = (d.viewHeight - min) / 2f;
        float side = min;
        if (d.contentMode) {
            float pad = d.shapePadding;
            cx += pad / 2f;
            cy += pad / 2f;
            side = Math.max(1f, min - pad);
        }
        float left = cx + d.borderWidth / 2f;
        float top = cy + d.borderWidth / 2f;
        float innerSide = side - d.borderWidth;
        innerSide = Math.max(1f, innerSide);
        float rad = d.cornerRadius > 0f
                ? Math.min(d.cornerRadius, innerSide * 0.45f)
                : innerSide * DEFAULT_RADIUS_RATIO;
        RectF r = new RectF(left, top, left + innerSide, top + innerSide);
        path.addRoundRect(r, rad, rad, Path.Direction.CCW);
    }
}
