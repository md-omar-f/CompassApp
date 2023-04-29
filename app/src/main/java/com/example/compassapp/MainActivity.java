package com.example.compassapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView compassTv;
    private ImageView compassIv;

    private float startDegree = 0f;

    private SensorManager sm;
    private Sensor os; // for orientation sensor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compassTv = findViewById(R.id.compassTv);
        compassIv = findViewById(R.id.compassImage);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        os = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sm.registerListener(this, os, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);
        compassTv.setText("Heading: " + Float.toString(degree) + " degrees");
        RotateAnimation ra = new RotateAnimation(startDegree, -degree, Animation.RELATIVE_TO_SELF
                , 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
        ra.setDuration(200);

        compassIv.startAnimation(ra);
        startDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}