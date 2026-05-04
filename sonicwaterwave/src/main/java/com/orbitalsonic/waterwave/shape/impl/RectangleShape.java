package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;
import android.graphics.RectF;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class RectangleShape implements ShapeGenerator {

    private static final float DEFAULT_RADIUS_RATIO = 0.04f;

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float w = d.viewWidth - pad;
        float h = d.viewHeight - pad;
        float left = (d.viewWidth - w) / 2f;
        float top = (d.viewHeight - h) / 2f;
        float rad = d.cornerRadius > 0f
                ? Math.min(d.cornerRadius, Math.min(w, h) * 0.35f)
                : Math.min(w, h) * DEFAULT_RADIUS_RATIO;

        if (d.contentMode) {
            left += bw;
            top += bw;
            w -= 2f * bw;
            h -= 2f * bw;
        }
        w = Math.max(1f, w);
        h = Math.max(1f, h);
        rad = Math.min(rad, Math.min(w, h) * 0.48f);

        RectF r = new RectF(left, top, left + w, top + h);
        path.addRoundRect(r, rad, rad, Path.Direction.CCW);
    }
}
