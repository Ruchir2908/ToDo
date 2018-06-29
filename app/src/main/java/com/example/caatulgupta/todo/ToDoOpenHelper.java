package com.example.caatulgupta.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoOpenHelper extends SQLiteOpenHelper {


    public ToDoOpenHelper(Context context) {
        super(context,Contract.TODO.DATABSE_NAME,null,Contract.TODO.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String todoSql = "CREATE TABLE "+Contract.TODO.TABLE_NAME+" ( "+Contract.TODO.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Contract.TODO.COLUMN_TITLE+" TEXT, "+Contract.TODO.COLUMN_DESCRIPTION+" TEXT, "+Contract.TODO.COLUMN_DATE+" TEXT, "+Contract.TODO.COLUMN_TIME+" TEXT, "+Contract.TODO.COLUMN_DTCREATED+" TEXT )";
        sqLiteDatabase.execSQL(todoSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
