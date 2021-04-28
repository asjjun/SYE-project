package com.example.sye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowVolunteerPlanActivity extends AppCompatActivity {
    Intent intent;
    TextView dateText,timeText,placeText,goalText,contentText,prepareText,etcText;
    String vol_date,vol_time,place,vol_goal,activity_content,vol_prepare,etc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_volunteer_plan);
        intent=getIntent();

        dateText=findViewById(R.id.dateText);
        timeText=findViewById(R.id.timeText);
        placeText=findViewById(R.id.placeText);
        goalText=findViewById(R.id.goalText);
        contentText=findViewById(R.id.contentText);
        prepareText=findViewById(R.id.prepareText);
        etcText=findViewById(R.id.etcText);

        vol_date=intent.getStringExtra("vol_date");
        vol_time=intent.getStringExtra("vol_time");
        place=intent.getStringExtra("place");
        vol_goal=intent.getStringExtra("vol_goal");
        activity_content=intent.getStringExtra("activity_content");
        vol_prepare=intent.getStringExtra("vol_prepare");
        etc=intent.getStringExtra("etc");

        dateText.setText(vol_date);
        timeText.setText(vol_time);
        placeText.setText(place);
        goalText.setText(vol_goal);
        contentText.setText(activity_content);
        prepareText.setText(vol_prepare);
        etcText.setText(etc);

    }
}
