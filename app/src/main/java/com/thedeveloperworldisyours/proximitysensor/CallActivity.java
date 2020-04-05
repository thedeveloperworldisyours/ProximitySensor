package com.thedeveloperworldisyours.proximitysensor;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class CallActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private RelativeLayout mRelativeLayout;
    private ImageButton mHungUp;
    private ImageButton mHookOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);

        mRelativeLayout = findViewById(R.id.call_activity_relative_layout);
        mHookOff = findViewById(R.id.call_activity_hook_off);
        mHungUp = findViewById(R.id.call_activity_hung_up);

        overridePendingTransition(R.anim.right_go_in, R.anim.right_go_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    public void activateSensor() {
        customSnackBar(getString(R.string.call_activity_proximity_on), R.color.colorGreen);
        mHookOff.setVisibility(View.GONE);
        mHungUp.setVisibility(View.VISIBLE);
        mRelativeLayout.setBackgroundResource(R.drawable.thewordis);
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

    public void deactivateSensor() {
        mHookOff.setVisibility(View.VISIBLE);
        mHungUp.setVisibility(View.GONE);
        mRelativeLayout.setBackgroundResource(android.R.color.white);
        customSnackBar(getString(R.string.call_activity_proximity_off), R.color.colorAccent);
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            Log.d(TAG, "Last call ended: releasing incall (CPU only) wake lock");
        } else {
            Log.d(TAG, "Last call ended: no incall (CPU only) wake lock were held");
        }
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
        TextView textview = mView.findViewById(com.google.android.material.R.id.snackbar_text);

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
