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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button button, post, get;
    private EditText editText;
    private TextView vkId;
    private Api apiVk, apiMeetAllid;
    private CheckBox checkBox;
    private String access_token, myIdVK;
    private RegistrationBody body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonAdd);
        post = (Button) findViewById(R.id.buttonMeetAllPOST);
        get = (Button) findViewById(R.id.buttonMeetAllGET);
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

        initializeRetrofit();
        setButtonsListeners();

        body = new RegistrationBody();
        body.setFacebookid("facebookMax");
        body.setInstlogin("instikMax");
        body.setRandomkey("963");
        body.setUsername("Masik");
        body.setVkid("0000Max");

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
                //  Intent intent = new Intent(MainActivity.this, SetNumberActivity.class);
                //  startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setButtonsListeners() {
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
            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiMeetAllid.registerUser(body).enqueue(new Callback<MeetallId>() {
                        @Override
                        public void onResponse(Call<MeetallId> call, Response<MeetallId> response) {
                            Log.d("id", "successful");
                            Log.d("id", String.valueOf(response.body().getVkid())  + " ");
                            Log.d("id", String.valueOf(response.body().getRandomkey())  + " ");
                            Log.d("id", String.valueOf(response.body().getUsername()) + " ");
                            Log.d("id", String.valueOf(response.body().getInstlogin()) + " ");
                            Log.d("id", String.valueOf(response.body().getFacebookid()));
                        }

                        @Override
                        public void onFailure(Call<MeetallId> call, Throwable t) {
                            Log.d("id", t.getMessage());
                        }
                    });
                }
            });

            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiMeetAllid.getIds(1).enqueue(new Callback<MeetallId>() {
                        @Override
                        public void onResponse(Call<MeetallId> call, Response<MeetallId> response) {
                            Log.d("id", String.valueOf(response.body().getVkid())  + " ");
                            Log.d("id", String.valueOf(response.body().getRandomkey())  + " ");
                            Log.d("id", String.valueOf(response.body().getUsername()) + " ");
                            Log.d("id", String.valueOf(response.body().getInstlogin()) + " ");
                            Log.d("id", String.valueOf(response.body().getId()) + " ");
                            Log.d("id", String.valueOf(response.body().getFacebookid()));
                        }

                        @Override
                        public void onFailure(Call<MeetallId> call, Throwable t) {
                            Log.d("id", t.getMessage());

                        }
                    });
                }
            });
        }
    }

    private void initializeRetrofit() {
        Retrofit vkRetrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiVk = vkRetrofitBuilder.create(Api.class);

        Retrofit meetallRetrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://meetall.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiMeetAllid = meetallRetrofitBuilder.create(Api.class);
    }
}
