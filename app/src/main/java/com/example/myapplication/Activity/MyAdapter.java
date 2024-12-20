package com.example.myapplication.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    private DataStore dataStore;
    private ArrayList<HashMap<Object, Object>> datalist;

    public MyAdapter(DataStore dataStore, ArrayList<HashMap<Object, Object>> datalist) {
        this.dataStore=dataStore;
        this.datalist=datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View ds = LayoutInflater.from(dataStore).inflate(R.layout.myview, parent, false);

        TextView tit2 = ds.findViewById(R.id.tit2);
        TextView detai = ds.findViewById(R.id.detai);

        HashMap<Object, Object> data = datalist.get(position);

        tit2.setText(data.get("Title").toString());
        detai.setText(data.get("Description").toString());


        tit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataStore.startActivity(new Intent(dataStore,Update.class)
                        .putExtra("key",data.get("key").toString())
                        .putExtra("Title",tit2.getText())
                        .putExtra("Description",detai.getText()));

            }
        });

        return ds;
    }
}
