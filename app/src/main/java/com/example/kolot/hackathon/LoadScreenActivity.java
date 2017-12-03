package com.example.kolot.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class LoadScreenActivity extends AppCompatActivity {



    public static final String SP_NAME = "spName";
    public static final String SP_KEY_FIRST_START = "spKeyFirstStart";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        /*SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        boolean firstStart = sp.getBoolean(SP_KEY_FIRST_START, true);
        Intent intent = null;
        if(firstStart) {
            sp.edit().putBoolean(SP_KEY_FIRST_START, false).apply();
            intent = new Intent(this, InstructionsActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }*/


/*
       Intent intent= new Intent(this, MailSenderActivity.class);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        finish();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadScreenActivity.this,MainActivity.class));
                finish();
            }
        },2000);


    }
}