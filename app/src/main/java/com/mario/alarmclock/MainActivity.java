package com.mario.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarm_manager;
    TimePicker alarm_timpicker;
    TextView update_text;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timpicker = (TimePicker) findViewById(R.id.timePicker);
        update_text = (TextView) findViewById(R.id.alarmStatus);

        final Calendar calendar = Calendar.getInstance();
        final Intent alarmReceiverIntent = new Intent(this.context, AlarmReceiver.class);

        Button start_alarm = (Button) findViewById(R.id.startAlarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timpicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timpicker.getMinute());

                int hour = alarm_timpicker.getHour();
                int minute = alarm_timpicker.getMinute();

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (minute < 10) {
                    minute_string = "0" + minute_string;
                }

                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);


                alarmReceiverIntent.putExtra("extra", true);

                // Creating a pending intent
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Button finish_alarm = (Button) findViewById(R.id.stopAlarm);
        finish_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm off!");

                alarm_manager.cancel(pendingIntent);

                alarmReceiverIntent.putExtra("extra", false);
                sendBroadcast(alarmReceiverIntent);
            }
        });
    }

    private void set_alarm_text(String label) {
        update_text.setText(label);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
