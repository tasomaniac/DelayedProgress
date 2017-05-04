package com.tasomaniac.android.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;

/**
 * ContentLoadingProgressBar implements a ProgressBar that waits a minimum time to be
 * dismissed before showing. Once visible, the progress bar will be visible for
 * a minimum amount of time to avoid "flashes" in the UI when an event could take
 * a largely variable time to complete (from none, to a user perceivable amount)
 */
public class DelayedProgressBar extends ProgressBar {

    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms

    private long mStartTime = -1;

    private boolean mPostedHide = false;

    private boolean mPostedShow = false;

    private boolean mDismissed = false;

    private boolean animate = false;

    private Runnable showEndAction;

    private Runnable hideEndAction;

    private ViewPropertyAnimatorCompat animator;

    private final Runnable mEmptyEndAction = new Runnable() {
        @Override
        public void run() {
        }
    };

    private final Runnable mDelayedHide = new Runnable() {

        @Override
        public void run() {
            mPostedHide = false;
            doHide(animate);
            mStartTime = -1;
        }
    };

    private final Runnable mDelayedShow = new Runnable() {

        @Override
        public void run() {
            mPostedShow = false;
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                if (animate) {
                    setAlpha(0f);
                    setVisibility(View.VISIBLE);
                    if (animator != null) {
                        animator.cancel();
                    }
                    animator = ViewCompat.animate(DelayedProgressBar.this)
                            .alpha(1.0f)
                            .setInterpolator(new AccelerateInterpolator())
                            .withEndAction(showEndAction);
                } else {
                    setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public DelayedProgressBar(Context context) {
        this(context, null);
    }

    public DelayedProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        mPostedHide = false;
        removeCallbacks(mDelayedShow);
        mPostedShow = false;
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    public void hide() {
        hide(false);
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     *
     * @param animate whether to animate the progress bar.
     */
    public void hide(boolean animate) {
        hide(animate, mEmptyEndAction);
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     *
     * @param animate whether to animate the progress bar.
     * @param endAction Runnable to run after the animation.
     */
    public void hide(boolean animate, @NonNull Runnable endAction) {
        this.animate = animate;
        this.hideEndAction = endAction;

        mDismissed = true;
        removeCallbacks(mDelayedShow);
        mPostedShow = false;
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
            doHide(animate);
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    private void doHide(boolean animate) {
        // The progress spinner has been shown long enough
        // OR was not shown yet. If it wasn't shown yet,
        // it will just never be shown.
        if (animate && mStartTime != -1) {
            if (animator != null) {
                animator.cancel();
            }
            animator = ViewCompat.animate(this)
                    .alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            setVisibility(View.GONE);
                            hideEndAction.run();
                        }
                    });
        } else {
            setVisibility(View.GONE);
            hideEndAction.run();
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    public void show() {
        show(false);
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     *
     * @param animate whether to animate the progress bar.
     */
    public void show(boolean animate) {
        show(animate, mEmptyEndAction);
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     *
     * @param animate whether to animate the progress bar.
     * @param endAction Runnable to run after the animation.
     */
    public void show(boolean animate, @NonNull Runnable endAction) {
        this.animate = animate;
        this.showEndAction = endAction;
        // Reset the start time.
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);
        mPostedHide = false;
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY);
            mPostedShow = true;
        }
    }
}
