package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class Edit extends AppCompatActivity {

    TextInputEditText name2, Description2;
    Button sve2, cncl2,whatsap,skype;
    FloatingActionButton pop2,bck;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        name2 = findViewById(R.id.name2);
        Description2 = findViewById(R.id.Description2);
        sve2 = findViewById(R.id.sve2);
        cncl2 = findViewById(R.id.cncl2);
        pop2 = findViewById(R.id.pop2);
        bck = findViewById(R.id.bck);
        whatsap = findViewById(R.id.whatsap);
        skype = findViewById(R.id.skype);

        String updatitle = getIntent().getStringExtra("Title");
        String updatedescrip = getIntent().getStringExtra("Description");
        String key = getIntent().getStringExtra("key");

        name2.setText(updatitle);
        Description2.setText(updatedescrip);

        sve2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = name2.getText().toString();
                String description = Description2.getText().toString();

                if (!title.isEmpty() && !description.isEmpty()) {

                    DatabaseReference myRef = database.getReference("user").child(key);

                    myRef.child("Title").setValue(title);
                    myRef.child("Description").setValue(description);

                    Intent i = new Intent(Edit.this, DataStore.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finishAffinity();

                } else {
                    Toast.makeText(Edit.this, "Please Fill A Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cncl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Edit.this, DataStore.class));
                finishAffinity();
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Edit.this, DataStore.class));
                finishAffinity();
            }
        });

        whatsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Whatsapp share
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String shareBody = "Title :- " + name2.getText().toString()
                            + "\n" +
                            "Description :- " + Description2.getText().toString();
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    Edit.this.startActivity(waIntent);
            }
        });

        skype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String shareBody2 = "Title :- " + name2.getText().toString()
                        + "\n" +
                        "Description :- " + Description2.getText().toString();
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody2);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);


            }
        });

        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pmenu2 = new PopupMenu(Edit.this, pop2);

                pmenu2.inflate(R.menu.mymenu2);
                pmenu2.show();

                pmenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.shre)
                        {
                            Toast.makeText(Edit.this, "Share", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            String shareBody = "Title :- "+name2.getText().toString()+"\n"+"Description :- "+Description2.getText().toString();
                            intent.setType("text/plain");
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            Edit.this.startActivity(Intent.createChooser(intent, ""));
                        }

                        else if (item.getItemId() == R.id.delete)
                        {
                            Dialog dialog = new Dialog(Edit.this);
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

                                    DatabaseReference myRef = database.getReference("user").child(key);
//
                                    myRef.removeValue();
                                    startActivity(new Intent(Edit.this, DataStore.class));
                                    finishAffinity();
                                }
                            });

                            no2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        }

                        return false;
                    }
                });

            }
        });

//        store data delete karava mate
//        nnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // store data  delete karav mate > key,value
//                DatabaseReference myRef = database.getReference("user").child("-OChq0InTnrZBu99aS52");
//
//                myRef.removeValue();
//            }
//        });


    }
}