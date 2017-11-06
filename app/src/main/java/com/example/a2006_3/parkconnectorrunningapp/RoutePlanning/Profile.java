package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.example.a2006_3.parkconnectorrunningapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements ProfileAPI.RequestListener{


    DrawerLayout dLayout;
    ScrollView scrollView;
    private ArrayList userRoutes = new ArrayList<>();

    List<DataAdapter> DataAdapterClassList;
    ArrayList<String> SubjectNames;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        DataAdapterClassList =  new ArrayList<>();
        SubjectNames = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        setNavigationDrawer();
        //scrollView = (ScrollView) findViewById(R.id.cardScrollview);

        //Get data from backend to show user's history of routes
        // to-do: some way to pass username into ProfileAPI()
        new ProfileAPI("songwei",this).execute(); // for testing only

    }
    @Override
    public void onFinished(String routesJSON) { //read the data which is in an array of JSON objects
        Log.e("Input", routesJSON);
        try {
            JSONArray reader = new JSONArray(routesJSON);
            for (int i = 0; i < reader.length(); i++) {
                DataAdapter GetDataAdapter2 = new DataAdapter();

                try {
                    JSONObject object = reader.getJSONObject(i);
                    GetDataAdapter2.setTime(object.getInt("timetaken"));
                    GetDataAdapter2.setDist(object.getInt("distance"));
                    GetDataAdapter2.setCal(object.getInt("calorie"));
                    GetDataAdapter2.setDate(object.getString("date"));
                    //scrollView.addView(view);
                    //updateTextViews(distance, time, calories);
                } catch (Exception e){
                    Log.e("error", "corrupted data");
                }
                DataAdapterClassList.add(GetDataAdapter2);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        recyclerViewadapter = new RecyclerViewAdapter(DataAdapterClassList, this);
        recyclerView.setAdapter(recyclerViewadapter);

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
                    Intent intent = new Intent(Profile.this,RoutePlanning.class);
                    startActivity(intent);
                }else{
                    // Setting intent
                }
                dLayout.closeDrawers();
                return true;
            }
        });
    }


//
//            mapViewFragment.clearRoutes();
//            int id = 0;
//            for (int routeNum = 0; routeNum < reader.length(); routeNum++) {
//                JSONArray routeWaypoints = reader.getJSONArray(routeNum);
//                ArrayList<Coordinate> coordinates = new ArrayList<>();
//                for (int waypoint = 0; waypoint < routeWaypoints.length()-1; waypoint++) {
//                    JSONArray coordinatesString = routeWaypoints.getJSONArray(waypoint);
//                    float lat = Float.valueOf(coordinatesString.getString(0));
//                    float lng = Float.valueOf(coordinatesString.getString(1));
//                    coordinates.add(new Coordinate(lat, lng));
//                }
//                routeList.add(coordinates);
//                Double distance = routeWaypoints.getDouble(routeWaypoints.length()-1);
//                routeDistance.add(distance);
//                mapViewFragment.displayRoute(coordinates, id);
//                id += 1;
//            }
//            updateTextViews(0);
//            outAnimation = new AlphaAnimation(1f, 0f);
//            outAnimation.setDuration(400);
//            progressBar.setAnimation(outAnimation);
//            progressBar.setVisibility(View.GONE);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        } catch (Exception e) {
//            Log.e("Error", "Invalid JSON Array");
//            outAnimation = new AlphaAnimation(1f, 0f);
//            outAnimation.setDuration(400);
//            progressBar.setAnimation(outAnimation);
//            progressBar.setVisibility(View.GONE);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            AlertDialog.Builder wrongCredentials  = new AlertDialog.Builder(this);
//            wrongCredentials.setMessage("There is no route found according to your preference.");
//            wrongCredentials.setTitle("Oops!");
//            wrongCredentials.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(RoutePlanner.this,RoutePlanning.class);
//                    startActivity(intent);
//                }
//            });
//            wrongCredentials.create().show();
//        }
//
//
//
//    }



}


