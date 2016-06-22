package com.androidexample.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MemoryGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory_game);

        Button startBtn = (Button)findViewById(R.id.start_button);
        Button homeBtn = (Button)findViewById(R.id.home_button)


    }


}
