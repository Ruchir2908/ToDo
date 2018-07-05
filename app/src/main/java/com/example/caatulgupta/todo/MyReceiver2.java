package com.example.caatulgupta.todo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra("ID",-1);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myChannelId", "ToDoChannel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"myChannelId");
        builder.setContentTitle("ToDo");
        ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(context);
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String[] selectionArgs = {id+""};
        String[] cols = {Contract.TODO.COLUMN_TITLE};
        Cursor cursor = database.query(Contract.TODO.TABLE_NAME,null,Contract.TODO.COLUMN_ID+" = ?",selectionArgs,null,null,null);
        cursor.moveToNext();
        builder.setContentText(cursor.getString(cursor.getColumnIndex(Contract.TODO.COLUMN_TITLE)));

        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent intent1 = new Intent(context,Details.class);
        intent1.putExtra("ID",id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,2,intent1,0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1,notification);
    }
}
