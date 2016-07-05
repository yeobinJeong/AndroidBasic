package com.androidexample.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mMessage;
    Button mGetRequestButton, mPostRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessage = (TextView)findViewById(R.id.message);

        mGetRequestButton = (Button)findViewById(R.id.get_request_button);
        mGetRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessage.setText("");
                (new Thread() {
                    public void run() {
                        try {
                            String qString = "name=디카프리오정&phone=google.com";
                            URL url =
                                    new URL("http://192.168.13.19:8088/server-web/get-request.action?" + qString);
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            InputStream istream = conn.getInputStream();//요청 + 응답수신

                            Gson gson = new Gson();

                            InputStreamReader reader =  new InputStreamReader(istream);

                            /*BufferedReader breader =
                                    new BufferedReader(new InputStreamReader(istream));*/
                            final Person person = gson.fromJson(reader, Person.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMessage.append("no: "+person.getNo()+"name: "+person.getName()+"email: "+ person.getEmail());
                                }
                            });

                            /*while (true) {
                                final String line = breader.readLine();
                                if (line == null) break;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        mMessage.append(line + "\r\n");
                                    }
                                });
                            }*/
                            /*breader.close();*/

                        } catch (Exception ex) {
                            //디버깅할 때 오류 메시지를 확인하기 위한 처리
                            throw new RuntimeException(ex);
                        }
                    }

                }).start();

            }
        });

        mPostRequestButton = (Button)findViewById(R.id.post_request_button);
        mPostRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessage.setText("");
                (new Thread() {
                    public void run() {
                        try {
                            String qString = "name=0sun&phone=12345";
                            URL url =
                                    new URL("http://192.168.13.19:8088/server-web/post-request.action");
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);//전송 메시지 본문에 데이터 쓰기 설정
                            conn.setDoInput(true);//수신 메시지 읽기 설정
                            conn.setRequestProperty(
                                    "Content-Type", "application/x-www-form-urlencoded");
                            //전송 데이터 쓰기
                            OutputStream ostream = conn.getOutputStream();
                            ostream.write(qString.getBytes("utf-8"));//한글일 경우 euc-kr
                            ostream.flush();//미전송 데이터 강제 전송
                            ostream.close();

                            //수신 데이터 읽기
                            InputStream istream = conn.getInputStream();//요청 + 응답수신
                            BufferedReader breader =
                                    new BufferedReader(new InputStreamReader(istream));
                            while (true) {
                                final String line = breader.readLine();
                                if (line == null) break;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        mMessage.append(line + "\r\n");
                                    }
                                });
                            }
                            breader.close();

                        } catch (Exception ex) {
                            //디버깅할 때 오류 메시지를 확인하기 위한 처리
                            throw new RuntimeException(ex);
                        }
                    }

                }).start();

            }
        });
    }
}




