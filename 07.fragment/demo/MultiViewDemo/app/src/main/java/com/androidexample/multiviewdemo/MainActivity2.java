package com.androidexample.multiviewdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    List<Fragment> fragments;

    FrameLayout mContainer;

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments = new ArrayList<>();
        fragments.add(new LottoGameFragment());
        fragments.add(new MemoryGameFragment());

        mContainer = (FrameLayout)findViewById(R.id.container2);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        //탭 항목 추가
        mTabLayout.addTab(mTabLayout.newTab().setText("로또 번호 추출기 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("기억력 게임 2"));

        //탭을 터치했을 때 호출되는 이벤트 처리기
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //현재 선택된 탭의 번호 읽기
                int index = tab.getPosition();

                //FragmentManager 객체
                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();//Fragment 관리 작업 시작
                transaction.replace(R.id.container2, fragments.get(index));
                transaction.commit();//관리 작업 종료 + 확정
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.container2, fragments.get(0)).commit();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        int position = mTabLayout.getSelectedTabPosition();
//        if (position == 1) {
//            MemoryGameFragment fragment = (MemoryGameFragment) fragments.get(position);
//            if (fragment.mStatus == fragment.ON_CHECKING || fragment.mStatus == fragment.ON_PREPARING)
//                return;
//
//            if (fragment.mStatus != fragment.PRE_START_GAME) {
//                (new AlertDialog.Builder(MainActivity2.this)).setTitle("게임을 중지하고 종료할까요?")
//                        .setNegativeButton("계속", null)
//                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                MainActivity2.super.onBackPressed();//호출되면 Activity finish
//                            }
//                        }).show();
//            } else {
//                super.onBackPressed();
//            }
//        } else{
//            super.onBackPressed();
//        }
    }
}
