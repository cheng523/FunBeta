package com.a1w0n.funbeta.ui;

import android.support.v4.app.FragmentActivity;

import com.a1w0n.funbeta.GlobalApplication;

/**
 * 所有Activity的基类，实现自动在GlobalApplication里头注册和反注册。
 */
public class BaseActivity extends FragmentActivity {

    /**
     * onResume的时候自动在GlobalApplication注册
     */
    @Override
    protected void onResume() {
        super.onResume();
        GlobalApplication.getInstance().setCurrentRunningActivity(this);
    }

    /**
     * onPause的时候自动在GlobalApplication反注册
     */
    @Override
    protected void onPause() {
        super.onPause();
        // 只反注册自身。
        if (GlobalApplication.getInstance().getCurrentRunningActivity() == this) {
        	GlobalApplication.getInstance().setCurrentRunningActivity(null);
        }
    }
}
