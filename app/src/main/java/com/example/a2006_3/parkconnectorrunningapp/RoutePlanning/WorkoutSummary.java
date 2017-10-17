package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.Manifest;

import com.example.a2006_3.parkconnectorrunningapp.R;

public class WorkoutSummary extends AppCompatActivity {

    TextView summarytimeTextView, summarydistTextView, summarycaloriesTextView;
    int time;
    float distance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_summary);

        // Data fields to be filled in from navigation page values
        summarytimeTextView = (TextView) findViewById(R.id.summarytimeTextView);
        summarydistTextView = (TextView) findViewById(R.id.summarydistTextView);
        summarycaloriesTextView = (TextView) findViewById(R.id.summarycaloriesTextView);

        // Get data from previous screen regarding workout
        Intent intent = getIntent();
        time = intent.getIntExtra("time",0);
        distance = intent.getFloatExtra("distance",0);

    }

    //Function to get data from previous screen/intent(?)
    private void getData(int id){

    }

    //Update current text views using previous data
    private void updateTextViews(int id){

    }



}
