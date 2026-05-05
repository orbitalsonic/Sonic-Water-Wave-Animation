package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Tumbler profile: bowed top rim, slightly concave sides, flat base.
 */
public final class GlassShape implements ShapeGenerator {

    private static final float TOP_WIDTH_RATIO = 0.80f;
    private static final float BOTTOM_WIDTH_RATIO = 0.62f;
    private static final float TOP_RIM_DIP_RATIO = 0.03f;
    private static final float SIDE_BULGE_RATIO = 0.055f;
    private static final float SIDE_NECK_IN_RATIO = 0.035f;

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

        float safeWidth = right - left;
        float safeHeight = bottom - top;
        float centerX = (left + right) / 2f;

        float topHalfWidth = safeWidth * TOP_WIDTH_RATIO * 0.5f;
        float bottomHalfWidth = safeWidth * BOTTOM_WIDTH_RATIO * 0.5f;
        float topY = top + safeHeight * 0.05f;
        float bottomY = bottom - safeHeight * 0.02f;
        float rimDip = safeHeight * TOP_RIM_DIP_RATIO;
        float sideBulge = safeWidth * SIDE_BULGE_RATIO;
        float sideNeckIn = safeWidth * SIDE_NECK_IN_RATIO;

        float xTl = centerX - topHalfWidth;
        float xTr = centerX + topHalfWidth;
        float xBl = centerX - bottomHalfWidth;
        float xBr = centerX + bottomHalfWidth;
        float yMid = topY + (bottomY - topY) * 0.45f;

        path.moveTo(xTl, topY);
        path.quadTo(centerX, topY + rimDip, xTr, topY);
        path.cubicTo(
                xTr + sideBulge, yMid,
                xBr + sideNeckIn, bottomY - safeHeight * 0.18f,
                xBr, bottomY
        );
        path.lineTo(xBl, bottomY);
        path.cubicTo(
                xBl - sideNeckIn, bottomY - safeHeight * 0.18f,
                xTl - sideBulge, yMid,
                xTl, topY
        );
        path.close();
    }
}
