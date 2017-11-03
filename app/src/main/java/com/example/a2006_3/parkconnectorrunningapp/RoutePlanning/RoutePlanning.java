package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.R;

public class RoutePlanning extends AppCompatActivity {
    String[] connectors = {"Kallang", "Bishan","JurongWest","Jurong","BukitBatok","PeltonCanal", "UluPandan", "WestCoastPark", "ChuaChuKang"};
    double x, y,distance;
    EditText editText;
    AutoCompleteTextView acTxtview;
    Button distSubmitButton,endpointSubmitButton;
    Button endpointButton,distanceButton;
    DrawerLayout dLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning);



        //autocomplete for endpoint
        acTxtview = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        acTxtview.setVisibility(View.INVISIBLE);

        //edit text for distance
        editText = (EditText) findViewById(R.id.editText);
        editText.setVisibility(View.VISIBLE);

        distSubmitButton = (Button) findViewById(R.id.DistanceSubmitButton);
        distSubmitButton.setVisibility(View.VISIBLE);

        endpointSubmitButton = (Button) findViewById(R.id.EndpointSubmitButton);
        endpointSubmitButton.setVisibility(View.INVISIBLE);

        //for the autocomplete
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,connectors);
        acTxtview.setThreshold(1);
        acTxtview.setAdapter(adapter);

        acTxtview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the end point selected by user
                String selection = (String)parent.getItemAtPosition(position);

                if (selection == "Kallang") {
                    x = 1.306609;
                    y = 103.869922;
                }
                else if (selection == "JurongWest") {
                    x = 1.353074;
                    y = 103.714731;
                }
                else if (selection == "Jurong") {
                    x = 1.342577;
                    y = 103.719879;
                }
                else if (selection == "BukitBatok") {
                    x = 1.322336;
                    y = 103.881873;
                }
                else if (selection == "PeltonCanal") {
                    x = 1.356762;
                    y = 103.753643;
                }
                else if (selection == "Bishan") {
                    x = 1.363456;
                    y = 103.843573;
                }
                else if (selection == "UluPandan") {
                    x = 1.312644;
                    y = 103.779675;
                }
                else if (selection == "WestCoastPark") {
                    x = 1.317598;
                    y = 103.754378;
                }
                else if (selection == "ChuaChuKang") {
                    x = 1.387777;
                    y = 103.747071;
                }
                else x = 0;

                // check the value of x
                //txtview.setText(""+x);

            }
        });

        distSubmitButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            distance = Float.parseFloat(editText.getText().toString())*1000;
            //check the value of distance
            Intent intent = new Intent(RoutePlanning.this, RoutePlanner.class);
            intent.putExtra("DESTINATION","");
            intent.putExtra("DISTANCE",(float) distance);
            Log.e("Submitted distance: ",String.valueOf(distance));
            startActivity(intent);
          }
        });


        //user selects distance
        distanceButton = (Button) findViewById(R.id.DistanceButton);
        distanceButton.setBackgroundColor(Color.LTGRAY);

        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                acTxtview.setVisibility(View.INVISIBLE);
                distSubmitButton.setVisibility(View.VISIBLE);
                endpointSubmitButton.setVisibility(View.INVISIBLE);
                endpointButton.setBackgroundColor(Color.GRAY);
                distanceButton.setBackgroundColor(Color.LTGRAY);

                distSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        distance = Float.parseFloat(editText.getText().toString())*1000;
                        //check the value of distance
                        Intent intent = new Intent(RoutePlanning.this, RoutePlanner.class);
                        intent.putExtra("DESTINATION","");
                        intent.putExtra("DISTANCE",(float) distance);
                        Log.e("Submitted distance: ",String.valueOf(distance));
                        startActivity(intent);
                    }
                });

            }
        });

        //user selects endpoint
        endpointButton = (Button) findViewById(R.id.EndpointButton);
        endpointButton.setBackgroundColor(Color.GRAY);

        endpointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acTxtview.setVisibility(View.VISIBLE);
                endpointButton.setBackgroundColor(Color.LTGRAY);
                distanceButton.setBackgroundColor(Color.GRAY);

                editText.setVisibility(View.INVISIBLE);
                endpointSubmitButton.setVisibility(View.VISIBLE);
                distSubmitButton.setVisibility(View.INVISIBLE);


                endpointSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoutePlanning.this, RoutePlanner.class);
                        intent.putExtra("DESTINATION",String.valueOf(x)+","+String.valueOf(y));
                        intent.putExtra("DISTANCE",0f);
                        startActivity(intent);
                    }
                });
            }
        });




    setNavigationDrawer();

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
                }else{
                    // Setting intent
                    Intent intent = new Intent(RoutePlanning.this,Profile.class);
                    startActivity(intent);
                }
                dLayout.closeDrawers();
                return true;
            }
        });
    }
}
