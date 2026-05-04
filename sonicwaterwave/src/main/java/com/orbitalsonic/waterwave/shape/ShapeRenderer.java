package com.orbitalsonic.waterwave.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

import com.orbitalsonic.waterwave.shape.factory.ShapeFactory;
import com.orbitalsonic.waterwave.shape.model.ShapeDimensions;

/**
 * Builds shape paths, clips the canvas to the content outline for fills, and draws the border stroke.
 */
public final class ShapeRenderer {

    private final Path borderPath = new Path();
    private final Path contentPath = new Path();

    private ShapeType shapeType = ShapeType.CIRCLE;

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType != null ? shapeType : ShapeType.CIRCLE;
    }

    @NonNull
    public ShapeType getShapeType() {
        return shapeType;
    }

    @NonNull
    public Path getBorderPath() {
        return borderPath;
    }

    @NonNull
    public Path getContentPath() {
        return contentPath;
    }

    public void rebuildPaths(
            int viewWidth,
            int viewHeight,
            float shapePadding,
            float borderWidth,
            float cornerRadius,
            int starSpikes
    ) {
        if (viewWidth <= 0 || viewHeight <= 0) {
            return;
        }
        ShapeGenerator generator = ShapeFactory.create(shapeType);
        ShapeDimensions borderDim = new ShapeDimensions(
                viewWidth, viewHeight, shapePadding, borderWidth, cornerRadius, starSpikes, false
        );
        ShapeDimensions contentDim = new ShapeDimensions(
                viewWidth, viewHeight, shapePadding, borderWidth, cornerRadius, starSpikes, true
        );
        borderPath.rewind();
        contentPath.rewind();
        generator.appendShape(borderPath, borderDim);
        generator.appendShape(contentPath, contentDim);
    }

    /**
     * Saves canvas state, clips to the content path, runs {@code drawInside}, then restores.
     */
    public void clipContentAndDraw(@NonNull Canvas canvas, @NonNull Runnable drawInside) {
        canvas.save();
        canvas.clipPath(contentPath);
        drawInside.run();
        canvas.restore();
    }

    public void drawBorderPath(@NonNull Canvas canvas, @NonNull Paint borderPaint) {
        canvas.drawPath(borderPath, borderPaint);
    }
}
