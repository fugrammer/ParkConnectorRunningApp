package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
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

public class RoutePlanner extends AppCompatActivity implements RouteAPI.RequestListener, MapViewFragment.PolylineClickListener {

    private GoogleMap mMap;
    private String distance="20000";
    private String destination ="";
    private int routeSelected = 0;
    boolean firstTime = true;
    LocationManager locationManager;
    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planner);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        myLocation = new Location("");
        myLocation.setLatitude(1.402955204229532d);
        myLocation.setLongitude(103.9242875429241d);
        if (checkPermission())
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        Button confirmButton = (Button) findViewById(R.id.activateButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to next page
            }
        });
    }

    private void getRoutes() {
        new RouteAPI(distance, myLocation, destination, this).execute();
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

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            myLocation = location;
            if (firstTime){
                firstTime = false;
                getRoutes();
            }
            Log.e("LOCATION: ",myLocation.toString());
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };
    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    @Override
    public void onClick(int id) {
        // To do
    }
}
