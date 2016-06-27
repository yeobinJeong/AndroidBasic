package com.example.graphicsdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyView myView;

    String[] shapeList = { "Line", "Oval", "Rectangle", "FreeLine"  };
    String[] thickness = { "3", "5", "10", "20" };
    String[] colors = { "RED", "ORANGE", "YELLOW", "GREEN", "BLUE" };

    final int MENU_ITEM_SHAPE_TYPE = 1;
    final int MENU_ITEM_LINE_THICKNESS = 2;
    final int MENU_ITEM_FILL_COLOR = 3;
    final int MENU_ITEM_LINE_COLOR = 4;

    final int REQUEST_SELECT_FILL_COLOR = 1;
    final int REQUEST_SELECT_LINE_COLOR = 2;
    final int REQUEST_SELECT_PEN_THICKNESS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        //View root = View.inflate(this, R.layout.activity_main, null);//xml -> 객체화
        //setContentView(root);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.activity_main, null);
        setContentView(root);

        //myView = new MyView(this);
        //setContentView(myView);

        myView = (MyView)findViewById(R.id.myView);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.select_shape_type) {
            (new AlertDialog.Builder(this))
                    .setTitle("도형 종류 선택")
                    .setItems(shapeList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    myView.shapeType = Shape.ShapeType.Line;
                                    break;
                                case 1:
                                    myView.shapeType = Shape.ShapeType.Oval;
                                    break;
                                case 2:
                                    myView.shapeType = Shape.ShapeType.Rectangle;
                                    break;
                                case 3:
                                    myView.shapeType = Shape.ShapeType.FreeLine;
                                    break;
                            }
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();

        } else if (item.getItemId() == R.id.select_line_thickness) {
            Intent intent = new Intent(this, PenPaletteDialog.class);
            startActivityForResult(intent, REQUEST_SELECT_PEN_THICKNESS);
        } else if (item.getItemId() == R.id.select_line_color) {
            Intent intent = new Intent(this, ColorPaletteDialog.class);
            startActivityForResult(intent, REQUEST_SELECT_LINE_COLOR);
        } else if (item.getItemId() == R.id.select_fill_color) {
            Intent intent = new Intent(this, ColorPaletteDialog.class);
            startActivityForResult(intent, REQUEST_SELECT_FILL_COLOR);
        }

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SELECT_PEN_THICKNESS :
                if (resultCode == Activity.RESULT_OK) {
                    myView.thickness = data.getIntExtra("selected_pen", Shape.DEFAULT_PEN_THICKNESS);
                }
                break;
            case REQUEST_SELECT_LINE_COLOR:
                if (resultCode == Activity.RESULT_OK) {
                    myView.strokeColor = data.getIntExtra("selected_color", Shape.DEFAULT_LINE_COLOR);
                }
                break;
            case REQUEST_SELECT_FILL_COLOR:
                if (resultCode == Activity.RESULT_OK) {
                    myView.fillColor = data.getIntExtra("selected_color", Shape.DEFAULT_FILL_COLOR);
                }
                break;
        }

    }
}











