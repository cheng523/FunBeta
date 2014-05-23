package com.a1w0n.funbeta;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Display;

import com.a1w0n.funbeta.Utils.StrictModeUtils;

/**
 * Apk文件运行时的入口类，全局的。
 */
public final class GlobalApplication extends Application {

	// 单例模式的instance.
	private static GlobalApplication mGlobalApplication = null;

    // 每个Activity运行的时候都会注册为这个变量，标识正在运行的Activity实例
	private Activity currentRunningActivity = null;

	//private DisplayMetrics displayMetrics = null;

    // 初始化UI线程的Handler.
	private Handler handler = new Handler();

    /**
     * 被创建的时候系统会调用这个回调
     */
	@Override
	public void onCreate() {
		super.onCreate();
		mGlobalApplication = this;
		//Crashlytics.start(this);

		if (BuildConfig.DEBUG) {
			// Remeber to set class instance limit inside this api. D
			StrictModeUtils.enableStrictMode();
		}
	}

    /**
     * 获取单例instance
     */
	public static GlobalApplication getInstance() {
		return mGlobalApplication;
	}

    /**
     * 获取UI线程的Handler
     */
	public Handler getUIHandler() {
		return handler;
	}

//	public DisplayMetrics getDisplayMetrics() {
//		if (displayMetrics != null) {
//			return displayMetrics;
//		} else {
//			Activity a = getActivity();
//			if (a != null) {
//				Display display = getActivity().getWindowManager().getDefaultDisplay();
//				DisplayMetrics metrics = new DisplayMetrics();
//				display.getMetrics(metrics);
//				this.displayMetrics = metrics;
//				return metrics;
//			} else {
//				// Default screen is 800x480
//				DisplayMetrics metrics = new DisplayMetrics();
//				metrics.widthPixels = 480;
//				metrics.heightPixels = 800;
//				return metrics;
//			}
//		}
//	}

    /**
     * 获取当前运行Activity的接口
     */
	public Activity getCurrentRunningActivity() {
		return currentRunningActivity;
	}

    /**
     * 给Activity注册的接口
     */
	public void setCurrentRunningActivity(Activity currentRunningActivity) {
		this.currentRunningActivity = currentRunningActivity;
	}

}
