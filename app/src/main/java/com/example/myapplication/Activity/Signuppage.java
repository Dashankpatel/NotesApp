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

public class Signuppage extends AppCompatActivity {

    Button login2, signup2;
    TextInputEditText email, crtpass, name, cnfpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        mAuth = FirebaseAuth.getInstance();

        login2 = findViewById(R.id.login2);
        signup2 = findViewById(R.id.signup2);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        crtpass = findViewById(R.id.crtpass);
        cnfpass = findViewById(R.id.cnfpass);


//        login page ma java
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Signuppage.this, Loginpage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finishAffinity();
            }
        });

//        account crate karava mate
        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() &&
                        !crtpass.getText().toString().isEmpty() && !cnfpass.getText().toString().isEmpty()) {

                    String pass1 = crtpass.getText().toString();
                    String pass2 = cnfpass.getText().toString();
                    String emailid = email.getText().toString();

//                    Email check
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                        email.setError("Please enter a valid email address");
                        return;
                    }

//                    password in 6 character
                    if (pass1.length() != 6 && pass2.length() != 6) {
                        Toast.makeText(Signuppage.this, "Please Enter Valid Character", Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    pass1 & pass2 are same
                    if (pass1.equals(pass2)) {
                        sign(emailid, pass1);

                        Intent i = new Intent(Signuppage.this, Loginpage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finishAffinity();
                    } else {
                        Toast.makeText(Signuppage.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signuppage.this, "please Enter Your Data", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Signuppage.this, "Authentication Completed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("--+-+--", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signuppage.this, "Authentication failed.",
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