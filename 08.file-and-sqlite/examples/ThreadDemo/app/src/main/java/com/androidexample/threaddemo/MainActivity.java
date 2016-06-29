package com.androidexample.threaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (TextView)findViewById(R.id.message);


        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(){

                   public void run(){

                       try{
                            //여기서는 UI처리가 불가능함 Error뜸
                       }catch(Exception e){
                           Log.e("Thread error : ", e.getMessage());
                       }

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               try {
                                   Thread.sleep(500);
                                   message.setText("비동기 잡업 완료");
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
                           }
                       });

                       /*message.post(new Runnable() {
                           @Override
                           public void run() {
                               message.setText("비동기 잡업 완료");
                           }
                       });*/

                   }

                }).start();
            }

        });

        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            Thread.sleep(500);
                            message.setText("비동기작업2완료");
                        }
                        catch (Exception e){}
                    }
                })
                ).start();
            }
        });

        //메시지 통신기 (...과정을 통해 메시지가 전달되면 handleMessage가 호출됨)
        final Handler handler = new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 1){
                    message.setText("핸들러를 통해 메시지가 전달됨");
                }else if(msg.what == 2){
                    message.setText(String.format("%d", msg.what));
                }

                return false;
            }
        });
        Button btn3 = (Button)findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(){

                    public void run(){
                        try{
                            Thread.sleep(500);
                            handler.sendEmptyMessage(1);
                        }
                        catch (Exception e){}
                    }

                }).start();
            }
        });


        Button btn4 = (Button)findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(){

                    public void run(){
                        try{
                            Thread.sleep(500);
                            handler.sendEmptyMessage(2);
                        }
                        catch (Exception e){}
                    }

                }).start();
            }
        });

    }
}
