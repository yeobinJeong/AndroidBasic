package com.androidexample.moviemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

                /*intent = new Intent(this, ImageLoader.class);*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
