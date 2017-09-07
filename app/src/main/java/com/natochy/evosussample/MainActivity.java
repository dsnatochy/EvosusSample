package com.natochy.evosussample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import co.poynt.os.model.Intents;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static class CloudMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getAction());
        }
    }

    private CloudMessageReceiver cloudMessageReceiver = new CloudMessageReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(cloudMessageReceiver, new IntentFilter(Intents.ACTION_CLOUD_MESSAGE_RECEIVED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(cloudMessageReceiver);
    }
}
