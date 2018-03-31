package com.toracode.myreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.toracode.myreminder.service.SchedulingService;

public class AlarmUtils {

    public static void create(Context context, Task task) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = SchedulingService.newIntent(context, task.getId(), task.getTitle());
        int requestCode = (int) (task.getId().getLeastSignificantBits());
        PendingIntent pendingIntent = PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getDate().getTime(), pendingIntent);
    }
}
