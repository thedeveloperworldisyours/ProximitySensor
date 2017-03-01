package com.thedeveloperworldisyours.proximitysensor;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity {

    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private RelativeLayout mRelativeLayout;
    private ImageButton mHungUp;
    private ImageButton mHookOff;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.call_activity_relative_layout);
        mHookOff = (ImageButton) findViewById(R.id.call_activity_hook_off);
        mHungUp = (ImageButton) findViewById(R.id.call_activity_hung_up);

        overridePendingTransition(R.anim.right_go_in, R.anim.right_go_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    public void activateSensor(View v) {
        customSnackBar(getString(R.string.call_activity_proximity_on), R.color.colorGreen);
        mHookOff.setVisibility(View.GONE);
        mHungUp.setVisibility(View.VISIBLE);
        if (mWakeLock == null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "incall");
        }
        if (!mWakeLock.isHeld()) {
            Log.d(TAG, "New call active : acquiring incall (CPU only) wake lock");
            mWakeLock.acquire();
        } else {
            Log.d(TAG, "New call active while incall (CPU only) wake lock already active");
        }
    }

    public void deactivateSensor(View v) {
        mHookOff.setVisibility(View.VISIBLE);
        mHungUp.setVisibility(View.GONE);
        customSnackBar(getString(R.string.call_activity_proximity_off), R.color.colorAccent);
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            Log.d(TAG, "Last call ended: releasing incall (CPU only) wake lock");
        } else {
            Log.d(TAG, "Last call ended: no incall (CPU only) wake lock were held");
        }
    }


    @Override
    public void onBackPressed() {
        finishMyActivity();
    }

    public void finishMyActivity() {
        finish();
        overridePendingTransition(R.anim.right_back_in, R.anim.right_back_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishMyActivity();
                break;
        }
        return true;
    }

    void customSnackBar(String text, int color) {
        Snackbar mSnackbar = Snackbar.make(mRelativeLayout, text, Snackbar.LENGTH_LONG);

        // get snackbar view
        View mView = mSnackbar.getView();
        mView.setBackgroundResource(color);

        // get textview inside snackbar view
        TextView textview = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);

        textview.setTypeface(Typeface.DEFAULT_BOLD);

        // set text to center
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        } else {

            textview.setGravity(Gravity.CENTER_HORIZONTAL);

        }

        // show the snackbar
        mSnackbar.show();
    }
}
