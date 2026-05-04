package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class CircleShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        int min = d.minSide();
        float cx = (d.viewWidth - min) / 2f;
        float cy = (d.viewHeight - min) / 2f;
        float half = min / 2f;
        if (d.contentMode) {
            float pad = d.shapePadding;
            cx += pad / 2f;
            cy += pad / 2f;
            min = Math.max(0, min - (int) pad);
            half = min / 2f;
        }
        float centerX = cx + half;
        float centerY = cy + half;
        float r = Math.max(0f, half - d.borderWidth);
        path.addCircle(centerX, centerY, r, Path.Direction.CCW);
    }
}
