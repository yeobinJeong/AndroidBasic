package com.androidexample.moviemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchImageActivity extends AppCompatActivity {

    Button mImageSearchButton;
    EditText mImageSearchText;
    GridView mImageGridView;

    ImageAdapter adapter;
    ArrayList<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_image);

        mImageSearchText = (EditText)findViewById(R.id.image_search_text);

        mImageSearchButton = (Button)findViewById(R.id.image_search_button);
        mImageSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mImageSearchText.getWindowToken(), 0);
                adapter.setViewSize(mImageGridView.getColumnWidth());

                Thread thread = new Thread() {
                    public void run() {
                        String apiKey = "73248eefbf298d60250361436f0aa81c";
                        String format = "json";
                        String searchText = mImageSearchText.getText().toString();
                        String urlString = String.format("https://apis.daum.net/search/image?apikey=%s&q=%s&result=20&output=%s", apiKey, searchText, format);

                        images.clear();

                        try {
                            URL url = new URL(urlString);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {

                                //2. JSON

                                InputStream is = conn.getInputStream();
                                InputStreamReader reader = new InputStreamReader(is);
                                JsonParser parser = new JsonParser();
                                JsonObject doc = parser.parse(reader).getAsJsonObject();
                                JsonObject root = doc.getAsJsonObject("channel");
                                JsonArray items = root.getAsJsonArray("item");

                                Gson gson = new Gson();
                                for (int i = 0; i <items.size(); i++) {
                                    JsonObject element = items.get(i).getAsJsonObject();
                                    Image image = new Image();

//                                    String title = element.getAsJsonPrimitive("title").getAsString();
//                                    image.setTitle(title.replace("<b>", "").replace("</b>", ""));
//                                    image.setLink(element.getAsJsonPrimitive("link").getAsString());
//                                    image.setImage(element.getAsJsonPrimitive("image").getAsString());
//                                    image.setThumbnail(element.getAsJsonPrimitive("thumbnail").getAsString());
//                                    image.setPubDate(element.getAsJsonPrimitive("pubDate").getAsString());
//                                    image.setWidth(element.getAsJsonPrimitive("width").getAsInt());
//                                    image.setHeight(element.getAsJsonPrimitive("height").getAsInt());
//                                    image.setCp(element.getAsJsonPrimitive("cp").getAsString());
//                                    images.add(image);

                                    Image img = gson.fromJson(element, Image.class);
                                    img.setTitle(img.getTitle().replace("&lt;b&gt;", "").replace("&lt;/b&gt;", ""));
                                    images.add(img);
                                }

                                //1. DOM
//                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                                factory.setIgnoringElementContentWhitespace(true);
//                                DocumentBuilder builder = factory.newDocumentBuilder();
//                                Document doc = builder.parse(conn.getInputStream());
//
//                                images.clear();
//
//                                NodeList list = doc.getElementsByTagName("item");//<item> 엘리먼트 요청
//
//                                for (int i = 0; i <list.getLength(); i++) {
//                                    Element element = (Element)list.item(i);
//                                    Image image = new Image();
//                                    NodeList children = element.getChildNodes();//하위 엘리먼트 목록 반환
//                                    String title = element.getElementsByTagName("title").item(0).getTextContent();
//                                    image.setTitle(title.replace("<b>", "").replace("</b>", ""));
//                                    image.setLink(element.getElementsByTagName("link").item(0).getTextContent());
//                                    image.setImage(element.getElementsByTagName("image").item(0).getTextContent());
//                                    image.setThumbnail(element.getElementsByTagName("thumbnail").item(0).getTextContent());
//                                    image.setPubDate(element.getElementsByTagName("pubDate").item(0).getTextContent());
//                                    image.setWidth(Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent()));
//                                    image.setHeight(Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent()));
//                                    image.setCp(element.getElementsByTagName("cp").item(0).getTextContent());
//                                    images.add(image);
//                                }


                                SearchImageActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

        images = new ArrayList<>();
        adapter = new ImageAdapter(images, -1, this);

        mImageGridView = (GridView)findViewById(R.id.imageGridView);
        mImageGridView.setAdapter(adapter);

        mImageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image = images.get(position);
                //String link = image.getLink();

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                //startActivity(intent);

                String imageUrl = image.getImage();
                Intent intent = new Intent(SearchImageActivity.this, ImageDisplayActivity.class);
                intent.putExtra("image_url", imageUrl);
                startActivity(intent);
            }
        });
    }
}
