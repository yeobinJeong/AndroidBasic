package com.androidexample.lab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class MemoryGameActivity extends AppCompatActivity {



    //게임 상태를 구분하는 상수 선언
    private final int PRE_START_GAME = 0;
    private final int ON_WAITING = 1;
    private final int ON_CHECKING = 2;
    private final int ON_PREPARING = 3;

    //현재 게임 상태를 저장하는 변수
    private int mStatus = PRE_START_GAME;

    //사용자가 발견한 카드 갯수 저장하는 변수
    private int mMatchingCount;


    private Button mStartBtn;
    private Button mHomeBtn;

    private Bitmap[] mCardImages = new Bitmap[16];
    private Bitmap mBacksideImage;
    private TableLayout mCardTable;

    private HashMap<ImageView, CardItem> mItems = new HashMap<>();

    private CardItem mFirstItem, mSecondItem;
    private GameResult mGameResult = new GameResult();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory_game);

        mStartBtn = (Button)findViewById(R.id.start_button);
        mHomeBtn = (Button)findViewById(R.id.back_button);

        mStartBtn.setOnClickListener(mStartListener);

        mHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // 인텐트에 데이터를 저장하고 호출한 액티비티로 반환
                Intent intent = new Intent();
                intent.putExtra("result", mGameResult);
                setResult(Activity.RESULT_OK, intent);

                finish();

            }
        });

        //reosurce에서 image를 읽어서 bitmap 객체를 만들고 배열에 저장하는

        Bitmap bm = null;
        Resources resources = this.getResources();//Resource를 관리하는 객체

        for(int i=0; i<mCardImages.length; i++) {
            bm = BitmapFactory.decodeResource(resources, R.drawable.card1 + i);
            mCardImages[i] = bm;

        }

        bm = BitmapFactory.decodeResource(resources, R.drawable.back6);
        mBacksideImage = bm;

        //초기에 모든 imageview 를 일단 backsideimage 로 설정
        mCardTable = (TableLayout)findViewById(R.id.card_table);

        for(int i=0; i<mCardTable.getChildCount(); i++){
            TableRow row = (TableRow)mCardTable.getChildAt(i);

            for(int j=0; j<row.getChildCount(); j++){
                ImageView imageview = (ImageView)row.getChildAt(j);
                imageview.setImageBitmap(mBacksideImage);
                imageview.setOnTouchListener(mSeletListener);


                CardItem cardItem = new CardItem();
                cardItem.setImageView(imageview);
                mItems.put(imageview, cardItem);
            }
        }

        /////////이전 Activity 에서 넘어온 데이터를 읽기
        Intent intent = getIntent();
        String pdata = intent.getStringExtra("data");
        Date pdate = (Date)intent.getSerializableExtra("data2");


        Toast.makeText(this, pdata+"/"+pdate, Toast.LENGTH_LONG).show();



    }

    View.OnClickListener mStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(mStatus == ON_CHECKING || mStatus == ON_PREPARING) return;

            if(mStatus != PRE_START_GAME ){ // 게임이 진행중인경우
                //화면에 dialog를 표시하는 명령 ( 버튼을 포함 )
                new AlertDialog.Builder(MemoryGameActivity.this).setTitle("게임을 중지할까요")
                        .setNegativeButton("계속",null)
                        .setPositiveButton("중지", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                stopGame();
                            }
                        })
                        .setNeutralButton("다시시작", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startGame();
                            }
                        })
                        .show();
            }else {
                startGame();
            }
    };




    };

    private View.OnTouchListener mSeletListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //DOWN, UP, MOVE 중에서 DOWN인 경우에 아래 코드 수행
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView imageView = (ImageView)v;
                CardItem cardItem = mItems.get(v);

                if (mStatus == ON_WAITING && !cardItem.isOpened()) {

                    cardItem.setOpened(true);
                    imageView.setImageBitmap(mCardImages[cardItem.getImageIndex()]);

                    if (mFirstItem == null) {
                        mFirstItem = cardItem;
                    } else if (mSecondItem == null) {
                        //두 번째 이미지가 null일경우 이미지를 채우고
                        //비교해서 같은 이미지라면 점수를 올리는 식의
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
                                        Toast.makeText(getApplicationContext(), "성공!!!", Toast.LENGTH_SHORT).show();
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

    private void startGame(){

        mStatus = ON_PREPARING;
        mMatchingCount = 0;

        mStartBtn.setText("게임 중지");

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
        //일정한 시간이 경과한 후 지정된 코드를 실행하는 명령
        mStartBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                Set<ImageView> keys = mItems.keySet();
                for (ImageView key : keys) {
                    ImageView iv = mItems.get(key).getImageView();
                    iv.setImageBitmap(mBacksideImage);
                }

                mStatus = ON_WAITING;
            }
        }, 1500);
    }


    private void stopGame(){
        mStatus = PRE_START_GAME;
        mMatchingCount = 0;
        mStartBtn.setText("게임 시작");

        Set<ImageView> keys = mItems.keySet();
        for (ImageView key : keys) {
            ImageView iv = mItems.get(key).getImageView();
            iv.setImageBitmap(mBacksideImage);
        }

    }

}
