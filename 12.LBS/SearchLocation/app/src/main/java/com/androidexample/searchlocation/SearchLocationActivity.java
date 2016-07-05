package com.androidexample.searchlocation;

import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SearchLocationActivity extends AppCompatActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        //하위 호완성 버전의 FragmentManager 반환
        FragmentManager manager = getSupportFragmentManager();
        //Layout에 포함된 Fragment 찾기
        SupportMapFragment fragment =
                (SupportMapFragment)manager.findFragmentById(R.id.map);
        //MapFragment에 Map 로딩
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

    }

    //////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        menu.add(0, 1, 0, "장소검색");
        menu.add(0, 2, 0, "지원하지 않는 메뉴");
        menu.add(0, 3, 0, "지원하지 않는 메뉴");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1 :
                searchLocation();
                break;
            case 2 : break;
            case 3 : break;
        }

        return true;
    }

    private void searchLocation() {

        //입력요소
        final EditText input = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("위치 검색")
                .setIcon(R.mipmap.ic_launcher)
                .setView(input)//다이얼로그에 입력요소 삽입
                .setNeutralButton("취소", null)
                .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String locationName = input.getText().toString();
                        (new Thread() {
                            public void run() {
                                final List<ThePoint> locations = getLocations(locationName);
                                //locations를 지도에 표시
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        showLocations(locations);
                                        Toast.makeText(getApplicationContext(),
                                                locations.size() + " 장소가 검색되었습니다.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .show();

    }

    private void showLocations(List<ThePoint> locations){
        map.clear();

        //첫 번째 검색 결과의 중심으로 이동랙
        ThePoint location = locations.get(0);


        LatLng latLng = new LatLng(location.getY(), location.getX());
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));


        //Marker를 만들기 위한 옵션들 설정 Setting
        for(ThePoint l : locations){
            MarkerOptions options = new MarkerOptions();

            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.location));
            options.title(l.getTitle());
            options.snippet(l.getDescription());

            options.position(new LatLng(l.getY(), l.getX()));
            //map에 Maker추가
            map.addMarker(options);
        }

    }

    private List<ThePoint> getLocations(String data) {

        ArrayList<ThePoint> points = new ArrayList<ThePoint>();

        try {
            String encodedData = URLEncoder.encode(data, "utf-8");
            String path =
                    String.format("http://openapi.naver.com/search?key=bf0d3a53c020bdcb5ae4620d2c7ed896&query=%s&target=local&start=1&display=100",
                            encodedData);

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream istream = conn.getInputStream();

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =
                    factory.newDocumentBuilder();
            Document doc = builder.parse(istream);
            istream.close();

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                ThePoint point = new ThePoint();
                //String title =
                point.setTitle(item.getFirstChild().getTextContent());
                Element link = (Element)item.getChildNodes().item(1);
                if (link.getTextContent().length() > 0)
                    point.setLink(link.getTextContent());
                //int x =
                point.setX(
                        Integer.parseInt(
                                item.getChildNodes().item(7).getTextContent())
                );
                //int y =
                point.setY(
                        Integer.parseInt(
                                item.getChildNodes().item(8).getTextContent())
                );

                PointF pt = getWGS84FromKTM(point.getX(), point.getY());

                if (pt != null) {
                    point.setLatitude(pt.x);
                    point.setLongitude(pt.y);
                }

                points.add(point);
            }
        } catch (Exception ex) {
            //Log.e("search", ex.getMessage());
            throw new RuntimeException(ex);
        }

        return points;
    }

    private PointF getWGS84FromKTM(int x, int y) {
        PointF point = null;

        try {
            String path = String.format(
                    "http://apis.daum.net/maps/transcoord?apikey=73248eefbf298d60250361436f0aa81c&x=%d&y=%d&fromCoord=KTM&toCoord=WGS84&out=xml",
                    x, y);
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream istream = conn.getInputStream();

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            istream.close();
            double longitude =
                    Double.parseDouble(
                            doc.getDocumentElement().getAttribute("x"));
            double latitude =
                    Double.parseDouble(
                            doc.getDocumentElement().getAttribute("y"));

            point = new PointF((float)latitude, (float)longitude);

        } catch (Exception ex) {
            Log.e("convert", ex.getMessage());
        }

        return point;
    }
}
