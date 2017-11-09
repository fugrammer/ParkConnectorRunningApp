package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import com.example.a2006_3.parkconnectorrunningapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.v7.appcompat.R.styleable.View;

public class WorkoutSummary extends AppCompatActivity implements WorkoutsummaryAPI.RequestListener{

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
        float distanceRan = intent.getFloatExtra("distanceRan",-1);

        Log.e("DistanceRan:",String.valueOf(distanceRan));

        String cal = intent.getStringExtra("running");
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        // Added by Thomas
        summarytimeTextView.setText(String.valueOf(time)+ " min " + String.valueOf(sec)+" sec");
        summarydistTextView.setText(String.format("%.2f km",distanceRan/1000.0));
        summarycaloriesTextView.setText(String.format("%.0f cal",distanceRan/1000*60.625));
        ((TextView)findViewById(R.id.summarycaloriesCyclingTextView)).setText(String.format("%.0f cal",distanceRan/1000*30));
        findViewById(R.id.finishWorkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutSummary.this,RoutePlanning.class);
                startActivity(intent);
            }
        });

        new WorkoutsummaryAPI("trudy",String.valueOf(time),String.valueOf(sec),String.format("%.0f",distanceRan),String.format("%.0f",distanceRan/1000*60.265),formattedDate,this).execute(); //executes workoutsummary API to send data to backend
    }


    @Override
    public void onFinished(String result) {

    }
}
