package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.Commons.DrawerItemCustomAdapter;
import com.example.a2006_3.parkconnectorrunningapp.R;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;

import java.util.ArrayList;

public class RoutePlanner extends AppCompatActivity implements ListView.OnItemClickListener, RouteAPI.RequestListener, MapViewFragment.PolylineClickListener {

    public static final String COORDINATES = "RoutePlanner.COORDINATES";

    private GoogleMap mMap;
    private String distance="20000";
    private String destination ="";
    private int routeSelected = 0;
    private ArrayList<ArrayList<Coordinate>> routeList = new ArrayList<>();
    private ArrayList<Double> routeDistance = new ArrayList<>();
    boolean firstTime = true, running = false;
    LocationManager locationManager;
    Location myLocation;
    MapViewFragment mapViewFragment;
    ProgressBar progressBar;
    Animation inAnimation,outAnimation;
    TextView runningTextView, cyclingTextView, distanceTextView, cyclingTextTextView,
            runningTextTextView, distanceTextTextView, expectedCalorieTextView;
    Button activateButton;

    DrawerLayout dLayout;

    TextView timeTextView;
    int minElapsed = 0, secElapsed=0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planner);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // Get values from previous activity
        Intent intent = getIntent();
        distance = String.valueOf(Math.round(intent.getFloatExtra("DISTANCE",2000f)));
        destination = intent.getStringExtra("DESTINATION");
        Log.e("Data received: ", distance + " " + destination);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        runningTextView = (TextView) findViewById(R.id.runningTextView);
        cyclingTextView = (TextView) findViewById(R.id.cyclingTextView);
        distanceTextView = (TextView) findViewById(R.id.distanceTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        runningTextTextView = (TextView) findViewById(R.id.runningTextTextView);
        cyclingTextTextView = (TextView) findViewById(R.id.cyclingTextTextView);
        distanceTextTextView = (TextView) findViewById(R.id.distanceTextTextView);
        expectedCalorieTextView = (TextView) findViewById(R.id.expectedCalorieTextView);
        activateButton = (Button) findViewById(R.id.activateButton);
        mapViewFragment = (MapViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        myLocation = new Location("");
        myLocation.setLatitude(1.402955204229532d);
        myLocation.setLongitude(103.9242875429241d);
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else{
            Log.e("Error", "No permission for location");
        }
        setNavigationDrawer();
    }

    public void startNavigation(View view){
        if (running==false) {
            running = true;
            dLayout.closeDrawers();
            dLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mapViewFragment.clearRoutesBut(routeSelected);
            runningTextView.setVisibility(View.INVISIBLE);
            cyclingTextView.setVisibility(View.INVISIBLE);
            distanceTextView.setVisibility(View.INVISIBLE);
            expectedCalorieTextView.setVisibility(View.INVISIBLE);
            distanceTextTextView.setVisibility(View.INVISIBLE);
            runningTextTextView.setVisibility(View.INVISIBLE);
            cyclingTextTextView.setVisibility(View.INVISIBLE);
            activateButton.setText("Finish");
            timeTextView.setVisibility(View.VISIBLE);
            mapViewFragment.zoomIn(myLocation);
            handler.postDelayed(runnable, 1000);
        }else{
            dLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            // Intent for next activity
            Intent intent = new Intent(RoutePlanner.this, WorkoutSummary.class);
            intent.putExtra("time",minElapsed);
            intent.putExtra("timeSec",secElapsed);
            intent.putExtra("distance",distanceTextView.getText());
            intent.putExtra("running",runningTextView.getText());
            intent.putExtra("cycling",cyclingTextView.getText());
            startActivity(intent);
        }
    }

    private void getRoutes() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBar = (ProgressBar) findViewById(R.id.routePlannerProgressBar);
        progressBar.startAnimation(inAnimation);
        new RouteAPI(distance, myLocation, destination, this).execute();
    }

    @Override
    public void onFinished(String routesJSON) {
        Log.e("Input",routesJSON);
        try {
            JSONArray reader = new JSONArray(routesJSON);
            mapViewFragment.clearRoutes();
            int id = 0;
            for (int routeNum = 0; routeNum < reader.length(); routeNum++) {
                JSONArray routeWaypoints = reader.getJSONArray(routeNum);
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                for (int waypoint = 0; waypoint < routeWaypoints.length()-1; waypoint++) {
                    JSONArray coordinatesString = routeWaypoints.getJSONArray(waypoint);
                    float lat = Float.valueOf(coordinatesString.getString(0));
                    float lng = Float.valueOf(coordinatesString.getString(1));
                    coordinates.add(new Coordinate(lat, lng));
                }
                routeList.add(coordinates);
                Double distance = routeWaypoints.getDouble(routeWaypoints.length()-1);
                routeDistance.add(distance);
                mapViewFragment.displayRoute(coordinates, id);
                id += 1;
            }
            updateTextViews(0);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(400);
            progressBar.setAnimation(outAnimation);
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } catch (Exception e) {
            Log.e("Error", "Invalid JSON Array");
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(400);
            progressBar.setAnimation(outAnimation);
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            AlertDialog.Builder wrongCredentials  = new AlertDialog.Builder(this);
            wrongCredentials.setMessage("There is no route found according to your preference.");
            wrongCredentials.setTitle("Oops!");
            wrongCredentials.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(RoutePlanner.this,RoutePlanning.class);
                    startActivity(intent);
                }
            });
            wrongCredentials.create().show();
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
            if (running){
                mapViewFragment.zoomIn(location);
            }
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

    private void updateTextViews(int id){
        distanceTextView.setText(String.format("%.1f",routeDistance.get(id)/1000));
        cyclingTextView.setText(String.format("%.1f",routeDistance.get(id)/1000*60.625));
        runningTextView.setText(String.format("%.1f",routeDistance.get(id)/1000*30));
    }
    @Override
    public void onClick(int id) {
        if (id==-1) return;
        updateTextViews(id);
        this.routeSelected = id;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            secElapsed ++;
            if (secElapsed==60){
                secElapsed = 0;
                minElapsed += 1;
            }
            timeTextView.setText(String.format("%02d:%02d",minElapsed,secElapsed));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("CLICKED",String.valueOf(position));
    }

    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId(); // get selected menu item's id
                String title = menuItem.getTitle().toString();
                Log.e("ItemID",String.valueOf(itemId));
                if (title.compareTo("Route Planning")==0){
                    Intent intent = new Intent(RoutePlanner.this,RoutePlanning.class);
                    startActivity(intent);
                }else{
                    // Setting intent
                    Intent intent = new Intent(RoutePlanner.this,Profile.class);
                    startActivity(intent);
                }
                dLayout.closeDrawers();
                return true;
            }
        });
    }
}
