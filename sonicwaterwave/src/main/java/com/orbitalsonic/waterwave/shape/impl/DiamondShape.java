package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Symmetric rhombus with equal width and height, centered.
 */
public final class DiamondShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float avail = Math.min(d.viewWidth - pad, d.viewHeight - pad);
        if (d.contentMode) {
            avail -= 2.2f * bw;
        }
        avail = Math.max(1f, avail);
        float cx = d.viewWidth / 2f;
        float cy = d.viewHeight / 2f;
        float half = avail / 2f - (d.contentMode ? bw * 0.55f : bw * 0.5f);
        half = Math.max(1f, half);

        path.moveTo(cx, cy - half);
        path.lineTo(cx + half, cy);
        path.lineTo(cx, cy + half);
        path.lineTo(cx - half, cy);
        path.close();
    }
}
