package com.example.kolot.hackathon;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private Button button,login, add_contact;
    private EditText editText;
    private TextView vkId;
    private Api apiVk, apiMeetAllid;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;
    private String access_token, myIdVK;
    private RegistrationBody body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        login = (Button) findViewById(R.id.buttonMeetAllLOGIN);
        add_contact = (Button) findViewById(R.id.buttonMeetAllGET);
        editText = (EditText) findViewById(R.id.contact_id);
        checkBox = (CheckBox) findViewById(R.id.checkBox5);
        vkId = (TextView) findViewById(R.id.myIniqKey);

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
        body.setFacebookid("a");
        body.setInstlogin("a");
        body.setRandomkey("a");
        body.setUsername("a");


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
                body.setVkid(res.userId);
                myIdVK = res.userId;
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainActivity.this, "Приложению необходимо разрешение, запустите приложение еще раз.", Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setButtonsListeners() {
        if (VKSdk.wakeUpSession(this)) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
                    apiMeetAllid.registerUser(body).enqueue(new Callback<MeetallId>() {
                        @Override
                        public void onResponse(Call<MeetallId> call, Response<MeetallId> response) {

                                vkId.setText(String.valueOf(response.body().getId()));
                            Log.d("111", String.valueOf(response.body().getId()) + " ");

                            Log.d("111", "successful");
                            Log.d("111", String.valueOf(response.body().getVkid()) + " ");
                                Log.d("111", String.valueOf(response.body().getRandomkey()) + " ");
                                Log.d("111", String.valueOf(response.body().getUsername()) + " ");
                                Log.d("111", String.valueOf(response.body().getInstlogin()) + " ");
                                Log.d("111", String.valueOf(response.body().getFacebookid()));
                                login.setEnabled(false);
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<MeetallId> call, Throwable t) {
                            Log.d("id", t.getMessage());
                        }
                    });
                }
            });

            add_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!editText.getText().toString().isEmpty())
                    apiMeetAllid.getIds(Integer.valueOf(editText.getText().toString())).enqueue(new Callback<MeetallId>() {
                        @Override
                        public void onResponse(Call<MeetallId> call, Response<MeetallId> response) {

                           apiVk.getResponse(Integer.valueOf(response.body().getVkid()), access_token).enqueue(new Callback<ResponseApi>() {
                               @Override
                               public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {

                               }

                               @Override
                               public void onFailure(Call<ResponseApi> call, Throwable t) {

                               }
                           });
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