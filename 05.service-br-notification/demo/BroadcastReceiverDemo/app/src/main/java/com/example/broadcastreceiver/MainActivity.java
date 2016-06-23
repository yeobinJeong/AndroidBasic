package com.example.broadcastreceiver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyReceiver receiver = new MyReceiver();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, null);
    }
}
