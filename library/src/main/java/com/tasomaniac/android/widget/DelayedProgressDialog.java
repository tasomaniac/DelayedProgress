package com.tasomaniac.android.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * DelayedProgressDialog implements a ProgressDialog that waits a minimum time to be
 * dismissed before showing. Once visible, the progress bar will be visible for
 * a minimum amount of time to avoid "flashes" in the UI when an event could take
 * a largely variable time to complete (from none, to a user perceivable amount)
 */
public class DelayedProgressDialog extends ProgressDialog {

    private static final int DEFAULT_MIN_DELAY = 500; // ms

    private long mStartTime = -1;

    private boolean mPostedHide = false;

    private boolean mPostedShow = false;

    private boolean mDismissed = false;

    private int minShowTime = 500; // ms

    private int minDelay = 500; // ms

    private Handler mHandler;


    private final Runnable mDelayedHide = new Runnable() {

        @Override
        public void run() {
            mPostedHide = false;
            mStartTime = -1;
            DelayedProgressDialog.super.dismiss();
        }
    };

    private final Runnable mDelayedShow = new Runnable() {

        @Override
        public void run() {
            mPostedShow = false;
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                DelayedProgressDialog.super.show();
            }
        }
    };

    public DelayedProgressDialog(Context context) {
        super(context);
        mHandler = new Handler();
    }

    public DelayedProgressDialog(Context context, int theme) {
        super(context, theme);
        mHandler = new Handler();
    }

    public DelayedProgressDialog title(CharSequence title) {
        super.setTitle(title);
        return this;
    }

    public DelayedProgressDialog message(CharSequence message) {
        super.setMessage(message);
        return this;
    }

    public DelayedProgressDialog minShowTime(int minShowTime) {
        this.minShowTime = minShowTime;
        return this;
    }

    public DelayedProgressDialog minDelay(int minDelay) {
        this.minDelay = minDelay;
        return this;
    }

    public DelayedProgressDialog cancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    public DelayedProgressDialog indeterminate(boolean indeterminate) {
        super.setIndeterminate(indeterminate);
        return this;
    }

    public DelayedProgressDialog title(String title) {
        super.setTitle(title);
        return this;
    }

    public DelayedProgressDialog message(String message) {
        super.setMessage(message);
        return this;
    }

    public static DelayedProgressDialog makeDelayed(Context context, CharSequence title,
                                             CharSequence message) {
        return makeDelayed(context, title, message, false);
    }

    public static DelayedProgressDialog makeDelayed(Context context, CharSequence title,
                                             CharSequence message, boolean indeterminate) {
        return makeDelayed(context, title, message, indeterminate, false, null);
    }

    public static DelayedProgressDialog makeDelayed(Context context, CharSequence title,
                                             CharSequence message, boolean indeterminate, boolean cancelable) {
        return makeDelayed(context, title, message, indeterminate, cancelable, null);
    }

    public static DelayedProgressDialog makeDelayed(Context context, CharSequence title,
                                                           CharSequence message,
                                                           boolean indeterminate,
                                                           boolean cancelable, OnCancelListener cancelListener) {
        DelayedProgressDialog dialog = new DelayedProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.minDelay(DEFAULT_MIN_DELAY);
        return dialog;
    }

    public static DelayedProgressDialog showDelayed(Context context, CharSequence title,
                                      CharSequence message) {
        return showDelayed(context, title, message, false);
    }

    public static DelayedProgressDialog showDelayed(Context context, CharSequence title,
                                             CharSequence message, boolean indeterminate) {
        return showDelayed(context, title, message, indeterminate, false, null);
    }

    public static DelayedProgressDialog showDelayed(Context context, CharSequence title,
                                      CharSequence message, boolean indeterminate, boolean cancelable) {
        return showDelayed(context, title, message, indeterminate, cancelable, null);
    }

    public static DelayedProgressDialog showDelayed(Context context, CharSequence title,
                                                           CharSequence message,
                                                           boolean indeterminate,
                                                           boolean cancelable, OnCancelListener cancelListener) {
        DelayedProgressDialog dialog = new DelayedProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.minDelay(DEFAULT_MIN_DELAY);
        dialog.show();
        return dialog;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        if(mHandler != null) {
            mHandler.removeCallbacks(mDelayedHide);
            mHandler.removeCallbacks(mDelayedShow);
        }
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    @Override
    public void dismiss() {
        mDismissed = true;
        mHandler.removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= minShowTime || mStartTime == -1) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            super.dismiss();
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                mHandler.postDelayed(mDelayedHide, minShowTime - diff);
                mPostedHide = true;
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    @Override
    public void show() {
        // Reset the start time.
        mStartTime = -1;
        mDismissed = false;
        mHandler.removeCallbacks(mDelayedHide);
        if (!mPostedShow) {
            mHandler.postDelayed(mDelayedShow, minDelay);
            mPostedShow = true;
        }
    }

}
