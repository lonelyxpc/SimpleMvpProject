package com.lonelyxpc.mvp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lonelyxpc.mvp.utils.ActivityKeepManagerUtil;

public class ActivityKeepReceiver extends BroadcastReceiver {

    private static final String TAG = "ActivityKeepReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "receive:" + action);
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            //灭屏 开启1px activity
            ActivityKeepManagerUtil.getInstance().startKeep(context);
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
            //亮屏 关闭
            ActivityKeepManagerUtil.getInstance().finishKeep();
        }
    }
}
