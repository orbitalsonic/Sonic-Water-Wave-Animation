package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class StarShape implements ShapeGenerator {

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        int min = d.minSide();
        float cx = (d.viewWidth - min) / 2f + min / 2f;
        float cy = (d.viewHeight - min) / 2f + min / 2f;
        int spikes = Math.max(3, d.starSpikes);
        float outer;
        float inner;
        if (d.contentMode) {
            float pad = d.shapePadding;
            outer = min / 2f - d.borderWidth - pad;
            inner = min / 4f - pad;
        } else {
            outer = min / 2f - d.borderWidth;
            inner = min / 4f;
        }
        outer = Math.max(1f, outer);
        inner = Math.max(1f, Math.min(inner, outer * 0.85f));

        double rot = Math.PI / 2d * 3d;
        double step = Math.PI / spikes;

        path.moveTo(cx, cy - outer);
        for (int i = 0; i < spikes; i++) {
            path.lineTo(cx + (float) Math.cos(rot) * outer, cy + (float) Math.sin(rot) * outer);
            rot += step;
            path.lineTo(cx + (float) Math.cos(rot) * inner, cy + (float) Math.sin(rot) * inner);
            rot += step;
        }
        path.lineTo(cx, cy - outer);
        path.close();
    }
}
