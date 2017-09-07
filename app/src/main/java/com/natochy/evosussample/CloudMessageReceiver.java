package com.natochy.evosussample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import co.poynt.os.model.Intents;

public class CloudMessageReceiver extends BroadcastReceiver {
    private static final String TAG = CloudMessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(Intents.INTENT_EXTRA_CLOUD_MESSAGE_BODY);
        Log.d(TAG, "onReceive: "  + data);
        Intent i = new Intent(context, SecondScreenService.class);
        i.putExtra("REQUEST", data);
        context.startService(i);
    }
}
