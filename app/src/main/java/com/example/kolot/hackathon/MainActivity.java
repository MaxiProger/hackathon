package com.example.kolot.hackathon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 1);

            }

        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());

        StringBuilder stringBuilder = new StringBuilder();

        for(String s : fingerprints){
            stringBuilder.append(s);
        }

        VKSdk.login(this, "friends");
        Log.d("tag", stringBuilder.toString());
    }
}
