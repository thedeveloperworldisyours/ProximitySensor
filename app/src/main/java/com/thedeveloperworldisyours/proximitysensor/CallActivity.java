package com.thedeveloperworldisyours.proximitysensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);

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
            if (event.values[0] == 0) {
                //near
                Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                turnOffScreen();

            } else {
                //far
                Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                turnOnScreen();
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
}
