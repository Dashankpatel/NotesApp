package com.example.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplaceScreen extends AppCompatActivity {

    static SharedPreferences sp;
    static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace_screen);

        sp = getSharedPreferences("myshare",MODE_PRIVATE);
        edit = sp.edit();

        Boolean in = sp.getBoolean("status",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (in)
                {
                    startActivity(new Intent(SplaceScreen.this,DataStore.class)
                            .putExtra("userid",sp.getInt("uid",0)));
                    finishAffinity();
                }
                else
                {
                    startActivity(new Intent(SplaceScreen.this, Loginpage.class));
                    finishAffinity();
                }

            }
        },2000);

    }
}