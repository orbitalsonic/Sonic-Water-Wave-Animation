package com.orbitalsonic.waterwave.shape.impl;

import android.graphics.Path;

import com.orbitalsonic.waterwave.shape.ShapeGenerator;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Tumbler profile: bowed top rim, slightly concave sides, flat base.
 */
public final class GlassShape implements ShapeGenerator {

    private static final float TOP_BOW = 0.06f;
    private static final float SIDE_INSET = 0.07f;
    private static final float GLASS_WALL = 0.04f;

    @Override
    public void appendShape(Path path, ShapeDimensions d) {
        float pad = d.shapePadding;
        float bw = d.borderWidth;
        float w = d.viewWidth - pad;
        float h = d.viewHeight - pad;
        float left = (d.viewWidth - w) / 2f;
        float top = (d.viewHeight - h) / 2f;

        float wall = d.contentMode ? Math.max(bw, w * GLASS_WALL) : 0f;
        if (d.contentMode) {
            left += wall;
            top += wall;
            w -= 2f * wall;
            h -= 2f * wall;
        }

        float midX = left + w / 2f;
        float topInset = w * (0.12f - SIDE_INSET * 0.5f);
        float bottomInset = w * (0.18f + SIDE_INSET);
        float xTl = left + topInset;
        float xTr = left + w - topInset;
        float xBl = left + bottomInset;
        float xBr = left + w - bottomInset;
        float yTop = top + bw * 0.5f;
        float yBot = top + h - bw * 0.5f;
        float rimDip = w * TOP_BOW;

        path.moveTo(xTl, yTop);
        path.quadTo(midX, yTop + rimDip, xTr, yTop);
        path.cubicTo(
                xTr + w * 0.02f, top + h * 0.35f,
                xBr + w * 0.015f, yBot - h * 0.08f,
                xBr, yBot
        );
        path.lineTo(xBl, yBot);
        path.cubicTo(
                xBl - w * 0.015f, yBot - h * 0.08f,
                xTl - w * 0.02f, top + h * 0.35f,
                xTl, yTop
        );
        path.close();
    }
}
