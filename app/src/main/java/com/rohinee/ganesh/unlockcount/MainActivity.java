package com.rohinee.ganesh.unlockcount;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

//import static android.app.Service.START_STICKY;
//import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
   private LockScreenStateReceiver mLockScreenStateReceiver;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    static int count = 0;
    TextView textView;
    IntentFilter filter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // unregisterReceiver(mLockScreenStateReceiver);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textview);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int restoredText = prefs.getInt("count", 0);
        if (restoredText != 0) {
            count = restoredText; //0 is the default value.
              textView.setText(""+restoredText);
         }

       /* startService(new Intent(this, HelloService.class));*/

       mLockScreenStateReceiver = new LockScreenStateReceiver();
        filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(getPackageName() + "android.intent.action.SCREEN_OFF");
        filter.addAction(getPackageName() + "android.intent.action.SCREEN_ON");
        filter.addAction(Intent.ACTION_USER_PRESENT);
//        Intent intent = new Intent("com.rohinee.ganesh.unlockcount");
//        sendBroadcast(intent);
//      //  registerReceiver(mLockScreenStateReceiver, filter);

    }


    public class LockScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
           //     Toast.makeText(context, "Screen is off", Toast.LENGTH_LONG).show();
                // screen is turn off
                count++;
                 textView.setText(""+count);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("count", count);
                //editor.apply();
                editor.commit();
                //("Screen locked");
            } else {
                //Handle resuming events if user is present/screen is unlocked
         //       Toast.makeText(context, "screen is on", Toast.LENGTH_LONG).show();
                //("Screen unlocked");
            }
//            if(intent.getAction().equals("com.rohinee.ganesh.unlockcount"))
//                Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();
//
//            else {
//                Toast.makeText(context, "check screen", Toast.LENGTH_LONG).show();
//            }
        }
    }

    @Override
    public void onDestroy() {
       unregisterReceiver(mLockScreenStateReceiver);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
    //    unregisterReceiver(mLockScreenStateReceiver);
        super.onStop();
    }

    @Override
    protected void onPause() {
 //       unregisterReceiver(mLockScreenStateReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mLockScreenStateReceiver, filter);
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * Called after {@link #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by {@link #onStart} and then {@link #onResume}.
     *
     * <p>For activities that are using raw {@link //Cursor} objects (instead of
     * creating them through
     * {@link #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * {@link #onStop}.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
