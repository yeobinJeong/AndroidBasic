package com.androidexample.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView startLottoGame = (ImageView)findViewById(R.id.start_lotto_game);
        ImageView startMemoryGame = (ImageView)findViewById(R.id.start_memory_game);


        startLottoGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LottoGameActivity.class);
                startActivity(intent);
            }
        });

        startMemoryGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoryGameActivity.class);
                startActivity(intent);
            }
        });

    }
}
