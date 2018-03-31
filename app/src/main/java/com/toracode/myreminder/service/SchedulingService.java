package com.toracode.myreminder.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.toracode.myreminder.R;
import com.toracode.myreminder.TaskPagerActivity;

import java.util.UUID;

public class SchedulingService extends IntentService {

    private static final int TIME_VIBRATE = 1000;
    private static final String EXTRA_TASK_ID = "com.toracode.android.myreminder.task_id";
    private static final String EXTRA_TASK_TITLE = "com.toracode.android.myreminder.task_title";


    public SchedulingService() {
        super(SchedulingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        UUID taskId = (UUID) intent.getSerializableExtra(EXTRA_TASK_ID);
        String content = intent.getStringExtra(EXTRA_TASK_TITLE);

        Intent notificationIntent = TaskPagerActivity.newIntent(this, taskId);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestCode = (int) (taskId.getLeastSignificantBits());
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(content)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setPriority(1)
                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                TIME_VIBRATE})
                        .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public static Intent newIntent(Context packageContext, UUID id, String content) {
        Intent intent = new Intent(packageContext, SchedulingService.class);
        intent.putExtra(EXTRA_TASK_ID, id);
        intent.putExtra(EXTRA_TASK_TITLE, content);
        return intent;
    }
}
