package com.androidexample.multiviewdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

public class MemoryGameFragment extends Fragment {

    final int PRE_START_GAME = 0;
    final int ON_WAITING = 1;
    final int ON_CHECKING = 2;
    final int ON_PREPARING = 3;

    Bitmap[] mCardImages = new Bitmap[16];//카드 이미지 배열
    Bitmap mBacksideImage;

    HashMap<ImageView, CardItem> mItems = new HashMap<>();

    Button mStartButton, mHomeButton;

    CardItem mFirstItem = null, mSecondItem = null;

    int mStatus = PRE_START_GAME;
    int mMatchingCount = 0;

    GameResult mGameResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity parentActivity = getActivity();

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_memory_game, null);

        mStartButton = (Button)rootView.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(startListener);

        mGameResult = new GameResult();

        //////////////////////////////////
        //카드 이미지 읽어서 변수에 저장
        AssetManager am = parentActivity.getAssets();//assets 폴더의 리소스를 관리하는 객체
        InputStream fis = null;
        String path = null;
        Bitmap bm = null;
        for (int i = 0; i < mCardImages.length; i++) {
            try {
                path = String.format("cards/card%d.png", (i + 1));
                fis = am.open(path);
                bm = BitmapFactory.decodeStream(fis);
                fis.close();
                mCardImages[i] = bm;
            } catch (Exception ex) {
            }
        }
        try {
            fis = am.open("cards/back1.png");
            mBacksideImage = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (Exception ex) {}

        //////////////////////////////////
        //초기 구성 -> 모든 ImageView를 backside 이미지로 설정
        TableLayout cardTable = (TableLayout)rootView.findViewById(R.id.card_table);
        for (int i = 0; i < cardTable.getChildCount(); i++) {
            TableRow row = (TableRow)cardTable.getChildAt(i);
            for (int j = 0;j < row.getChildCount(); j++) {
                ImageView cardImage = (ImageView)row.getChildAt(j);
                cardImage.setImageBitmap(mBacksideImage);
                CardItem cardItem = new CardItem();
                cardItem.setImageView(cardImage);
                cardImage.setOnTouchListener(mSelectListener);

                mItems.put(cardImage, cardItem);
            }
        }

        return rootView;

    }

    View.OnTouchListener mSelectListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            ImageView imageView = (ImageView)v;
            CardItem cardItem = mItems.get(v);

            if (mStatus == ON_WAITING && !cardItem.isOpened()) {

                cardItem.setOpened(true);
                imageView.setImageBitmap(mCardImages[cardItem.getImageIndex()]);

                if (mFirstItem == null) {
                    mFirstItem = cardItem;
                } else if (mSecondItem == null) {
                    mSecondItem = cardItem;
                    mStatus = ON_CHECKING;

                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        if (mFirstItem.getImageIndex() != mSecondItem.getImageIndex()) {
                            mFirstItem.getImageView().setImageBitmap(mBacksideImage);
                            mSecondItem.getImageView().setImageBitmap(mBacksideImage);
                            mFirstItem.setOpened(false);
                            mSecondItem.setOpened(false);
                            mStatus = ON_WAITING;
                        } else {
                            mMatchingCount++;
                            if (mMatchingCount == mItems.size() / 2) {
                                stopGame();
                                mGameResult.setSuccess(mGameResult.getSuccess() + 1);
                                Toast.makeText(getActivity(), "성공!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                mStatus = ON_WAITING;
                            }
                        }
                        mFirstItem = null;
                        mSecondItem = null;

                        }
                    }, 500);
                }
            }
        }

        return false;
        }
    };

    View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mStatus == ON_CHECKING  || mStatus == ON_PREPARING) return;

            if (mStatus != PRE_START_GAME) {
                (new AlertDialog.Builder(getActivity()).setTitle("게임을 중지할까요?")
                        .setNegativeButton("계속", null)
                        .setNeutralButton("중지", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopGame();
                            }
                        })
                        .setPositiveButton("다시시작", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGame();
                            }
                        })).show();
            } else {
                startGame();
            }
        }
    };

    private void stopGame() {
        mStatus = PRE_START_GAME;
        mMatchingCount = 0;
        mStartButton.setText("게임 시작");

        Set<ImageView> keys = mItems.keySet();
        for (ImageView key : keys) {
            ImageView iv = mItems.get(key).getImageView();
            iv.setImageBitmap(mBacksideImage);
        }

    }
    private void startGame() {

        mStatus = ON_PREPARING;
        mMatchingCount = 0;

        mStartButton.setText("게임 중지");

        int[] selectedImages = new int[mItems.size()];
        for (int i = 0; i < selectedImages.length / 2; i++) {
            selectedImages[i] = (int)(Math.random() * mCardImages.length);
            selectedImages[i + selectedImages.length / 2] = selectedImages[i];
            for (int j = 0; j < i; j++) {
                if (selectedImages[i] == selectedImages[j]) {
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            int index1 = (int)(Math.random() * mItems.size());
            int index2 = (int)(Math.random() * mItems.size());
            if (index1 == index2) {
                i--;
                continue;
            }
            int temp = selectedImages[index1];
            selectedImages[index1] = selectedImages[index2];
            selectedImages[index2] = temp;
        }

        Set<ImageView> keys = mItems.keySet();
        int i = 0;
        for (ImageView key : keys) {
            mItems.get(key).setImageIndex(selectedImages[i]);
            ImageView iv = mItems.get(key).getImageView();
            iv.setImageBitmap(mCardImages[selectedImages[i]]);
            mItems.get(key).setOpened(false);
            i++;
        }

        mStartButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                Set<ImageView> keys = mItems.keySet();
                for (ImageView key : keys) {
                    ImageView iv = mItems.get(key).getImageView();
                    iv.setImageBitmap(mBacksideImage);
                }

                mStatus = ON_WAITING;
            }
        }, 1000);
    }
}
