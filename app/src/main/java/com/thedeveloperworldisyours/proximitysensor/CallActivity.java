package com.thedeveloperworldisyours.proximitysensor;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CallActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.call_activity_relative_layout);

        overridePendingTransition(R.anim.right_go_in, R.anim.right_go_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -4 && event.values[0] <= 4) {
                    // near
                    customSnackBar(getString(R.string.proximity_sensor_activity_near), R.color.colorGreen);
                } else {
                    // far
                    customSnackBar(getString(R.string.proximity_sensor_activity_far), R.color.colorAccent);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void turnOffScreen() {

        mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "tag");
        mWakeLock.acquire();

    }

    public void turnOnScreen() {

        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
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
