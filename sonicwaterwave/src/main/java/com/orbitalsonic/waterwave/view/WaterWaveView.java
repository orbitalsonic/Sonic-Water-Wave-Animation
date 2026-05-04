package com.orbitalsonic.waterwave.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.orbitalsonic.waterwave.R;
import com.orbitalsonic.waterwave.animation.WaveAnimationController;
import com.orbitalsonic.waterwave.listener.OnWaveStuffListener;
import com.orbitalsonic.waterwave.shape.ShapeRenderer;
import com.orbitalsonic.waterwave.shape.ShapeType;
import com.orbitalsonic.waterwave.wave.WaveRenderer;
import com.orbitalsonic.waterwave.wave.WaveType;

import java.util.Locale;

public class WaterWaveView extends View {

    private static final int DEFAULT_PROGRESS = 405;
    private static final int DEFAULT_MAX = 1000;
    private static final int DEFAULT_STRONG = 50;
    private static final int DEFAULT_SPIKE_COUNT = 5;
    private static final float DEFAULT_PADDING = 0f;
    private static final float DEFAULT_WAVE_VECTOR = -0.25f;
    private static final int DEFAULT_WAVE_OFFSET = 25;
    private static final int DEFAULT_ANIMATION_STEP_MS = 25;
    private static final float DEFAULT_WAVE_AMPLITUDE = 1f;
    private static final float AMPLITUDE_PULSE_STRENGTH = 0.07f;
    private static final float AMPLITUDE_PULSE_PHASE_SCALE = 0.085f;

    public static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#90cbf9");
    public static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#80c5fc");
    public static final int DEFAULT_BORDER_COLOR = Color.parseColor("#000000");
    private static final float DEFAULT_BORDER_WIDTH = 5f;
    public static final int DEFAULT_TEXT_COLOR = Color.parseColor("#000000");
    private static final boolean DEFAULT_ENABLE_ANIMATION = false;
    private static final boolean DEFAULT_HIDE_TEXT = false;

    /**
     * Legacy shape enum; prefer {@link ShapeType} for new code (includes bonus shapes).
     */
    public enum Shape {
        CIRCLE(1),
        WATER_DROP(2),
        GLASS(3),
        HEART(4),
        STAR(5),
        SQUARE(6),
        RECTANGLE(7),
        TRIANGLE(8),
        DIAMOND(9),
        ROUNDED_RECTANGLE(10),
        CAPSULE(11),
        BLOB(12);

        final int value;

        Shape(int value) {
            this.value = value;
        }

        static Shape fromValue(int value) {
            for (Shape shape : values()) {
                if (shape.value == value) {
                    return shape;
                }
            }
            return CIRCLE;
        }

        @NonNull
        public ShapeType toShapeType() {
            return ShapeType.fromLegacyXmlValue(value);
        }

        @NonNull
        public static Shape fromShapeType(@NonNull ShapeType type) {
            for (Shape s : values()) {
                if (s.value == type.legacyXmlValue) {
                    return s;
                }
            }
            return CIRCLE;
        }
    }

    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final WaveRenderer waveRenderer = new WaveRenderer();
    private final WaveAnimationController waveAnimationController = new WaveAnimationController(this);
    private final Point screenSize = new Point(0, 0);

    private float shiftPhase;
    private float waveVector = DEFAULT_WAVE_VECTOR;
    private int waveOffset = DEFAULT_WAVE_OFFSET;
    private int animationStepMs = DEFAULT_ANIMATION_STEP_MS;

    private float mShapePadding = DEFAULT_PADDING;
    private int mProgress = DEFAULT_PROGRESS;
    private int mMax = DEFAULT_MAX;
    private int mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR;
    private int mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private float mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private boolean isAnimation = DEFAULT_ENABLE_ANIMATION;
    private boolean isHideText = DEFAULT_HIDE_TEXT;
    private int mStrong = DEFAULT_STRONG;
    private int mSpikes = DEFAULT_SPIKE_COUNT;
    private ShapeType mShapeType = ShapeType.CIRCLE;
    private WaveType mWaveType = WaveType.SINE;
    private float mWaveAmplitude = DEFAULT_WAVE_AMPLITUDE;
    private float mCornerRadiusPx;
    private boolean thirdWaveLayerEnabled;
    private boolean shapePathsValid;
    private OnWaveStuffListener mListener;

    public WaterWaveView(Context context) {
        this(context, null);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularWaterWaveView, defStyleAttr, 0);

