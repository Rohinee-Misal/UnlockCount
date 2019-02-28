package com.rohinee.ganesh.unlockcount;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
//import android.support.annotation.Nullable;

import android.util.Log;

public class HelloService extends Service {
   // @androidx.annotation.Nullable
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    static int count = 0;
   // @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int restoredText = prefs.getInt("count", 0);
        if (restoredText != 0) {
            count = restoredText; //0 is the default value.
            Log.i("count 1",""+count);
           Log.i("count 2",""+restoredText);
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // screen is turn off
            //("Screen locked");
        } else {
            //Handle resuming events if user is present/screen is unlocked
            count++;
            Log.i("count 3",""+count);
            Log.i("count 4",""+count);
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putInt("count", count);
            Log.i("count 5",""+count);
            //editor.apply();
            editor.commit();
            Log.i("count 6",""+count);
            //("Screen unlocked");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
