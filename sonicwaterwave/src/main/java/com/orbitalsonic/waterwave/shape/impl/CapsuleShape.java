package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;
import android.graphics.RectF;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

public final class CapsuleShape implements ShapeGenerator {

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

        RectF r = new RectF(left, top, right, bottom);
        float cornerRadius = r.height() / 2f;
        path.addRoundRect(r, cornerRadius, cornerRadius, Path.Direction.CCW);
    }
}
