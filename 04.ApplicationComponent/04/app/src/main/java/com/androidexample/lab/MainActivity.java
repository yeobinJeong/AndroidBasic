package com.androidexample.lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

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
                /*Intent intent = new Intent();

                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:010-9992-6511"));*/

                /*Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo: 37.500366, 127.031167"));*/


                startActivity(intent);
            }
        });

        startMemoryGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoryGameActivity.class);
                intent.putExtra("data", "hello!!");
                intent.putExtra("data2", new Date());
                //activity 실행 요청
                /*startActivity(intent);*/
                startActivityForResult(intent, 1);
            }
        });


    }

    //이 액티비티에서 호출된 다른 Activity가 반환되면(종료) 자동으로 호출되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                super.onActivityResult(requestCode, resultCode, data);
                GameResult gameResult = (GameResult) data.getSerializableExtra("result");
                Toast.makeText(this, gameResult.getSuccess() + "", Toast.LENGTH_LONG).show();
            }
        }

    }
}
