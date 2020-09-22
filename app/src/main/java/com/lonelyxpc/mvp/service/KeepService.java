package com.lonelyxpc.mvp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.lonelyxpc.mvp.R;
import com.lonelyxpc.mvp.module.MainActivity.view.MainActivity;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class KeepService extends Service {

    private static final int keepId = 10;
    private NotificationManager manager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("keepservice", "keepservice",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("通知栏");
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            if (manager == null)
                return;
            manager.createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, "keepservice")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setCustomBigContentView(getRemoteView())
                    .setCustomContentView(getRemoteView())
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setOngoing(true)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .build();
            manager.notify(keepId, notification);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //如果 18 以上的设备 启动一个Service startForeground给相同的id
            //然后结束那个Service
            startForeground(keepId, new Notification());
            startService(new Intent(this, InnnerService.class));
        } else {
            startForeground(keepId, new Notification());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancel(keepId);
    }

    public class InnnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            startForeground(keepId, new Notification());
            stopSelf();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    private RemoteViews getRemoteView(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_view);
        return remoteViews;
    }

    private PendingIntent getDefaultIntent(int flags) {
        return PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), flags);
    }
}
