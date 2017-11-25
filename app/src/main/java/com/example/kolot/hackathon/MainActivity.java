package com.example.kolot.hackathon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vk.sdk.VKSdk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private Api apiVk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 1);

            }

        VKSdk.login(MainActivity.this, "friends");


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        apiVk = retrofit.create(Api.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiVk.getResponse(Integer.valueOf(editText.getText().toString())).enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                         Toast.makeText(MainActivity.this, response.body().getResponse(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

    }
}
