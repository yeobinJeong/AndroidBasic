package com.androidexample.moviemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//메뉴 생성 이벤트 메서드
        menu.add(Menu.NONE, 0, 0, "영화검색");
        menu.add(Menu.NONE, 1, 1, "이미지검색");
        menu.add(Menu.NONE, 2, 2, "파일지우기");

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//메뉴 항목 선택 이벤트 메서드
        Intent intent;
        switch (item.getItemId()) {
            case 0 :
                //영화 검색 화면으로 이동
                intent = new Intent(this, SearchMovieActivity.class);
                startActivity(intent);
                break;
            case 1 :
                //이미지 검색 화면으로 이동
                intent = new Intent(this, SearchImageActivity.class);
                startActivity(intent);
                break;
            case 2 :
                new AlertDialog.Builder(this)
                        .setTitle("파일 삭제")
                        .setMessage("다운로드된 이미지를 삭제할까요?")
                        .setNegativeButton("아니요", null)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(Environment.getExternalStorageDirectory(), "lazy-list");
                                if (file.exists()) {
                                    File[] files = file.listFiles();
                                    for (File f : files) {
                                        f.delete();
                                    }
                                }
                            }
                        }).show();
                //Image Cache에 저장된 파일 지우기

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
