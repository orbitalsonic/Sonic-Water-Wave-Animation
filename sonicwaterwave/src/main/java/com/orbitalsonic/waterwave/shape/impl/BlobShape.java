package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Organic symmetric blob using cubic segments between radial samples.
 */
public final class BlobShape implements ShapeGenerator {

    private static final int POINTS = 8;

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
        float cy = (top + bottom) / 2f;
        float baseR = Math.min(w, h) * 0.40f;

        float[] px = new float[POINTS];
        float[] py = new float[POINTS];
        for (int i = 0; i < POINTS; i++) {
            double ang = -Math.PI / 2d + (2 * Math.PI * i) / POINTS;
            float wobble = 1f + 0.14f * (float) Math.sin(ang * 3d + 0.6f);
            float r = baseR * wobble;
            px[i] = clamp(cx + (float) Math.cos(ang) * r, left, right);
            py[i] = clamp(cy + (float) Math.sin(ang) * r, top, bottom);
        }

        path.moveTo(px[0], py[0]);
        for (int i = 0; i < POINTS; i++) {
            int ni = (i + 1) % POINTS;
            float c1x = px[i] + (px[ni] - px[(i - 1 + POINTS) % POINTS]) * 0.18f;
            float c1y = py[i] + (py[ni] - py[(i - 1 + POINTS) % POINTS]) * 0.18f;
            float c2x = px[ni] - (px[(ni + 1) % POINTS] - px[i]) * 0.18f;
            float c2y = py[ni] - (py[(ni + 1) % POINTS] - py[i]) * 0.18f;
            path.cubicTo(c1x, c1y, c2x, c2y, px[ni], py[ni]);
        }
        path.close();
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
