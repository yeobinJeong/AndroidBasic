package com.androidexample.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(Menu.NONE, 1, 1, "Search Location");
        menu.add(Menu.NONE, 2, 2, "Good time for us");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case 1:
                Intent intent = new Intent(this, SearchLocationActivity.class);
                startActivity(intent);
                break;

            case 2:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
