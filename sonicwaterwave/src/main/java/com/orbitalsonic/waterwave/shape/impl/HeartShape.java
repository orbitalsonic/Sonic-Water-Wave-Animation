package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class HeartShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        int min = d.minSide();
        float cx = (d.viewWidth - min) / 2f;
        float cy = (d.viewHeight - min) / 2f;
        float r = min;
        if (d.contentMode) {
            float pad = d.shapePadding;
            cx += pad / 2f;
            cy += pad / 2f;
            r = Math.max(0f, min - pad);
        }
        float ox = cx;
        float oy = cy;
        path.moveTo(r / 2f + ox, r / 5f + oy);
        path.cubicTo(5 * r / 14f + ox, oy, ox, r / 15f + oy, r / 28f + ox, 2 * r / 5f + oy);
        path.cubicTo(r / 14f + ox, 2 * r / 3f + oy, 3 * r / 7f + ox, 5 * r / 6f + oy, r / 2f + ox, 9 * r / 10f + oy);
        path.cubicTo(4 * r / 7f + ox, 5 * r / 6f + oy, 13 * r / 14f + ox, 2 * r / 3f + oy, 27 * r / 28f + ox, 2 * r / 5f + oy);
        path.cubicTo(r + ox, r / 15f + oy, 9 * r / 14f + ox, oy, r / 2f + ox, r / 5f + oy);
        path.close();
    }
}
