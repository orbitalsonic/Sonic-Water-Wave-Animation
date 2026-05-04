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
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float size = d.minSide() - pad;
        if (d.contentMode) {
            size -= 2f * bw;
        }
        size = Math.max(1f, size);
        float cx = d.viewWidth / 2f;
        float cy = d.viewHeight / 2f;
        float baseR = size * 0.48f;

        float[] px = new float[POINTS];
        float[] py = new float[POINTS];
        for (int i = 0; i < POINTS; i++) {
            double ang = -Math.PI / 2d + (2 * Math.PI * i) / POINTS;
            float wobble = 1f + 0.2f * (float) Math.sin(ang * 3d + 0.6f);
            float r = baseR * wobble;
            px[i] = cx + (float) Math.cos(ang) * r;
            py[i] = cy + (float) Math.sin(ang) * r;
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
}
