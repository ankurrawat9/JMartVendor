package com.cinfy.jmvendor.base;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

public class ProgressBarDialog {

    private static ProgressBarDialog sInstance;

    private boolean inProgress = false;

    private ProgressBarDialog() {

    }

    public static ProgressBarDialog getInstance() {
        if (sInstance != null)
            return sInstance;
        sInstance = new ProgressBarDialog();
        return sInstance;
    }

    public void addProgressDialog(Activity activity, android.widget.ProgressBar mProgressBar) {
        if (activity != null && mProgressBar != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.VISIBLE);
            inProgress = true;
        }

    }

    public void addProgressDialog(Activity activity) {
        if (activity != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            inProgress = true;
        }
    }

    public void dismissProgressDialog(Activity activity) {
        if (activity != null) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            inProgress = false;
        }
    }

    public void dismissProgressDialog(Activity activity, android.widget.ProgressBar mProgressBar) {
        if (activity != null) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            inProgress = false;
        }
    }

    public boolean getProgress() {
        return inProgress;
    }

}

