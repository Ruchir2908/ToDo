package com.example.caatulgupta.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter {

    ArrayList<ToDo> toDos;
    LayoutInflater inflater;

    public ToDoAdapter(Context context, ArrayList<ToDo> toDos) {
        super(context, 0, toDos);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.toDos = toDos;
    }

    @Override
    public int getCount() {
        return toDos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = inflater.inflate(R.layout.todo_layout,null);
        TextView title = output.findViewById(R.id.title);
        TextView desc = output.findViewById(R.id.desc);
        ToDo toDo = toDos.get(position);
        title.setText(toDo.getTitle());
        desc.setText(toDo.getDescription());
        return output;
    }
}
