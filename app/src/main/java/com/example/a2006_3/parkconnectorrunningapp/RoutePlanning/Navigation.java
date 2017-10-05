package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Navigation extends AppCompatActivity implements MapViewFragment.PolylineClickListener {

    private ArrayList<Coordinate> coordinates;
    TextView timeTextView;
    int minElapsed = 0, secElapsed=0;
    private Handler handler = new Handler();
    MapViewFragment mapViewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent intent = getIntent();
        coordinates = intent.getParcelableArrayListExtra (RoutePlanner.COORDINATES);
        Log.e("COORDINATES",coordinates.toString());
        timeTextView = (TextView) findViewById(R.id.timeTextview);
        //Initialise map
        mapViewFragment = (MapViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment2);
        handler.postDelayed(runnable,1000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            secElapsed ++;
            if (secElapsed==60){
                secElapsed = 0;
                minElapsed += 0;
            }
            timeTextView.setText(String.format("%02d:%02d",minElapsed,secElapsed));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onClick(int id) {
        if (id==-1) mapViewFragment.displayRoute(coordinates, 0);
    }
}
