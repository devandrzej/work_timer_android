package com.example.andrzej.worktimer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    private static final int hours_in_milliseconds = /*3600**/1000;
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String FORMAT = "%02d:%02d:%02d";

    CountDownTimer workTimer;
    TextView text;
    TextView wifi_text;
    Button timer_button;
    Button stop_button;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi_text = (TextView)findViewById(R.id.text_nerwork_id);

        /*receiver = new WifiMonitor(wifi_text);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(receiver, filter);*/

        text = (TextView)findViewById(R.id.text_message);

        timer_button = (Button)findViewById(R.id.start_button);
        stop_button = (Button)findViewById(R.id.stop_button);

        stop_button.setEnabled(false);

        workTimer = new CountDownTimer(8*hours_in_milliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                text.setText("Time remaining: " +
                        String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                notify_done();
                stop_button();
            }
        };

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workTimer != null) {
                    workTimer.cancel();
                    stop_button();
                }
            }
        });

        timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workTimer.start();
                stop_button.setEnabled(true);
                timer_button.setEnabled(false);
            }
        });
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

    public void notify_done() {
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Context context = getApplicationContext();

        String notificationTitle = "Work time is over! Go home!";

        Notification notification = new Notification.Builder(context)
                .setContentTitle(notificationTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;

        notificationManager.notify(MY_NOTIFICATION_ID, notification);
    }

    private void stop_button() {
        timer_button.setEnabled(true);
        stop_button.setEnabled(false);

        Resources res = getResources();
        text.setText(res.getString(R.string.hello_msg));
    }
}
