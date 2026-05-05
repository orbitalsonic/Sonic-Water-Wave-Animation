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
        float inset = d.shapePadding + d.borderWidth;
        float left = inset;
        float top = inset;
        float right = d.viewWidth - inset;
        float bottom = d.viewHeight - inset;
        if (right <= left || bottom <= top) {
            return;
        }

        float w = right - left;
        float h = bottom - top;
        float cx = (left + right) / 2f;
        float tipY = top;
        float baseY = bottom;
        float halfW = w * 0.30f;
        float shoulderY = top + h * 0.36f;
        float waistY = top + h * 0.63f;

        path.moveTo(cx, tipY);
        path.cubicTo(
                cx + halfW * 0.12f, top + h * 0.10f,
                cx + halfW, shoulderY,
                cx + halfW, waistY
        );
        path.cubicTo(
                cx + halfW, top + h * 0.84f,
                cx + halfW * 0.44f, baseY,
                cx, baseY
        );
        path.cubicTo(
                cx - halfW * 0.44f, baseY,
                cx - halfW, top + h * 0.84f,
                cx - halfW, waistY
        );
        path.cubicTo(
                cx - halfW, shoulderY,
                cx - halfW * 0.12f, top + h * 0.10f,
                cx, tipY
        );
        path.close();
    }
}
