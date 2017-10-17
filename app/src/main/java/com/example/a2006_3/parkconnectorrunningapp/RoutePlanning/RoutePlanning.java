package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.R;

public class RoutePlanning extends AppCompatActivity {
    AutoCompleteTextView acTextView;
    TextView txtview;
    float x, y,distance;
    EditText editText;
    String[] connectors = {"Kallang", "Bishan","JurongWest","Jurong","BukitBatok","PeltonCanal", "UluPandan", "WestCoastPark", "ChuaChuKang"};
    Button confirm;
    DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning);

        //choose end point
        acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,connectors);

        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

        //a testing textview to see the values stored
        txtview = (TextView) findViewById(R.id.textView4);

        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the end point selected by user
                String selection = (String)parent.getItemAtPosition(position);

                if (selection == "Kallang") {
                    x = 1.306609f;
                    y = 103.869922f;
                }
                else if (selection == "JurongWest") {
                    x = 1.353074f;
                    y = 103.714731f;
                }
                else if (selection == "Jurong") {
                    x = 1.342577f;
                    y = 103.719879f;
                }
                else if (selection == "BukitBatok") {
                    x = 1.322336f;
                    y = 103.881873f;
                }
                else if (selection == "PeltonCanal") {
                    x = 1.356762f;
                    y = 103.753643f;
                }
                else if (selection == "Bishan") {
                    x = 1.363456f;
                    y = 103.843573f;
                }
                else if (selection == "UluPandan") {
                    x = 1.312644f;
                    y = 103.779675f;
                }
                else if (selection == "WestCoastPark") {
                    x = 1.317598f;
                    y = 103.754378f;
                }
                else if (selection == "ChuaChuKang") {
                    x = 1.387777f;
                    y = 103.747071f;
                }
                else x = 0;
                Coordinate coordinate = new Coordinate(x,y);
                Intent intent = new Intent(RoutePlanning.this, RoutePlanner.class);
                intent.putExtra("DESTINATION",String.valueOf(x)+","+String.valueOf(y));
                intent.putExtra("DISTANCE",0f);
                startActivity(intent);
                // check the value of x
                //txtview.setText(""+x);

            }
        });


        //choose distance
        editText = (EditText) findViewById(R.id.editText);

        confirm = (Button) findViewById(R.id.button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = Float.parseFloat(editText.getText().toString())*1000;
                //check the value of distance
                Intent intent = new Intent(RoutePlanning.this, RoutePlanner.class);
                intent.putExtra("DESTINATION","");
                intent.putExtra("DISTANCE",distance);
                startActivity(intent);
                txtview.setText(""+distance);
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
