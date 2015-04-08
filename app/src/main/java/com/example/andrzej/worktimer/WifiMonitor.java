package com.example.andrzej.worktimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

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
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !connectionInfo.getSSID().isEmpty()) {
                text.setText(connectionInfo.getSSID());
            }
        } else {
            text.setText("Disconnected");
        }
    }
}
