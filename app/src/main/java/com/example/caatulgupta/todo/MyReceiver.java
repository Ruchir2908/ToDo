package com.example.caatulgupta.todo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class MyReceiver extends BroadcastReceiver {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Calendar newCalendar = Calendar.getInstance();
    int month = newCalendar.get(Calendar.MONTH);
    int year = newCalendar.get(Calendar.YEAR);
    int day = newCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
    int min = newCalendar.get(Calendar.MINUTE);
    String dtCrerated = toString().valueOf(day)+"/"+toString().valueOf(month + 1)+"/"+toString().valueOf(year)+" at "+toString().valueOf(hour)+":"+toString().valueOf(min);
    String date = toString().valueOf(day)+"/"+toString().valueOf(month+2)+"/"+toString().valueOf(year);
    String time = toString().valueOf(hour)+":"+toString().valueOf(min);

    @Override
    public void onReceive(Context context, Intent intent) {


        sharedPreferences = context.getSharedPreferences("todo",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean sms = sharedPreferences.getBoolean("SMS",true);
        if(sms) {

            String sender = "", messageBody = "";
            Bundle data = intent.getExtras();
            Object[] pdus = (Object[]) data.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender = smsMessage.getDisplayOriginatingAddress();
                messageBody = smsMessage.getMessageBody();
            }
            ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(context);
            SQLiteDatabase database = openHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.TODO.COLUMN_TITLE, sender.substring(3));
            contentValues.put(Contract.TODO.COLUMN_DESCRIPTION, messageBody);
            contentValues.put(Contract.TODO.COLUMN_DATE, date);
            contentValues.put(Contract.TODO.COLUMN_TIME, time);
            contentValues.put(Contract.TODO.COLUMN_DTCREATED, dtCrerated);
            database.insert(Contract.TODO.TABLE_NAME, null, contentValues);

            Toast.makeText(context, "ToDo Created", Toast.LENGTH_LONG).show();

            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("myChannelId", "ToDoChannel", NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"myChannelId");
            builder.setContentTitle("ToDo");
            builder.setContentText("ToDo created from "+sender);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            Notification notification = builder.build();
            manager.notify(1,notification);
        }
    }
}
