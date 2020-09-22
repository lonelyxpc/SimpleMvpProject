package com.lonelyxpc.mvp.utils;

import android.app.Activity;

public class PhoneUtil {

    /*
    * 获取状态栏高度
    * activity 当前activity上下文
    * */
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourseId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourseId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourseId);
        }
        return result;
    }
}
