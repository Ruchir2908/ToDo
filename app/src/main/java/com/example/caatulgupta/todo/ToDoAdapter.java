package com.example.caatulgupta.todo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter {

    ArrayList<ToDo> toDos;
    LayoutInflater inflater;
    Context context;
    ToDoItemClickListener clickListener;

    public ToDoAdapter(Context context, ArrayList<ToDo> toDos, ToDoItemClickListener clickListener) {
        super(context, 0, toDos);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.toDos = toDos;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return toDos.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = inflater.inflate(R.layout.todo_layout,null);
        TextView title = output.findViewById(R.id.title);
        TextView desc = output.findViewById(R.id.desc);
        Button starButton = output.findViewById(R.id.starButton);
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.rowButtonClicked(view,position);
            }
        });
        ToDo toDo = toDos.get(position);
        title.setText(toDo.getTitle());
        desc.setText(toDo.getDescription());
        return output;
    }
}
