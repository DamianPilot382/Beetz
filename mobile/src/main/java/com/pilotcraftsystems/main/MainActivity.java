package com.pilotcraftsystems.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.wearable.MessageEvent;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageReceiver m = new MessageReceiver(){
            @Override
            public void onMessageReceived(MessageEvent messageEvent) {
                super.onMessageReceived(messageEvent);
                findViewById(R.id.text);
            }
        };
    }



}
