package com.androidexample.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LottoGameActivity extends AppCompatActivity {

    int[] imageIds = new int[45];
    Button mSelectButton, mHomeButton, mNewButton;
    LinearLayout mNumberContainer, mSelectedList;
    TextView[] mNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_game);

        mSelectButton = (Button)findViewById(R.id.select_number_button);
        mHomeButton = (Button)findViewById(R.id.home_button);
        mNewButton = (Button)findViewById(R.id.new_button);

        mNumberContainer = (LinearLayout)findViewById(R.id.number_container);

        mSelectedList = (LinearLayout)findViewById(R.id.selected_list);

        int count = mNumberContainer.getChildCount();

        mNumbers = new TextView[count];

        for (int i = 0; i < mNumbers.length; i++) {
            mNumbers[i] = (TextView)mNumberContainer.getChildAt(i);
        }

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LottoGameActivity.this, LottoGameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] numbers = new int[6];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = (int) (Math.random() * 45) + 1;
                    for (int j = 0; j < i; j++) {
                        if (numbers[i] == numbers[j]) {
                            i--;
                            break;
                        }
                    }
                }
                LinearLayout numberSet = new LinearLayout(LottoGameActivity.this);
                numberSet.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i < numbers.length; i++) {
                    TextView tv = new TextView(LottoGameActivity.this);
                    tv.setText(String.valueOf(numbers[i]));
                    tv.setGravity(Gravity.CENTER);
                    mNumbers[i].setText(String.valueOf(numbers[i]));
                    numberSet.addView(tv, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                }
                mSelectedList.addView(numberSet, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
        });

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.e("lab02", "on-start");
    }
    @Override
    protected void onResume(){
        super.onResume();

        Log.e("lab02", "on-resume");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e("lab02", "on-stop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("lab02", "on-restart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("lab02", "on-pause");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("lab02", "on-destroy");
    }


}
