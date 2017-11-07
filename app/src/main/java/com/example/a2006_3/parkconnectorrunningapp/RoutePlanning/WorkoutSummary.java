package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import com.example.a2006_3.parkconnectorrunningapp.R;

public class WorkoutSummary extends AppCompatActivity {

    TextView summarytimeTextView, summarydistTextView, summarycaloriesTextView;
    int time;
    String distance;
    
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
        int sec = intent.getIntExtra("timeSec",0);
        distance = intent.getStringExtra("distance");
        
        // Added by Thomas
        summarytimeTextView.setText(String.valueOf(time)+ " min " + String.valueOf(sec)+" sec");
        summarydistTextView.setText(distance+" km");
        summarycaloriesTextView.setText(intent.getStringExtra("running") + " cal");
        ((TextView)findViewById(R.id.summarycaloriesCyclingTextView)).setText(intent.getStringExtra("running") + " cal");
        findViewById(R.id.finishWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutSummary.this,RoutePlanning.class);
                startActivity(intent);
            }
        });

    }

    //Function to get data from previous screen/intent(?)
    private void getData(int id){

    }

    //Update current text views using previous data
    private void updateTextViews(int id){

    }



}
