package com.mario.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mario on 27/10/2017.
 */

public class RingtonePlayingService extends Service{

    MediaPlayer media_song;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("In the service", "Show");

        boolean isServiceStarting = intent.getExtras().getBoolean("extra");


        if (!isRunning && isServiceStarting) {
            media_song = MediaPlayer.create(this, R.raw.music);
            media_song.start();

            isRunning = true;

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            Intent intentMainActivity = new Intent(this.getApplicationContext(),MainActivity.class);

            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this,0,intentMainActivity,0);

            Notification notificationPopup = new Notification.Builder(this)
                    .setContentText("Alarm Is Going off")
                    .setContentTitle("Alarm")
                    .setSmallIcon(R.drawable.ic_stat_add_alarm)
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notificationPopup);
        }
        else if (isRunning && !isServiceStarting) {
            media_song.stop();
            media_song.reset();

            isRunning = false;
        }
        else
        {
            Log.e("No valid status", "Show");
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {

    }
}
