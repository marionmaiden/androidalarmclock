package com.mario.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mario on 27/10/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver", "Show");

        boolean isServiceStart = intent.getExtras().getBoolean("extra");

        Log.e("What's the key? ", isServiceStart ? "true" : "false");

        Intent ringtoneIntent = new Intent(context, RingtonePlayingService.class);

        ringtoneIntent.putExtra("extra", isServiceStart);


        context.startService(ringtoneIntent);
    }
}
