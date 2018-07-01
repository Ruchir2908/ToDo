package com.example.caatulgupta.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditDetails extends AppCompatActivity {

    EditText titleET,descET,dateET,timeET;
    long id;

    Calendar newCalendar = Calendar.getInstance();
    int month = newCalendar.get(Calendar.MONTH);
    int year = newCalendar.get(Calendar.YEAR);
    int day = newCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
    int min = newCalendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        titleET = findViewById(R.id.titleEditText);
        descET = findViewById(R.id.descEditText);
        dateET = findViewById(R.id.dateEditText);
        timeET = findViewById(R.id.timeEditText);

        Intent intent = getIntent();
//        String title = intent.getStringExtra("Title");
//        String desc = intent.getStringExtra("Desc");
//        String date = intent.getStringExtra("Date");
//        String time = intent.getStringExtra("Time");


        id = intent.getLongExtra("ID",0);
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



        titleET.setText(title);
        descET.setText(desc);
        dateET.setText(date);
        timeET.setText(time);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDATE(view,day,month,year);
            }
        });

        timeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTIME(view);
            }
        });
    }

    public void addTIME(View view){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int min = i1;
//                if(hour>12){
//                    hour -= 12;
//                }
                timeET.setText(toString().valueOf(hour)+" : "+toString().valueOf(min));
            }
        },hour,min,true);
        timePickerDialog.setTitle("Set time");
        timePickerDialog.show();

    }

    public void addDATE(View view,int day,int month,int year){
//        DatePickerDialog date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(i,i1,i2);
//                dateTV = findViewById(R.id.dateTV);
//                dateTV.setText(newDate.getTime().toString());
//            }
//        });

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i,i1,i2);
//                String date = i+" "+i1+" "+i2;
                dateET.setText(toString().valueOf(i)+" "+toString().valueOf(i1)+" "+toString().valueOf(i2));
            }
        },year,month,day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    public void addTODO(View view){
        String title = titleET.getText().toString();
        String desc = descET.getText().toString();
        String date = dateET.getText().toString();
        String time = timeET.getText().toString();
        String dtCreated;

        Intent intent = new Intent();
//        intent.putExtra("Title",title);
//        intent.putExtra("Desc",desc);
//        intent.putExtra("Date",date);
//        intent.putExtra("Time",time);

        ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TODO.COLUMN_TITLE,title);
        contentValues.put(Contract.TODO.COLUMN_DESCRIPTION,desc);
        contentValues.put(Contract.TODO.COLUMN_DATE,date);
        contentValues.put(Contract.TODO.COLUMN_TIME,time);
//        contentValues.put(Contract.TODO.COLUMN_DTCREATED,dtCreated);
        String[] selectionArgs = {id+""};
        database.update(Contract.TODO.TABLE_NAME,contentValues,Contract.TODO.COLUMN_ID+" = ?",selectionArgs);
        intent.putExtra("ID",id);
        setResult(4,intent);
        finish();
    }
}
