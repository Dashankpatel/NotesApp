package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;
//import com.example.myapplication.database.MyDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class DataStore extends AppCompatActivity {

    SearchView search;
    FloatingActionButton add, pop;
    ListView list;
    private FirebaseAuth mAuth;

    ArrayList<HashMap<Object, Object>> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_store);
        mAuth = FirebaseAuth.getInstance();

        add = findViewById(R.id.add);
        list = findViewById(R.id.list);
        pop = findViewById(R.id.pop);
        search = findViewById(R.id.search);

        ArrayList<HashMap<Object, Object>> searchlist = new ArrayList<>();

        // new data add karava mate
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DataStore.this, Addnew.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");

        // main page store(add) data show karava mate
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    HashMap alldata = (HashMap<Object, Object>) dataSnapshot.getValue();

                    for (Object data : alldata.values()) {
                        HashMap userdata = (HashMap<Object, Object>) data;

                        Log.d("--+--", "onDataChange: " + userdata.get("Title"));
                        Log.d("--+--", "onDataChange: " + userdata.get("Description"));

                        datalist.add(userdata);
                    }
                    MyAdapter adapter = new MyAdapter(DataStore.this, datalist);
                    list.setAdapter(adapter);
                } else {
                    Toast.makeText(DataStore.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("--+--", "loadPost:onCancelled", error.toException());
            }
        };
        myRef.addValueEventListener(postListener);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
//            submit ni click pa6i data show karava
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchlist.clear();

                for (HashMap<Object, Object> data : datalist) {
                    String title = data.get("Title").toString();
                    String description = data.get("Description").toString();

                    if (title.contains(newText) || description.contains(newText)) {
                        searchlist.add(data);
                    }
                }

                MyAdapter searchAdapter = new MyAdapter(DataStore.this, searchlist);
                list.setAdapter(searchAdapter);

                return true;
            }
        });

        // pop up menu open karava
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pmenu = new PopupMenu(DataStore.this, pop);

                pmenu.inflate(R.menu.mymenu);
                pmenu.show();

                pmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.exit) {
                            // exit karva mate no dialogue box open thase
                            Dialog dialog = new Dialog(DataStore.this);
                            dialog.setContentView(R.layout.dialogview_exit);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.show();

                            TextView tex = dialog.findViewById(R.id.tex);
                            Button yes = dialog.findViewById(R.id.yes);
                            Button no = dialog.findViewById(R.id.no);

                            tex.getText();

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SplaceScreen.edit.putBoolean("status", false);
                                    SplaceScreen.edit.apply();
                                    SplaceScreen.edit.putInt("uid", 0);

                                    startActivity(new Intent(DataStore.this, Loginpage.class));
                                    finishAffinity();
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        else if (item.getItemId() == R.id.setting)
                        {
                            Toast.makeText(DataStore.this, "setting", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);

                            startActivity(intent);
                            finishAffinity();
                        }
                        else if (item.getItemId() == R.id.detail) {
                            Toast.makeText(DataStore.this, "Detail", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }
        });
    }
}