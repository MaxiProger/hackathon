package com.example.kolot.hackathon.mailSender;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kolot.hackathon.R;

public class MailSenderActivity extends AppCompatActivity {

    private static final String emailadres="eningapps@gmail.com";
    private static final String topic="test";
    private static final String content="vsdsdvdfvfdv";




    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in);
        Button buttonSend = (Button) findViewById(R.id.sendButton);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailadres});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, topic);
                emailIntent.putExtra(Intent.EXTRA_TEXT, content);
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });
    }
}
