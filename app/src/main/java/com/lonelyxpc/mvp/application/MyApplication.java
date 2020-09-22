package com.lonelyxpc.mvp.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.lonelyxpc.mvp.net.HttpManager;
import com.lonelyxpc.mvp.service.KeepService;
import com.lonelyxpc.mvp.utils.UncaughtExceptionHandlerUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private HttpManager mHttpManager = null;
    public static Context context;
    public static SharedPreferences sharedPreferences;
    private boolean isRunInBackground =false;
    public int appCount=0;
    private Intent keepService;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mHttpManager = new HttpManager();
        context = getApplicationContext();
        UncaughtExceptionHandlerUtil handler = new UncaughtExceptionHandlerUtil();
        Thread.setDefaultUncaughtExceptionHandler(handler);
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);

        keepService = new Intent(this, KeepService.class);
        appBackgroundCallBack();
    }

    public static <T> T apiService(Class<T> clz) {
        return getInstance().mHttpManager.getService(clz);
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    private void appBackgroundCallBack(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.e("app","onActivityCreated");

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.e("app","onActivityStarted");

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.e("app","onActivityResumed");

                appCount++;
                if (isRunInBackground) {
                    //应用从后台回到前台 需要做的操作
                    back2App(activity);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.e("app","onActivityStopped");
                appCount--;
                if (appCount == 0) {
                    //应用进入后台 需要做的操作
                    leaveApp(activity);
                }
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.e("app","onActivityStopped");

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }


    /**
     * 从后台回到前台需要执行的逻辑
     *
     * @param activity
     */
    private void back2App(Activity activity) {
        Log.e("app","back2App");
        isRunInBackground = false;
        activity.stopService(keepService);
    }

    /**
     * 离开应用 压入后台或者退出应用
     *
     * @param activity
     */
    private void leaveApp(Activity activity) {
        Log.e("app","leaveApp");
        isRunInBackground = true;

            activity.startService(keepService);

    }
}
