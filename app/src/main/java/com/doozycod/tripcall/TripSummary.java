package com.doozycod.tripcall;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TripSummary extends AppCompatActivity {
    TextView trip_distance, trip_Time, rp_price;
    String TRIP_DISTANCE, TRIP_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);
        trip_Time = findViewById(R.id.trip_time_tv);
        trip_distance = findViewById(R.id.trip_distance_tv);
        rp_price = findViewById(R.id.rp_price);

        getSupportActionBar().hide();
        hideStatusBar();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        MediaPlayer ring = MediaPlayer.create(TripSummary.this, R.raw.thankyoueffect);
        ring.start();

        Bundle bundle = getIntent().getExtras();

        TRIP_TIME = bundle.getString("diff");
        TRIP_DISTANCE = bundle.getString("distance");
        trip_Time.setText(TRIP_TIME);
        trip_distance.setText(TRIP_DISTANCE + " KM");
        float price = Float.parseFloat(TRIP_DISTANCE) * 1000;

        DecimalFormat df = new DecimalFormat("#.0000");
        String print = df.format(price);
        rp_price.setText(print + " Rp");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(TripSummary.this, ScanQRcode.class));
                finish();
            }
        }, 5000);

    }

    @Override
    protected void onResume() {
        hideStatusBar();
        super.onResume();
    }

    void hideStatusBar() {
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // hide status bar and nav bar after a short delay, or if the user interacts with the middle of the screen
        );

    }
}
