package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.R;

public class Profile extends AppCompatActivity {

    TextView profiledateTextView, profiletimetakenTextView, profiledisttakenTextView, profilecaloriestakenTextView;
    DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profiledateTextView = (TextView) findViewById(R.id.profiledateTextView);
        profiletimetakenTextView = (TextView) findViewById(R.id.profiletimetakenTextView);
        profiledisttakenTextView = (TextView) findViewById(R.id.profiledisttakenTextView);
        profilecaloriestakenTextView = (TextView) findViewById(R.id.profilecaloriestakenTextView);
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
}
