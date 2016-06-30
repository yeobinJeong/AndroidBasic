package com.androidexample.moviemanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SearchMovieActivity extends AppCompatActivity {

    final String REQUEST_URL = "http://openapi.naver.com/search?key=%s&query=%s&display=100&start=1&target=movie";
    final String API_KEY = "bf0d3a53c020bdcb5ae4620d2c7ed896";

    Button mSearchButton;
    EditText mSearchText;

    ArrayList<Movie> movies;
    MovieAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        listView = (ListView)findViewById(R.id.listView);
        movies = new ArrayList<>();
        adapter = new MovieAdapter(this, movies);
        listView.setAdapter(adapter);

        mSearchText = (EditText)findViewById(R.id.search_text);
        mSearchButton = (Button)findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //소프트웨어 키보드 숨기는 코드
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);

                (new Thread() {
                    public void run() {
                        try {
                            String searchText = mSearchText.getText().toString();
                            String sUrl = String.format(REQUEST_URL, API_KEY, searchText);
                            URL url = new URL(sUrl);
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            int respCode = conn.getResponseCode();
                            if (respCode == HttpURLConnection.HTTP_OK) {//200
                                //processResult(conn);
                                processResult2(conn);
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = movies.get(position);

                String url = movie.getLink();

                if(url != null && url.length() >0){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

                }else{
                    Toast.makeText(SearchMovieActivity.this, "관련 사이트가 존재하지 않습니다", Toast.LENGTH_LONG).show();
                }

            }
        });
    } // end of onCreate

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



    private void processResult2(HttpURLConnection conn) throws IOException {
        InputStream istream = conn.getInputStream();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(istream);//XML 읽기 -> 객체 트리 생성

            movies.clear();

            NodeList list = doc.getElementsByTagName("item");
            for (int i = 0; i <list.getLength(); i++) {
                Element element = (Element)list.item(i);
                Movie movie = new Movie();
                NodeList children = element.getChildNodes();//하위 엘리먼트 목록 반환
                String title = children.item(0).getTextContent();
                movie.setTitle(title.replace("<b>", "").replace("</b>", ""));
                movie.setLink(children.item(1).getTextContent());
                movie.setImage(children.item(2).getTextContent());
                movie.setSubtitle(children.item(3).getTextContent());
                movie.setPubDate(children.item(4).getTextContent());
                movie.setDirector(children.item(5).getTextContent());
                movie.setActor(children.item(6).getTextContent());
                movie.setUserRating(Float.parseFloat(element.getChildNodes().item(7).getTextContent()));
                movies.add(movie);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                }
            });

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
