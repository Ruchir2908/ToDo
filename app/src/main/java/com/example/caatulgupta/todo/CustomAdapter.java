package com.example.caatulgupta.todo;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context myContext;
    ArrayList<Item> itemsArrayList;
    LayoutInflater inflater;

    public CustomAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
        super(context, 0);

        myContext = context;
        inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsArrayList = itemArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return itemsArrayList.get(position).getItemType();
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return itemsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = convertView;
        if(getItemViewType(position)==0){
            if(output==null){
                output = inflater.inflate(R.layout.header_layout,parent,false);
                TextView headerTitleTextView = output.findViewById(R.id.headerTitleTextView);
                HeaderViewHolder headerViewHolder = new HeaderViewHolder(headerTitleTextView);
                output.setTag(headerViewHolder);
            }
            HeaderViewHolder headerViewHolder = (HeaderViewHolder)output.getTag();

        }
        return output;
    }
}
