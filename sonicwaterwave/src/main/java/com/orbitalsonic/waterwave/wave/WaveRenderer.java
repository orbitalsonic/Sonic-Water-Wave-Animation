package com.orbitalsonic.waterwave.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

/**
 * Path-based multi-layer wave fill. Caller must clip the canvas (e.g. via {@link com.orbitalsonic.waterwave.shape.ShapeRenderer}) before drawing.
 */
public final class WaveRenderer {

    public static final float DEFAULT_BACK_ALPHA = 0.55f;
    public static final float DEFAULT_MIDDLE_ALPHA = 0.45f;

    private static final float WAVE_X_STEP_PX = 3f;
    private static final float PHASE_OFFSET_RATIO = 6.25f;

    private final Path backPath = new Path();
    private final Path middlePath = new Path();
    private final Path frontPath = new Path();
    private final Paint backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint middlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint frontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private WaveType waveType = WaveType.SINE;
    private boolean thirdLayerEnabled;

    public WaveRenderer() {
        backPaint.setStyle(Paint.Style.FILL);
        middlePaint.setStyle(Paint.Style.FILL);
        frontPaint.setStyle(Paint.Style.FILL);
    }

    public void setWaveType(WaveType waveType) {
        this.waveType = waveType != null ? waveType : WaveType.SINE;
    }

    @NonNull
    public WaveType getWaveType() {
        return waveType;
    }

    public void setThirdLayerEnabled(boolean enabled) {
        this.thirdLayerEnabled = enabled;
    }

    public boolean isThirdLayerEnabled() {
        return thirdLayerEnabled;
    }

    public void setBehindWaveColor(int color) {
        backPaint.setColor(applyAlpha(color, DEFAULT_BACK_ALPHA));
    }

    public void setMiddleWaveColor(int color) {
        middlePaint.setColor(applyAlpha(color, DEFAULT_MIDDLE_ALPHA));
    }

    public void setFrontWaveColor(int color) {
        frontPaint.setColor(color);
    }

    /**
     * Draws stacked wave fills. Canvas must already be clipped to the liquid region.
     */
    public void drawWaves(
            @NonNull Canvas canvas,
            int viewWidth,
            int viewHeight,
            int progress,
            int max,
            int strongPercent,
            int waveOffsetPercent,
            float shiftPhase,
            float waveAmplitudeMultiplier,
            float amplitudePulse
    ) {
        if (viewWidth <= 0 || viewHeight <= 0) {
            return;
        }
        int waveSpan = Math.min(viewWidth, viewHeight);
        double angularFrequency = (2.0 * Math.PI) / waveSpan;
        float level = (((max - progress) / (float) Math.max(1, max)) * waveSpan)
                + ((viewHeight / 2f) - (waveSpan / 2f));
        float phaseSeparation = ((waveOffsetPercent - 50f) / 100f) * PHASE_OFFSET_RATIO;

        float baseAmplitude = strongPercent * (waveSpan / 20f) / 100f;
        float amplitude = Math.max(0.5f, baseAmplitude * waveAmplitudeMultiplier * amplitudePulse);

        float slow = shiftPhase * 0.42f;
        float mid = shiftPhase * 0.72f;
        float fast = shiftPhase;

        rebuildLayerPath(backPath, viewWidth, viewHeight, angularFrequency, level, amplitude, slow);
        canvas.drawPath(backPath, backPaint);

        if (thirdLayerEnabled) {
            rebuildLayerPath(middlePath, viewWidth, viewHeight, angularFrequency, level, amplitude * 0.88f, mid);
            canvas.drawPath(middlePath, middlePaint);
        }

        rebuildLayerPath(frontPath, viewWidth, viewHeight, angularFrequency, level, amplitude, fast + phaseSeparation);
        canvas.drawPath(frontPath, frontPaint);
    }

    private void rebuildLayerPath(
            Path path,
            int viewWidth,
            int viewHeight,
            double angularFrequency,
            float level,
            float waveAmplitude,
            float phase
    ) {
        path.rewind();
        path.moveTo(0f, sampleY(0f, angularFrequency, level, waveAmplitude, phase));
        for (float x = WAVE_X_STEP_PX; x <= viewWidth; x += WAVE_X_STEP_PX) {
            path.lineTo(x, sampleY(x, angularFrequency, level, waveAmplitude, phase));
        }
        path.lineTo(viewWidth, viewHeight);
        path.lineTo(0f, viewHeight);
        path.close();
    }

    private float sampleY(
            float x,
            double angularFrequency,
            float level,
            float waveAmplitude,
            float phase
    ) {
        float angle = (float) (angularFrequency * x + phase);
        float w = WaveMath.sample(waveType, angle);
        return level + waveAmplitude * w;
    }

    private static int applyAlpha(int color, float alpha01) {
        int a = (int) (Color.alpha(color) * alpha01);
        a = Math.min(255, Math.max(0, a));
        return (color & 0x00FFFFFF) | (a << 24);
    }
}
