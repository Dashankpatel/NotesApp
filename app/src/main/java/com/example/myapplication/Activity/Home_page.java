package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_page extends AppCompatActivity {

    Button login2,signup2;
    TextInputEditText name,crtpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();

        login2 = findViewById(R.id.login2);
        signup2 = findViewById(R.id.signup2);
        name = findViewById(R.id.name);
        crtpass = findViewById(R.id.crtpass);


//        login page ma java
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home_page.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finishAffinity();
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sign(name.getText().toString(),crtpass.getText().toString());

                if (!name.getText().toString().isEmpty() && !crtpass.getText().toString().isEmpty())
                {
                    sign(name.getText().toString(),crtpass.getText().toString());

                    Intent i = new Intent(Home_page.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finishAffinity();

                }
                else
                {
                    Toast.makeText(Home_page.this, "please Enter Your Data", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    //    create account
    void sign(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("--+-+--", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Home_page.this, "Authentication Completed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("--+-+--", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Home_page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }
    }


}