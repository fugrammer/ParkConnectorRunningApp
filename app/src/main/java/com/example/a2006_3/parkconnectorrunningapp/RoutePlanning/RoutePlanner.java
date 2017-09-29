package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.R;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;

import java.util.ArrayList;

public class RoutePlanner extends AppCompatActivity implements RouteAPI.RequestListener, SeekBar.OnSeekBarChangeListener {

    private GoogleMap mMap;
    SeekBar distanceBar;
    TextView distanceTextView;
    private String distance="20000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planner);
        distanceBar = (SeekBar) findViewById(R.id.distanceBar);
        distanceTextView = (TextView) findViewById(R.id.distanceTextView);
        distanceBar.setOnSeekBarChangeListener(this);
        Button activateButton = (Button) findViewById(R.id.activateButton);
        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRoutes();
            }
        });
    }

    private void getRoutes() {
        new RouteAPI(distance, this).execute();
    }

    @Override
    public void onFinished(String routesJSON) {
        Log.e("Input",routesJSON);
        try {
            JSONArray reader = new JSONArray(routesJSON);
            MapViewFragment mapViewFragment = (MapViewFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mapFragment);
            mapViewFragment.clearRoutes();
            int id = 0;
            for (int routeNum = 0; routeNum < reader.length(); routeNum++) {
                JSONArray routeWaypoints = reader.getJSONArray(routeNum);
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                for (int waypoint = 0; waypoint < routeWaypoints.length(); waypoint++) {
                    JSONArray coordinatesString = routeWaypoints.getJSONArray(waypoint);
                    float lat = Float.valueOf(coordinatesString.getString(0));
                    float lng = Float.valueOf(coordinatesString.getString(1));
                    coordinates.add(new Coordinate(lat, lng));
                }
                mapViewFragment.displayRoute(coordinates, id);
                id += 1;
            }
        } catch (Exception e) {
            Log.e("Error", "Invalid JSON Array");
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

        distance = String.valueOf(progress);
        distanceTextView.setText(distance +"m");

        //Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
