package com.thedeveloperworldisyours.proximitysensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void goToProximitySensor(View view) {
        Intent intent = new Intent(this, ProximitySensorActivity.class);
        startActivity(intent);
    }

    public void goToCall(View view) {
        Intent intent = new Intent(this, CallActivity.class);
        startActivity(intent);
    }
}
