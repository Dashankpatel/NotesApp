package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Loginpage extends AppCompatActivity {

    Button sign_up, login, nnew;
    SignInButton google;
    TextInputEditText mail, pass;
    private FirebaseAuth mAuth;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginpage);
        mAuth = FirebaseAuth.getInstance();

        sign_up = findViewById(R.id.sign_up);
        login = findViewById(R.id.login);
        google = findViewById(R.id.bt_sign_in);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);

//        nnew = findViewById(R.id.nnew);
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
//
//
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
//
//
//        Store Data Show Karava mate
//        DatabaseReference myRef = database.getReference("user");
//        nnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ValueEventListener postListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        HashMap alldata = (HashMap<Object,Object>) dataSnapshot.getValue();
//                        for (Object data : alldata.values())
//                        {
//                            HashMap userdata = (HashMap<Object,Object>) data;
//
//                            Log.d("--+--", "onDataChange: "+userdata.get("name"));
//                            Log.d("--+--", "onDataChange: "+userdata.get("number"));
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.d("--+--", "loadPost:onCancelled", databaseError.toException());
//                    }
//                };
//                myRef.addValueEventListener(postListener);
//            }
//        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("478254033903-mh3pfhd3hn8bq8ai03j8o6e9tbnc47im.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(Loginpage.this, googleSignInOptions);


//         create account signup page ma java mate
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Loginpage.this, Signuppage.class));
                finishAffinity();
            }
        });


//        google sign in
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = googleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent, 100);

            }
        });


//        old account login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mail.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {

                    oldlogin(mail.getText().toString(), pass.getText().toString());

                } else {
                    Toast.makeText(Loginpage.this, "Please Enter Your Email-Id", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //    google sign in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
                                    displayToast("Firebase authentication successful");

                                    SplaceScreen.edit.putBoolean("status", true);
                                    SplaceScreen.edit.putInt("uid", 0);
                                    SplaceScreen.edit.apply();
                                    Intent i = new Intent(Loginpage.this, DataStore.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finishAffinity();

                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }

                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("--+--+--", "onActivityResult: " + signInAccountTask.getException());
                displayToast("fail" + signInAccountTask.getException());
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    //    old account login
    void oldlogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("-+-+-+-", "signInWithEmail:success");
                            Toast.makeText(Loginpage.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            SplaceScreen.edit.putBoolean("status", true);
                            SplaceScreen.edit.putInt("uid", 0);
                            SplaceScreen.edit.apply();

                            Intent i = new Intent(Loginpage.this, DataStore.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finishAffinity();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("-+-+-+-", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(Loginpage.this, "Please Enter Valid Email-Id",
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