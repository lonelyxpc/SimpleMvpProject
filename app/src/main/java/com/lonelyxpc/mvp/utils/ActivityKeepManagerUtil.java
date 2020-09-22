package com.lonelyxpc.mvp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.lonelyxpc.mvp.broadcast.ActivityKeepReceiver;
import com.lonelyxpc.mvp.module.KeepActivity.view.KeepActivity;

import java.lang.ref.WeakReference;

public class ActivityKeepManagerUtil {

    private static ActivityKeepManagerUtil minstence = null;
    private ActivityKeepReceiver activityKeepReceiver;
    private WeakReference<Activity> mKeepActivity;

    public boolean getIsback() {
        return isback;
    }

    private  boolean isback = false;

    public static ActivityKeepManagerUtil getInstance() {

        synchronized (ActivityKeepManagerUtil.class) {

            if (minstence == null) {

                minstence = new ActivityKeepManagerUtil();

            }

        }

        return minstence;
    }

    /**
     * 注册
     * @param context
     */
    public void registerKeepReceiver(Context context){
        isback = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        if(activityKeepReceiver==null){
            activityKeepReceiver = new ActivityKeepReceiver();
        }
        context.registerReceiver(activityKeepReceiver, filter);
    }


    /**
     * 反注册
     * @param context
     */
    public void unRegisterKeepReceiver(Context context){
        if (activityKeepReceiver !=null) {
            context.unregisterReceiver(activityKeepReceiver);
        }
    }

    /**
     * 启动1个像素的KeepActivity
     * @param context
     */
    public void  startKeep(Context context) {
        Intent intent = new Intent(context, KeepActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * finish1个像素的KeepActivity
     */
    public void finishKeep() {
        if (null != mKeepActivity) {
            Activity activity = mKeepActivity.get();
            if (null != activity) {
                activity.finish();
            }
            mKeepActivity = null;
        }
    }

}
