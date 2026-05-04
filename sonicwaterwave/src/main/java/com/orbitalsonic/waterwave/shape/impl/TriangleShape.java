package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Equilateral triangle centered in the padded bounds, point-up.
 */
public final class TriangleShape implements ShapeGenerator {

    private static final float SQRT3 = 1.7320508f;

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float availW = d.viewWidth - pad;
        float availH = d.viewHeight - pad;
        float cx = d.viewWidth / 2f;
        float cy = d.viewHeight / 2f;

        float side = Math.min(availW, 2f * availH / SQRT3);
        if (d.contentMode) {
            side -= 2.2f * bw;
        }
        side = Math.max(1f, side);
        float h = side * SQRT3 / 2f;

        float topY = cy - h / 2f + (d.contentMode ? bw * 0.35f : bw * 0.5f);
        float bottomY = topY + h - (d.contentMode ? bw * 0.7f : bw);
        float halfBase = side / 2f - (d.contentMode ? bw * 0.55f : bw * 0.5f);
        halfBase = Math.max(1f, halfBase);

        path.moveTo(cx, topY);
        path.lineTo(cx + halfBase, bottomY);
        path.lineTo(cx - halfBase, bottomY);
        path.close();
    }
}
