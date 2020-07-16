package com.thedeveloperworldisyours.proximitysensor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void goToProximitySensor(View view) {
        startActivity(new Intent(this, ProximitySensorActivity.class));
    }

    public void goToCall(View view) {
        startActivity(new Intent(this, CallActivity.class));
    }
}
