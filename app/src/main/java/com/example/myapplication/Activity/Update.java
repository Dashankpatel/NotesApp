package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update extends AppCompatActivity {

    TextInputEditText name2, Description2;
    Button sve2, cncl2;
    FloatingActionButton pop2,bck;
    private FirebaseAuth mAuth;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        name2 = findViewById(R.id.name2);
        Description2 = findViewById(R.id.Description2);
        sve2 = findViewById(R.id.sve2);
        cncl2 = findViewById(R.id.cncl2);
        pop2 = findViewById(R.id.pop2);
        bck = findViewById(R.id.bck);

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

                    Intent i = new Intent(Update.this, DataStore.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finishAffinity();

                } else {
                    Toast.makeText(Update.this, "Please Fill A Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cncl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Update.this, DataStore.class));
                finishAffinity();
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Update.this, DataStore.class));
                finishAffinity();
            }
        });

        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pmenu2 = new PopupMenu(Update.this, pop2);

                pmenu2.inflate(R.menu.mymenu2);
                pmenu2.show();

                pmenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.shre)
                        {
                            Toast.makeText(Update.this, "Share", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            String shareBody = "Title :- "+name2.getText().toString()+"\n"+"Description :- "+Description2.getText().toString();
                            intent.setType("text/plain");
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            Update.this.startActivity(Intent.createChooser(intent, ""));
                        }

                        else if (item.getItemId() == R.id.delete)
                        {
                            Dialog dialog = new Dialog(Update.this);
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
                                    startActivity(new Intent(Update.this, DataStore.class));
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