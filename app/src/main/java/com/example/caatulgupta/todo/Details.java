package com.example.caatulgupta.todo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class Details extends AppCompatActivity {

    TextView titleTV,descTV,dateTV,timeTV,dtCreatedTV;
    String title,desc,date,time,dtCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleTV = findViewById(R.id.showTitle);
        descTV = findViewById(R.id.showDesc);
        dateTV = findViewById(R.id.showDate);
        timeTV = findViewById(R.id.showTime);
        dtCreatedTV = findViewById(R.id.dtCreated);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        desc = intent.getStringExtra("Desc");
        date = intent.getStringExtra("Date");
        time = intent.getStringExtra("Time");
        dtCreated = intent.getStringExtra("DTCreated");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        dtCreatedTV.setText(dtCreated);


//        int date = intent.getIntExtra("Date",0);
//        int time = intent.getIntExtra("Time",0);

        titleTV.setText(title);
        descTV.setText(desc);
        dateTV.setText(date);
        timeTV.setText(time);
//        dateTV.setText(date);
//        timeTV.setText(time);
    }

    public void editTODO(View view){
        Intent intent = new Intent(this,EditDetails.class);
        intent.putExtra("Title",title);
        intent.putExtra("Desc",desc);
        intent.putExtra("Date",date);
        intent.putExtra("Time",time);
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==3 && resultCode==4){
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            String date = data.getStringExtra("Date");
            String time = data.getStringExtra("Time");
            titleTV.setText(title);
            descTV.setText(desc);
            dateTV.setText(date);
            timeTV.setText(time);
            Intent intent = new Intent();
            intent.putExtra("Title",title);
            intent.putExtra("Desc",desc);
            intent.putExtra("Date",date);
            intent.putExtra("Time",time);

            setResult(4,intent);
        }
    }
}
