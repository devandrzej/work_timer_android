package com.example.andrzej.worktimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;

/**
 * Created by Andrzej on 2015-04-07.
 */
public class WifiMonitor extends BroadcastReceiver {

    private TextView text;

    public WifiMonitor(TextView text) {
        this.text = text;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn_mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn_mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            text.setText("Connected");
        }
    }
}
