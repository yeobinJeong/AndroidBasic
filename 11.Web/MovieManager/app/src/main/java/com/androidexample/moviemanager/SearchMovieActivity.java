package com.androidexample.moviemanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchMovieActivity extends AppCompatActivity {

    final String REQUEST_URL = "http://openapi.naver.com/search?key=%s&query=%s&display=100&start=1&target=movie";
    final String API_KEY = "bf0d3a53c020bdcb5ae4620d2c7ed896";

    Button mSearchButton;
    EditText mSearchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        mSearchText = (EditText)findViewById(R.id.search_text);
        mSearchButton = (Button)findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //소프트웨어 키보드를 없애는 메소드
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchText.getWindowToken(),0);


                (new Thread() {
                    public void run() {
                        try {
                            String searchText = mSearchText.getText().toString();
                            String sUrl = String.format(REQUEST_URL, API_KEY, searchText);
                            URL url = new URL(sUrl);
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            int respCode = conn.getResponseCode();
                            if (respCode == HttpURLConnection.HTTP_OK) {//200
                                processResult(conn);
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();
            }
        });
    }

    private void processResult(HttpURLConnection conn) throws IOException {
        BufferedReader breader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        final TextView tv = (TextView) findViewById(R.id.textView);
        final StringBuilder sb = new StringBuilder();
        while (true) {
            String line = breader.readLine();
            if (line == null) break;
            sb.append(line + "\r\n");
        }
        breader.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(sb.toString());
            }
        });
    }
}
