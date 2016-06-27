package com.androidexample.multiviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LottoGameFragment extends Fragment {

    int[] imageIds = new int[45];
    Button mSelectButton, mHomeButton;
    LinearLayout mNumberContainer, mSelectedList;
    TextView[] mNumbers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity parentActivity = getActivity();
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_lotto_game, null);

        mSelectButton = (Button)rootView.findViewById(R.id.select_number_button);

        mNumberContainer = (LinearLayout)rootView.findViewById(R.id.number_container);

        mSelectedList = (LinearLayout)rootView.findViewById(R.id.selected_list);

        int count = mNumberContainer.getChildCount();

        mNumbers = new TextView[count];

        for (int i = 0; i < mNumbers.length; i++) {
            mNumbers[i] = (TextView)mNumberContainer.getChildAt(i);
        }

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
                LinearLayout numberSet = new LinearLayout(getActivity());
                numberSet.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i < numbers.length; i++) {
                    TextView tv = new TextView(getActivity());
                    tv.setText(String.valueOf(numbers[i]));
                    tv.setGravity(Gravity.CENTER);
                    mNumbers[i].setText(String.valueOf(numbers[i]));
                    numberSet.addView(tv, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                }
                mSelectedList.addView(numberSet, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
        });

        return rootView;

    }

}
