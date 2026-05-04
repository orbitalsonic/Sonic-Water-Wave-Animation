package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Symmetric droplet: sharp apex, smooth shoulders, rounded base.
 */
public final class WaterDropShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float min = d.minSide();
        float box = min - pad;
        if (d.contentMode) {
            box -= 2f * bw;
        }
        box = Math.max(1f, box);
        float cx = d.viewWidth / 2f;
        float cy = d.viewHeight / 2f;
        float halfW = box * 0.44f;
        float h = box * 1.08f;
        float top = cy - h / 2f + (d.contentMode ? bw * 0.25f : bw * 0.5f);
        float bottom = top + h - (d.contentMode ? bw * 0.5f : bw);
        float tipY = top;
        float waistY = top + h * 0.58f;

        path.moveTo(cx, tipY);
        path.cubicTo(
                cx + halfW * 0.2f, top + h * 0.12f,
                cx + halfW, top + h * 0.28f,
                cx + halfW, waistY
        );
        path.quadTo(cx + halfW * 0.85f, bottom, cx, bottom);
        path.quadTo(cx - halfW * 0.85f, bottom, cx - halfW, waistY);
        path.cubicTo(
                cx - halfW, top + h * 0.28f,
                cx - halfW * 0.2f, top + h * 0.12f,
                cx, tipY
        );
        path.close();
    }
}
