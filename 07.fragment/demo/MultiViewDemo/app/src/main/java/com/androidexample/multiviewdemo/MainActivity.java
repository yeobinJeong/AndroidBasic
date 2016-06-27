package com.androidexample.multiviewdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Fragment> fragments;

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.tabs);

        mTabLayout.addTab(mTabLayout.newTab().setText("로또 번호 추출기"));
        mTabLayout.addTab(mTabLayout.newTab().setText("기억력 게임"));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {

//        int position = mTabLayout.getSelectedTabPosition();
//        if (position == 1) {
//            MemoryGameFragment fragment = (MemoryGameFragment) fragments.get(position);
//            if (fragment.mStatus == fragment.ON_CHECKING || fragment.mStatus == fragment.ON_PREPARING)
//                return;
//
//            if (fragment.mStatus != fragment.PRE_START_GAME) {
//                (new AlertDialog.Builder(MainActivity.this)).setTitle("게임을 중지하고 종료할까요?")
//                        .setNegativeButton("계속", null)
//                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                MainActivity.super.onBackPressed();//호출되면 Activity finish
//                            }
//                        }).show();
//            } else {
//
//                super.onBackPressed();
//            }
//        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {

            if (fragments == null) {
                fragments = new ArrayList<>();

                fragments.add(new LottoGameFragment());
                fragments.add(new MemoryGameFragment());
            }

            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lotto Game";
                case 1:
                    return "Memory Game";
            }
            return null;
        }
    }
}