        mFrontWaveColor = attributes.getColor(R.styleable.CircularWaterWaveView_frontColor, DEFAULT_FRONT_WAVE_COLOR);
        mBehindWaveColor = attributes.getColor(R.styleable.CircularWaterWaveView_behideColor, DEFAULT_BEHIND_WAVE_COLOR);
        mBorderColor = attributes.getColor(R.styleable.CircularWaterWaveView_borderColor, DEFAULT_BORDER_COLOR);
        mTextColor = attributes.getColor(R.styleable.CircularWaterWaveView_textColor, DEFAULT_TEXT_COLOR);
        mProgress = attributes.getInt(R.styleable.CircularWaterWaveView_progress, DEFAULT_PROGRESS);
        mMax = attributes.getInt(R.styleable.CircularWaterWaveView_max, DEFAULT_MAX);
        mBorderWidth = attributes.getDimension(R.styleable.CircularWaterWaveView_borderWidthSize, DEFAULT_BORDER_WIDTH);
        mStrong = attributes.getInt(R.styleable.CircularWaterWaveView_strong, DEFAULT_STRONG);
        mShapeType = ShapeType.fromLegacyXmlValue(attributes.getInt(R.styleable.CircularWaterWaveView_shapeType, 1));
        mShapePadding = attributes.getDimension(R.styleable.CircularWaterWaveView_shapePadding, DEFAULT_PADDING);
        isAnimation = attributes.getBoolean(R.styleable.CircularWaterWaveView_animatorEnable, DEFAULT_ENABLE_ANIMATION);
        isHideText = attributes.getBoolean(R.styleable.CircularWaterWaveView_textHidden, DEFAULT_HIDE_TEXT);

        attributes.recycle();

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(mBorderWidth);
        borderPaint.setColor(mBorderColor);

