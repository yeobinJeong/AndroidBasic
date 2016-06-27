package com.androidexample.multiviewdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    List<Fragment> fragments;

    FrameLayout mContainer;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        fragments = new ArrayList<>();
        fragments.add(new LottoGameFragment());
        fragments.add(new MemoryGameFragment());

        mContainer = (FrameLayout)findViewById(R.id.container2);

       Button btnLotto = (Button)findViewById(R.id.btnLotto);
       btnLotto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container2, fragments.get(0))
                        .commit();
           }
       });
       Button btnMemory = (Button)findViewById(R.id.btnMemory);
       btnMemory.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.container2, fragments.get(1))
                       .commit();
           }
       });

        getSupportFragmentManager().beginTransaction().add(R.id.container2, fragments.get(0)).commit();
    }

    //사용자가 뒤로가기 버튼 (하드웨어, 소프트웨어)
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setTitle("종료")
                .setMessage("프로그램을 종료할까요?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity3.super.onBackPressed();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
