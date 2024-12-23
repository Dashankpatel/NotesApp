package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    private DataStore dataStore;
    private ArrayList<HashMap<Object, Object>> datalist;

    public MyAdapter(DataStore dataStore, ArrayList<HashMap<Object, Object>> datalist) {
        this.dataStore = dataStore;
        this.datalist = datalist;
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
        TextView deleted = ds.findViewById(R.id.deleted);
        TextView shared = ds.findViewById(R.id.shared);

        HashMap<Object, Object> data = datalist.get(position);

        tit2.setText(data.get("Title").toString());
        detai.setText(data.get("Description").toString());

        tit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataStore.startActivity(new Intent(dataStore, Edit.class)
                        .putExtra("key", data.get("key").toString())
                        .putExtra("Title", tit2.getText())
                        .putExtra("Description", detai.getText()));

            }
        });

        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(dataStore, "Share", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_SEND);
                String shareBody = "Title :- " + tit2.getText().toString() + "\n" + "Description :- " + detai.getText().toString();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                dataStore.startActivity(Intent.createChooser(intent, ""));
            }
        });

        deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(dataStore);
                dialog.setContentView(R.layout.dialogview_delete);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView tex2 = dialog.findViewById(R.id.tex2);
                Button yes2 = dialog.findViewById(R.id.yes2);
                Button no2 = dialog.findViewById(R.id.no2);

                tex2.getText();

                yes2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        DatabaseReference myRef = database.getReference("user").child("key");
                        DatabaseReference myRef = FirebaseDatabase.getInstance()
                                .getReference("user")
                                .child(data.get("key").toString());

                        myRef.removeValue();
                        dialog.dismiss();

                        dataStore.startActivity(new Intent(dataStore,DataStore.class)
                                .putExtra("key", data.get("key").toString())
                                .putExtra("Title", tit2.getText())
                                .putExtra("Description", detai.getText()));
                        dataStore.finishAffinity();

                    }
                });

                no2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return ds;
    }
}
