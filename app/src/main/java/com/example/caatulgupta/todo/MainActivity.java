package com.example.caatulgupta.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<ToDo> toDos = new ArrayList<>();
    int posToDel = 0;
    ToDoAdapter adapter;
//    View alertView = findViewById(R.id.alert);
    EditText etTitle, etDesc;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Calendar newCalendar = Calendar.getInstance();
    int month = newCalendar.get(Calendar.MONTH);
    int year = newCalendar.get(Calendar.YEAR);
    int day = newCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
    int min = newCalendar.get(Calendar.MINUTE);
//    String dtCreated =  toString().valueOf(day)+"/"+toString().valueOf(month)+"/"+toString().valueOf(year)+" at "+toString().valueOf(hour)+":"+toString().valueOf(min);

    StringBuilder stringBuilderTitle = new StringBuilder();
    StringBuilder stringBuilderDesc = new StringBuilder();
    StringBuilder stringBuilderDate = new StringBuilder();
    StringBuilder stringBuilderTime = new StringBuilder();
    StringBuilder stringBuilderDTCreated = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("todo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
//
//        if(titles.size()!=0 && descs.size()!=0){
//            for(int i=0;i<titles.size();i++){
//                ToDo toDo = new ToDo()
//            }
//        }
        ListView listView = findViewById(R.id.listView);
//        for(int i=0;i<5;i++){
//            ToDo toDo = new ToDo("First","temporary");
//            toDos.add(toDo);
//        }
        adapter = new ToDoAdapter(this,toDos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDo toDo = toDos.get(i);
                final int pos = i;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setView();
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toDos.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Deletion Cancelled",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });



        String title = sharedPreferences.getString("Title",null);
        String desc = sharedPreferences.getString("Desc",null);
        String date = sharedPreferences.getString("Date",null);
        String time = sharedPreferences.getString("Time",null);
        String dtCreated = sharedPreferences.getString("DTCreated",null);

        if(title!=null && desc!=null && date!=null && time!=null && dtCreated!=null) {
            Log.i("MainActivity1",desc);
            String[] t = title.split("%");
            String[] d = desc.split("%");
            String[] da = date.split("%");
            String[] ti = time.split("%");
            String[] dt = dtCreated.split("%");
            for (int i = 0; i < t.length; i++) {
                Log.i("DTCreated",dt[i]);
                ToDo toDo = new ToDo(t[i], d[i],da[i],ti[i],dt[i]);
                toDos.add(toDo);
                adapter.notifyDataSetChanged();
            }
        }




      /*  String t = "",d = "";
        int pt = 0, pd = 0;
        for(int i=0,j=0;i<title.length() || j<desc.length();i++,j++){
            if(i<title.length()) {
                if (title.charAt(i) == '%') {
                    t = title.substring(pt,i);
                    pt = i;
                }
            }

            if(j<desc.length()) {
                if (desc.charAt(j) == '%') {
                    d = desc.substring(pd,i);
                    pd = j;
                }
            }
            ToDo toDo = new ToDo(t,d);
        }    */



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add) {



/*
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            final View alertView = inflater.inflate(R.layout.alert_dialog, null);

//        View alertView = findViewById(R.id.alert);
            builder.setView(alertView);
            builder.setTitle("Add ToDo");
            builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    etTitle = alertView.findViewById(R.id.getTitle);
                    etDesc = alertView.findViewById(R.id.getDesc);
                    String title = etTitle.getText().toString();
                    String desc = etDesc.getText().toString();
                    ToDo toDo = new ToDo(title, desc);


//                editor.putString("Desc",sharedPreferences.getString("Desc",null)+"%"+desc);
//                editor.putString("Title",sharedPreferences.getString("Title",null)+"%"+title);
//                editor.commit();
                    toDos.add(toDo);
                for(int j=0;j<toDos.size();j++){
                    Toast.makeText(MainActivity.this,"HIIII",Toast.LENGTH_LONG).show();
                    stringBuilder.append(toDos.get(j).getTitle());
                    stringBuilder1.append(toDos.get(j).getDescription());
                    stringBuilder.append("%");
                    stringBuilder1.append("%");
                }
                Log.i("MainActivity",stringBuilder.toString());
                editor.putString("Title",stringBuilder.toString());
                editor.putString("Desc",stringBuilder1.toString());
                editor.commit();


                    Toast.makeText(MainActivity.this, "Task succesfully added", Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "Task not added", Toast.LENGTH_LONG).show();
                }
            });
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            */
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            startActivityForResult(intent,1);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            String date = data.getStringExtra("Date");
            String time = data.getStringExtra("Time");
            String dtCreated = data.getStringExtra("DTCreated");

            ToDo toDo = new ToDo(title,desc,date,time,dtCreated);
            toDos.add(toDo);
            stringBuilderTitle = new StringBuilder();
            stringBuilderDesc = new StringBuilder();
            stringBuilderDate = new StringBuilder();
            stringBuilderTime = new StringBuilder();
            stringBuilderDTCreated = new StringBuilder();
            for(int j=0;j<toDos.size();j++){

                stringBuilderTitle.append(toDos.get(j).getTitle()); stringBuilderTitle.append("%");
                stringBuilderDesc.append(toDos.get(j).getDescription()); stringBuilderDesc.append("%");
                stringBuilderDate.append(toDos.get(j).getDate()); stringBuilderDate.append("%");
                stringBuilderTime.append(toDos.get(j).getTime()); stringBuilderTime.append("%");
                stringBuilderDTCreated.append(toDos.get(j).getDtCreated()); stringBuilderDTCreated.append("%");
            }
            Log.i("DTCreated","Final: " +stringBuilderTitle.toString());
            editor.putString("Title",stringBuilderTitle.toString());
            editor.putString("Desc",stringBuilderDesc.toString());
            editor.putString("Date",stringBuilderDate.toString());
            editor.putString("Time",stringBuilderTime.toString());
            editor.putString("DTCreated",stringBuilderDTCreated.toString());
            editor.commit();
            adapter.notifyDataSetChanged();
        }

        if(requestCode==3 && resultCode==4){
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            String date = data.getStringExtra("Date");
            String time = data.getStringExtra("Time");
            String dtCreated = data.getStringExtra("DTCreated");
            ToDo toDo = new ToDo(title,desc,date,time,dtCreated);
            toDos.set(posToDel,toDo);

//            toDos.add(toDo);
//            toDos.remove(posToDel);


            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToDo toDo = toDos.get(i);
        Intent intent = new Intent(MainActivity.this,Details.class);
        intent.putExtra("Title",toDo.getTitle());
        intent.putExtra("Desc",toDo.getDescription());
        intent.putExtra("Date",toDo.getDate());
        intent.putExtra("Time",toDo.getTime());
        intent.putExtra("DTCreated",toDo.getDtCreated());

        posToDel = i;
//        intent.putExtra("Time",toDo.getTime());
//        intent.putExtra("Date",toDo.getDate());
        startActivityForResult(intent,3);
    }

}
