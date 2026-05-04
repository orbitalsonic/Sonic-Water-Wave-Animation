package com.orbitalsonic.waterwave.shape.model;

/**
 * View size, insets, and options passed to {@link com.orbitalsonic.waterwave.shape.ShapeGenerator} implementations.
 */
public final class ShapeDimensions {

    public final int viewWidth;
    public final int viewHeight;
    public final float shapePadding;
    public final float borderWidth;
    /** Corner radius in px; 0 means generators pick a sensible default where applicable. */
    public final float cornerRadius;
    public final int starSpikes;
    /**
     * When true, build the inner fill path (inset for waves); when false, the outer stroke path.
     */
    public final boolean contentMode;

    public ShapeDimensions(
            int viewWidth,
            int viewHeight,
            float shapePadding,
            float borderWidth,
            float cornerRadius,
            int starSpikes,
            boolean contentMode
    ) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.shapePadding = shapePadding;
        this.borderWidth = borderWidth;
        this.cornerRadius = cornerRadius;
        this.starSpikes = starSpikes;
        this.contentMode = contentMode;
    }

    public int minSide() {
        return Math.min(viewWidth, viewHeight);
    }
}
