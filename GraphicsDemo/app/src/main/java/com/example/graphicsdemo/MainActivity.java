package com.example.graphicsdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        myView = new MyView(this);
        setContentView(myView);//사용자 정의 View를 화면에 표시

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "도형종류 선택");
//        menu.add(0, 2, 0, "선 두께 선택");
//        menu.add(0, 3, 0, "선 색상 선택");
//        menu.add(0, 4, 0, "면 색상 선택");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //if (item.getItemId() == 1) {
        if (item.getItemId() == R.id.select_shape_type) {
            new AlertDialog.Builder(this)
                    .setTitle("도형 종류 선택")
                    .setItems(shapeList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           switch (which) {
                               case 0 : myView.shapeType = Shape.ShapeType.Line; break;
                               case 1 : myView.shapeType = Shape.ShapeType.Oval; break;
                               case 2 : myView.shapeType = Shape.ShapeType.Rectangle; break;
                           }
                        }
                    })
                    .setNeutralButton("취소", null)
                    .show();
        //} else if (item.getItemId() == 2) {
        } else if (item.getItemId() == R.id.select_line_thickness) {
            (new AlertDialog.Builder(this))
                .setTitle("선 두께 선택")
                .setItems(thickness, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myView.thickness = Integer.parseInt(thickness[which]);

                    }
                })
                .setNegativeButton("취소", null)
                .show();

        //} else if (item.getItemId() == 3) {
        } else if (item.getItemId() == R.id.select_line_color) {
            (new AlertDialog.Builder(this))
                    .setTitle("선 색상 선택")
                    .setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    myView.strokeColor = Color.RED;
                                    break;
                                case 1:
                                    myView.strokeColor = Color.MAGENTA;
                                    break;
                                case 2:
                                    myView.strokeColor = Color.YELLOW;
                                    break;
                                case 3:
                                    myView.strokeColor = Color.GREEN;
                                    break;
                                case 4:
                                    myView.strokeColor = Color.BLUE;
                                    break;
                            }
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();

        //} else if (item.getItemId() == 4) {
        } else if (item.getItemId() == R.id.select_fill_color) {
            (new AlertDialog.Builder(this))
                .setTitle("면 색상 선택")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0 : myView.fillColor = Color.RED; break;
                            case 1 : myView.fillColor = Color.MAGENTA; break;
                            case 2 : myView.fillColor = Color.YELLOW; break;
                            case 3 : myView.fillColor = Color.GREEN; break;
                            case 4 : myView.fillColor = Color.BLUE; break;
                        }
                    }
                })
                .setNegativeButton("취소", null)
                .show();

        }

        return true;

    }

}











