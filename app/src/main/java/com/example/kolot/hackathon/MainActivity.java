package com.example.kolot.hackathon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button button, button2;
    private EditText editText;
    private TextView vkId;
    private Api apiVk, apiMeetAll;
    private CheckBox checkBox;
    private DBHelper dbHelper;
    private String access_token, myIdVK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox5);
        vkId = (TextView) findViewById(R.id.vkId);


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

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://meetall.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiMeetAll = retrofit1.create(Api.class);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                access_token = res.accessToken;
                myIdVK = res.userId;
                vkId.setText("My vk id: " + myIdVK);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainActivity.this, "Приложению необходимо разрешение, запустите приложение еще раз.", Toast.LENGTH_LONG).show();

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.setNumber:
                Intent intent = new Intent(MainActivity.this, SetNumberActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();



        if (VKSdk.wakeUpSession(this)) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    apiVk.getResponse(Integer.valueOf(editText.getText().toString()), access_token).enqueue(new Callback<ResponseApi>() {
                        @Override
                        public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                            Toast.makeText(MainActivity.this, response.body().getResponse(), Toast.LENGTH_LONG).show();
                            Log.d("access", access_token);
                        }

                        @Override
                        public void onFailure(Call<ResponseApi> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiMeetAll.getInfo().enqueue(new Callback<List<MeetAll>>() {
                        @Override
                        public void onResponse(Call<List<MeetAll>> call, Response<List<MeetAll>> response) {

                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append(response.body().get(0).getId());
                            stringBuilder1.append(" ");
                            stringBuilder1.append(response.body().get(0).getTitle());
                            stringBuilder1.append(" ");
                            stringBuilder1.append(response.body().get(0).getContent());
                            stringBuilder1.append(" ");
                            stringBuilder1.append(response.body().get(0).getCreatedAt());
                            stringBuilder1.append(" ");
                            stringBuilder1.append(response.body().get(0).getUpdatedAt());

                            Toast.makeText(MainActivity.this, stringBuilder1.toString(), Toast.LENGTH_LONG).show();
                            Log.d("meet", response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<List<MeetAll>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });
        }
    }
}
