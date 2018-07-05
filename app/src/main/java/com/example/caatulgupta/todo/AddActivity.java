package com.example.caatulgupta.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;

public class AddActivity extends AppCompatActivity {

    EditText titleEditText, descEditText, dateEditText, timeEditText;
    TextView dateTV;

    Calendar newCalendar = Calendar.getInstance();
    int month = newCalendar.get(Calendar.MONTH);
    int year = newCalendar.get(Calendar.YEAR);
    int day = newCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
    int min = newCalendar.get(Calendar.MINUTE);
    String dtCrerated = toString().valueOf(day)+"/"+toString().valueOf(month + 1)+"/"+toString().valueOf(year)+" at "+toString().valueOf(hour)+":"+toString().valueOf(min);



    long id;

//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//boolean finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//        finish = false;

        newCalendar.set(year,month,day,hour,min);

        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descEditText);
        //dateTV = findViewById(R.id.dateTV);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
//        int nextDay = day + 15;
//        if(nextDay>30 && (month==3 || month==5 || month==8 || month==10)){
//            day = nextDay-30;
//            month++;
//        }
//        if(nextDay>31 && (month==0 || month==2 || month==4 || month==6 || month==7 || month==9 || month==11)){
//            day = nextDay-31;
//            month++;
//        }
//        if(nextDay>28 && month==1 && (year%4==0)){
//            day = nextDay-28;
//            month++;
//        }
//        if(nextDay>29 && month==1 && (year%4!=0)){
//            day = nextDay-29;
//            month++;
//        }
        dateEditText.setText(toString().valueOf(day)+"/"+toString().valueOf(month+2)+"/"+toString().valueOf(year));
//        if(hour>12){
//            hour -= 12;
//        }
        timeEditText.setText(toString().valueOf(hour)+" : "+toString().valueOf(min));

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDATE(view,day,month,year);
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTIME(view);
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        if(action == ACTION_SEND){
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            descEditText.setText(text);
            titleEditText.setText("Copied");
//            finish = true;
        }


    }

    public void addTIME(View view){

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = (LayoutInflater)AddActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View time = inflater.inflate(R.layout.time,null);
//        builder.setView(time);
//        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.setTitle("Add Time");
//        AlertDialog dialog = builder.create();
//        dialog.show();

//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(){
//
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//            }
//        });

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int min = i1;
//                if(hour>12){
//                    hour -= 12;
//                }
                timeEditText.setText(toString().valueOf(hour)+" : "+toString().valueOf(min));
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
                dateEditText.setText(toString().valueOf(i)+" "+toString().valueOf(i1+1)+" "+toString().valueOf(i2));
            }
        },year,month,day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    public void addTODO(View view){
        String title = titleEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();


        ToDo toDo = new ToDo(title,desc,date,time,dtCrerated);


        ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(this);
        SQLiteDatabase database = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TODO.COLUMN_TITLE,title);
        contentValues.put(Contract.TODO.COLUMN_DESCRIPTION,desc);
        contentValues.put(Contract.TODO.COLUMN_DATE,date);
        contentValues.put(Contract.TODO.COLUMN_TIME,time);
        contentValues.put(Contract.TODO.COLUMN_DTCREATED,dtCrerated);

        id = database.insert(Contract.TODO.TABLE_NAME,null,contentValues);
        toDo.setId(id);
        Intent intent = getIntent();
        intent.putExtra("ID",toDo);
        Toast.makeText(this, "ToDo Created", Toast.LENGTH_SHORT).show();

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this,MyReceiver2.class);
        intent1.putExtra("ID",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent1,0);
        Log.i("Alarm",toString().valueOf(newCalendar.getTimeInMillis()));
        alarmManager.set(AlarmManager.RTC_WAKEUP,newCalendar.getTimeInMillis(),pendingIntent);

//        Intent intent = new Intent();
//        intent.putExtra("Title",title);
//        intent.putExtra("Desc",desc);
//        intent.putExtra("Date",date);
//        intent.putExtra("Time",time);
//        intent.putExtra("DTCreated",dtCrerated);
        setResult(2,intent);
//        if(!finish) {
            finish();
//        }
    }
}
