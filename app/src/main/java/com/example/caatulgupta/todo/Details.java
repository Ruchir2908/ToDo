package com.example.caatulgupta.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class Details extends AppCompatActivity {

    TextView titleTV,descTV,dateTV,timeTV,dtCreatedTV;
    String title,desc,date,time,dtCreated;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("DetailActivity","DetailActivity onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleTV = findViewById(R.id.showTitle);
        descTV = findViewById(R.id.showDesc);
        dateTV = findViewById(R.id.showDate);
        timeTV = findViewById(R.id.showTime);
        dtCreatedTV = findViewById(R.id.dtCreated);

        Intent intent = getIntent();
//        title = intent.getStringExtra("Title");
//        desc = intent.getStringExtra("Desc");
//        date = intent.getStringExtra("Date");
//        time = intent.getStringExtra("Time");
//        dtCreated = intent.getStringExtra("DTCreated");

        id = intent.getLongExtra("ID",-1);
        ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(this);
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String[] selectionArgs = {id+""};
        Log.i("IDMeriii",id+"");
        Cursor cursor = database.query(Contract.TODO.TABLE_NAME,null,Contract.TODO.COLUMN_ID+" = ?",selectionArgs,null,null,null);
        cursor.moveToNext();
        String title = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TITLE));
        String desc = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DATE));
        String time = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TIME));
        String dtCreated = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DTCREATED));
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
//        intent.putExtra("Title",title);
//        intent.putExtra("Desc",desc);
//        intent.putExtra("Date",date);
//        intent.putExtra("Time",time);
        intent.putExtra("ID",id);
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==3 && resultCode==4){
//            String title = data.getStringExtra("Title");
//            String desc = data.getStringExtra("Desc");
//            String date = data.getStringExtra("Date");
//            String time = data.getStringExtra("Time");
//            long id = data.getLongExtra("ID",0);
            //id = intent.getLongExtra("ID",0);
            ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(this);
            SQLiteDatabase database = openHelper.getReadableDatabase();
            String[] selectionArgs = {id+""};
            Cursor cursor = database.query(Contract.TODO.TABLE_NAME,null,Contract.TODO.COLUMN_ID+" = ?",selectionArgs,null,null,null);
            cursor.moveToNext();
            String title = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TIME));
            String dtCreated = cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_DTCREATED));
            titleTV.setText(title);
            descTV.setText(desc);
            dateTV.setText(date);
            timeTV.setText(time);
            Intent intent = new Intent();
//            intent.putExtra("Title",title);
//            intent.putExtra("Desc",desc);
//            intent.putExtra("Date",date);
//            intent.putExtra("Time",time);
            intent.putExtra("ID",id);
            setResult(4,intent);
        }
    }

    @Override
    protected void onStart() {
        Log.i("DetailActivity","DetailActivity onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("DetailActivity","DetailActivity onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("DetailActivity","DetailActivity onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("DetailActivity","DetailActivity onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("DetailActivity","DetailActivity onDestroy()");
        super.onDestroy();
    }
}
