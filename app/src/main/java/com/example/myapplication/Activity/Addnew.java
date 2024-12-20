package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Addnew extends AppCompatActivity {

    TextInputEditText name,Description;
    Button sve,cncle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);


        name = findViewById(R.id.name);
        Description = findViewById(R.id.Description);
        sve = findViewById(R.id.sve);
        cncle = findViewById(R.id.cncle);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        sve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // unique key sathe data store karava mate push key use
                DatabaseReference myRef = database.getReference("user").push();
                String key = myRef.getKey();

                myRef.child("Title").setValue(name.getText().toString());
                myRef.child("Description").setValue(Description.getText().toString());
                myRef.child("key").setValue(key);

                if (!name.getText().toString().isEmpty()
                        && !Description.getText().toString().isEmpty())
                {
                    Intent i = new Intent(Addnew.this, DataStore.class).putExtra("key",key);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finishAffinity();
                }
                else
                {
                    Toast.makeText(Addnew.this, "Please Fill A Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cncle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Addnew.this, DataStore.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finishAffinity();

            }
        });



        //        firebase ma data add , delete , show karava
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        unique key vagar data store karva mate
//        DatabaseReference myRef = database.getReference("user");
//
//        data Store karava mate
//        nnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                // unique key sathe data store karava mate push key use
//                DatabaseReference myRef = database.getReference("user").push();
//                // unique key leva mate
//                String key = myRef.getKey();
//
//                myRef.child("name").setValue("creative");
//                myRef.child("number").setValue("1478523698");
//                myRef.child("key").setValue(key);
//            }
//        });


    }
}