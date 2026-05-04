package com.orbitalsonic.waterwave.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Main-thread wave phase stepping using {@link ValueAnimator} (no {@code HandlerThread}).
 */
public final class WaveAnimationController {

    private static final long MIN_STEP_PERIOD_MS = 1L;

    private final View host;
    @Nullable
    private ValueAnimator animator;
    private long stepPeriodMs = 25L;
    private boolean running;
    @Nullable
    private Runnable onStep;

    public WaveAnimationController(View host) {
        this.host = host;
    }

    public void setStepPeriodMs(long periodMs) {
        this.stepPeriodMs = Math.max(MIN_STEP_PERIOD_MS, periodMs);
        if (running && onStep != null) {
            start(onStep);
        }
    }

    public long getStepPeriodMs() {
        return stepPeriodMs;
    }

    public void start(Runnable onStep) {
        this.onStep = onStep;
        running = true;
        cancelAnimator();
        ValueAnimator va = ValueAnimator.ofInt(0, 1);
        va.setDuration(stepPeriodMs);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tick();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                tick();
            }
        });
        va.start();
        animator = va;
    }

    private void tick() {
        if (onStep != null) {
            onStep.run();
        }
        host.postInvalidateOnAnimation();
    }

    public void stop() {
        running = false;
        onStep = null;
        cancelAnimator();
    }

    public boolean isRunning() {
        return running;
    }

    private void cancelAnimator() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
