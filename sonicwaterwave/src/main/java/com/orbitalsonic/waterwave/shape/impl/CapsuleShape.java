package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;
import android.graphics.RectF;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class CapsuleShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float w = d.viewWidth - pad;
        float h = d.viewHeight - pad;
        float left = (d.viewWidth - w) / 2f;
        float top = (d.viewHeight - h) / 2f;
        if (d.contentMode) {
            left += bw;
            top += bw;
            w -= 2f * bw;
            h -= 2f * bw;
        }
        w = Math.max(1f, w);
        h = Math.max(1f, h);
        float rx = Math.min(w, h) / 2f;
        RectF r = new RectF(left, top, left + w, top + h);
        path.addRoundRect(r, rx, rx, Path.Direction.CCW);
    }
}