        shapeRenderer.setShapeType(mShapeType);
        applyWaveColors();
    }

    private void applyWaveColors() {
        waveRenderer.setBehindWaveColor(mBehindWaveColor);
        waveRenderer.setMiddleWaveColor(mFrontWaveColor);
        waveRenderer.setFrontWaveColor(mFrontWaveColor);
    }

    private void rebuildShapePathsAndInvalidate() {
        if (screenSize.x <= 0 || screenSize.y <= 0) {
            shapePathsValid = false;
            return;
        }
        shapeRenderer.setShapeType(mShapeType);
        shapeRenderer.rebuildPaths(
                screenSize.x,
                screenSize.y,
                mShapePadding,
                mBorderWidth,
                mCornerRadiusPx,
                mSpikes
        );
        shapePathsValid = true;
        invalidate();
    }

    private void invalidateIfReady() {
        if (screenSize.x > 0 && screenSize.y > 0) {
            invalidate();
        }
    }

    public void setProgress(int progress) {
        if (progress <= mMax) {
            if (mListener != null) {
                mListener.onStuffing(progress, mMax);
            }
            mProgress = progress;
            invalidateIfReady();
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public void startAnimation() {
        isAnimation = true;
        if (getWidth() > 0 && getHeight() > 0) {
            waveAnimationController.setStepPeriodMs(animationStepMs);
            waveAnimationController.start(() -> shiftPhase += waveVector);
        }
    }

    public void stopAnimation() {
        isAnimation = false;
        waveAnimationController.stop();
    }

    public OnWaveStuffListener getListener() {
        return mListener;
    }

    public void setListener(OnWaveStuffListener mListener) {
        this.mListener = mListener;
    }

    public void setMax(int max) {
        if (mMax != max && max >= mProgress) {
            mMax = max;
            invalidateIfReady();
        }
    }

    public int getMax() {
        return mMax;
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
        borderPaint.setColor(mBorderColor);
        invalidateIfReady();
    }

    public void setFrontWaveColor(int color) {
        mFrontWaveColor = color;
        waveRenderer.setFrontWaveColor(mFrontWaveColor);
        waveRenderer.setMiddleWaveColor(mFrontWaveColor);
        invalidateIfReady();
    }

    public void setBehindWaveColor(int color) {
        mBehindWaveColor = color;
        waveRenderer.setBehindWaveColor(mBehindWaveColor);
        invalidateIfReady();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        invalidateIfReady();
    }

    public void setBorderWidth(float width) {
        mBorderWidth = width;
        borderPaint.setStrokeWidth(mBorderWidth);
        rebuildShapePathsAndInvalidate();
    }

    public void setShapePadding(float padding) {
        this.mShapePadding = padding;
        rebuildShapePathsAndInvalidate();
    }

    public void setAnimationSpeed(int speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("The speed must be greater than 0.");
        }
        this.animationStepMs = speed;
        waveAnimationController.setStepPeriodMs(animationStepMs);
        if (isAnimation) {
            startAnimation();
        }
    }

    public void setWaveVector(float offset) {
        if (offset < 0 || offset > 100) {
            throw new IllegalArgumentException("The vector of wave must be between 0 and 100.");
        }
        this.waveVector = (offset - 50f) / 50f;
        invalidateIfReady();
    }

    public void setHideText(boolean hidden) {
        this.isHideText = hidden;
        invalidateIfReady();
    }

    public void setStarSpikes(int count) {
        if (count < 3) {
            throw new IllegalArgumentException("The number of spikes must be greater than 3.");
        }
        this.mSpikes = count;
        if (Math.min(screenSize.x, screenSize.y) != 0) {
            rebuildShapePathsAndInvalidate();
        }
    }

    public void setWaveOffset(int offset) {
        this.waveOffset = offset;
        invalidateIfReady();
    }

    public void setWaveStrong(int strong) {
        this.mStrong = strong;
        invalidateIfReady();
    }

    public void setShape(@NonNull ShapeType shapeType) {
        mShapeType = shapeType != null ? shapeType : ShapeType.CIRCLE;
        rebuildShapePathsAndInvalidate();
    }

    public void setShape(@NonNull Shape shape) {
        setShape(shape.toShapeType());
    }

    @NonNull
    public ShapeType getShapeType() {
        return mShapeType;
    }

    @NonNull
    public Shape getLegacyShape() {
        return Shape.fromShapeType(mShapeType);
    }

    public void setWaveType(@NonNull WaveType waveType) {
        mWaveType = waveType != null ? waveType : WaveType.SINE;
        waveRenderer.setWaveType(mWaveType);
        invalidateIfReady();
    }

    @NonNull
    public WaveType getWaveType() {
        return mWaveType;
    }

    public void setWaveAmplitude(float multiplier) {
        mWaveAmplitude = Math.max(0f, multiplier);
        invalidateIfReady();
    }

    public float getWaveAmplitude() {
        return mWaveAmplitude;
    }

    public void setWaveSpeed(float phaseDeltaPerTick) {
        waveVector = phaseDeltaPerTick;
        invalidateIfReady();
    }

    public float getWaveSpeed() {
        return waveVector;
    }

    public void setCornerRadius(float cornerRadiusPx) {
        mCornerRadiusPx = Math.max(0f, cornerRadiusPx);
        rebuildShapePathsAndInvalidate();
    }

    public float getCornerRadius() {
        return mCornerRadiusPx;
    }

    public void setThirdWaveLayerEnabled(boolean enabled) {
        thirdWaveLayerEnabled = enabled;
        waveRenderer.setThirdLayerEnabled(enabled);
        invalidateIfReady();
    }

    public boolean isThirdWaveLayerEnabled() {
        return thirdWaveLayerEnabled;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenSize.set(w, h);
        rebuildShapePathsAndInvalidate();
        if (isAnimation) {
            startAnimation();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        waveRenderer.setWaveType(mWaveType);
        waveRenderer.setThirdLayerEnabled(thirdWaveLayerEnabled);
        applyWaveColors();
    }

    @Override
    protected void onDetachedFromWindow() {
        waveAnimationController.stop();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!shapePathsValid || screenSize.x <= 0 || screenSize.y <= 0) {
            return;
        }
        waveRenderer.setWaveType(mWaveType);
        final float pulse = 1f + AMPLITUDE_PULSE_STRENGTH * (float) Math.sin(shiftPhase * AMPLITUDE_PULSE_PHASE_SCALE);

        shapeRenderer.clipContentAndDraw(canvas, new Runnable() {
            @Override
            public void run() {
                waveRenderer.drawWaves(
                        canvas,
                        screenSize.x,
                        screenSize.y,
                        mProgress,
                        mMax,
                        mStrong,
                        waveOffset,
                        shiftPhase,
                        mWaveAmplitude,
                        pulse
                );
            }
        });

        if (mBorderWidth > 0f) {
            shapeRenderer.drawBorderPath(canvas, borderPaint);
        }

        if (!isHideText) {
            float percent = (mProgress * 100) / (float) mMax;
            String text = String.format(Locale.getDefault(), "%.1f", percent) + "%";
            TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(mTextColor);
            if (mShapeType == ShapeType.STAR) {
                textPaint.setTextSize((Math.min(screenSize.x, screenSize.y) / 2f) / 3f);
            } else {
                textPaint.setTextSize((Math.min(screenSize.x, screenSize.y) / 2f) / 2f);
            }
            float textHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(text, (screenSize.x - textPaint.measureText(text)) / 2f, (screenSize.y - textHeight) / 2f, textPaint);
        }
    }
}
