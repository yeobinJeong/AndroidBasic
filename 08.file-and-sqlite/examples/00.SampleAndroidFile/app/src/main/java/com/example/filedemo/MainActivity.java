package com.example.filedemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "내장 메모리에 파일 쓰기");
        menu.add(Menu.NONE, 2, Menu.NONE, "내장 메모리에서 파일 읽기");
        menu.add(Menu.NONE, 3, Menu.NONE, "SD 카드에 파일 쓰기");
        menu.add(Menu.NONE, 4, Menu.NONE, "SD 카드에서 파일 읽기");
        menu.add(Menu.NONE, 5, Menu.NONE, "Raw 폴더에서 파일 읽기");
        menu.add(Menu.NONE, 6, Menu.NONE, "Asset 폴더에서 파일 읽기");
        menu.add(Menu.NONE, 7, Menu.NONE, "공유 설정 파일에 쓰기");
        menu.add(Menu.NONE, 8, Menu.NONE, "공유 설정 파일에서 읽기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1 :
                try {
                    FileOutputStream fos =
                            openFileOutput("test.txt", Context.MODE_PRIVATE);
                    PrintStream ps = new PrintStream(fos);
                    ps.println("파일에 기록하는 데이터 1");
                    ps.println("파일에 기록하는 데이터 2");
                    ps.println("파일에 기록하는 데이터 3");
                    ps.close();
                    fos.close();
                } catch (Exception ex) {
                }
                break;
            case 2 :
                try {
                    FileInputStream fis = openFileInput("test.txt");
                    InputStreamReader reader = new InputStreamReader(fis);
                    BufferedReader breader = new BufferedReader(reader);
                    String message = "";
                    while (true) {
                        String temp = breader.readLine();
                        if (temp == null) break;
                        message += temp + "\r\n";
                    }
                    breader.close();
                    reader.close();
                    fis.close();

                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;
            case 3 :
                try {
                    File sdcardDir = Environment.getExternalStorageDirectory();
                    //File myDir = new File(sdcardDir, "mydir");
                    //boolean b = false;
                    //if (myDir.exists() == false) {
                    //    b = myDir.mkdirs();
                    //}

                    File myFile = new File(sdcardDir, "test2.txt");
                    FileOutputStream fos = new FileOutputStream(myFile);
                    PrintStream ps = new PrintStream(fos);
                    ps.println("파일에 기록하는 데이터 A");
                    ps.println("파일에 기록하는 데이터 B");
                    ps.println("파일에 기록하는 데이터 C");

                    ps.close();
                    fos.close();

                } catch (Exception ex) {
                    Log.e("file-error", ex.getMessage());
                }
                break;

            case 4:
                try {
                    File sdcardDir = Environment.getExternalStorageDirectory();
                    File myDir = new File(sdcardDir, "mydir");
                    if (myDir.exists() == false) break;

                    File myFile = new File(myDir, "test2.txt");

                    BufferedReader breader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(myFile)));
                    String message = "";
                    while (true) {
                        String temp = breader.readLine();
                        if (temp == null) break;
                        message += temp + "\r\n";
                    }

                    breader.close();
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }
                break;

            case 5 :
                try {
                    InputStream istream = getResources().openRawResource(R.raw.test3);
                    BufferedReader breader = new BufferedReader(
                            new InputStreamReader(istream));
                    String message = "";
                    while (true) {
                        String temp = breader.readLine();
                        if (temp == null) break;
                        message += temp + "\r\n";
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    breader.close();
                    istream.close();


                } catch (Exception ex) {

                }
                break;

            case 6:
                try {
                    InputStream istream = getAssets().open("asset-file.txt");
                    //InputStream istream = getAssets().open("subdir/sub-asset-file.txt");
                    BufferedReader breader = new BufferedReader(new InputStreamReader(istream));
                    String message = "";
                    while (true) {
                        String temp = breader.readLine();
                        if (temp == null) break;
                        message += temp + "\r\n";
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    breader.close();
                    istream.close();
                } catch (Exception ex) {
                }

                break;

            case 7 :
                SharedPreferences wpreferences = getPreferences(Activity.MODE_PRIVATE);
                //SharedPreferences wpreferences = getSharedPreferences("com.example.mypref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = wpreferences.edit();
                editor.putString("data1", "문자열 데이터");
                editor.putInt("data2", 10);
                editor.putFloat("data3", 123.4567f);
                editor.commit();
                Toast.makeText(this, "공유 저장소에 데이터를 기록했습니다.", Toast.LENGTH_SHORT).show();
                break;

            case 8 :
                SharedPreferences rpreferences = getPreferences(Activity.MODE_PRIVATE);
                //SharedPreferences rpreferences = getSharedPreferences("com.example.mypref", Activity.MODE_PRIVATE);
                String data1 = rpreferences.getString("data1", "default value");
                int data2 = rpreferences.getInt("data2", 0);
                float data3 = rpreferences.getFloat("data3", 0.0f);
                Toast.makeText(this, data1 + " / " + data2 + " / " + data3, Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
