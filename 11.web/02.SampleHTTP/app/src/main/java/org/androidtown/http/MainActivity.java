package org.androidtown.http;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText input01;
    TextView txtMsg;

    public static String defaultUrl = "http://m.naver.com";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input01 = (EditText) findViewById(R.id.input01);
        if (input01 != null) {
            input01.setText(defaultUrl);
        }

        txtMsg = (TextView) findViewById(R.id.txtMsg);

        Button requestBtn = (Button) findViewById(R.id.requestBtn);
        if (requestBtn != null) {
            requestBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String urlStr = input01.getText().toString();

                    ConnectThread thread = new ConnectThread(urlStr);
                    thread.start();

                }
            });
        }

    }

    class ConnectThread extends Thread {
        String urlStr;

        public ConnectThread(String inStr) {
            urlStr = inStr;
        }

        public void run() {

            try {
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    public void run() {
                        txtMsg.setText(output);
                    }
                });

            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }

        private String request(String urlStr) {
            StringBuilder output = new StringBuilder();
            try {
                //URL 요청 대상 서버 경로 생성
                URL url = new URL(urlStr);
                //대상 서버로 연결
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    //연결 or 요청 설정
                    conn.setConnectTimeout(10000);//최대 대기시간
                    conn.setRequestMethod("GET");//요청 방식
                    conn.setDoInput(true); //전송 데이터가 있을 경우
                    conn.setDoOutput(true); // 응답 데이터가 있을 경우 // Stream을 기준으로 생각하면 됨
                    int resCode = conn.getResponseCode();
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                        String line = null;
                        while(true) {
                            line = reader.readLine();
                            if (line == null)  break;
                            output.append(line + "\n");
                        }
                        reader.close(); conn.disconnect();
                    }
                }
            } catch(Exception ex) {
                Log.e("SampleHTTP", "Exception in processing response.", ex);
                ex.printStackTrace();
            }
            return output.toString();
        }
    }

}
