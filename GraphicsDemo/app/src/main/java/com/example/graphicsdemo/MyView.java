package com.example.graphicsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stu_t on 2016-06-24.
 */
public class MyView extends View {

    //Touchdown 좌표 + (Touchmove 좌표) + Touchup 좌표 저장하는 변수
    private List<PointF> points = new ArrayList<>();
    //Touchup 이벤트가 발생했을 때 도형정보를 저장하는 변수
    private List<Shape> shapes = new ArrayList<>();

    //현재 그리고 있는 도형 정보를 저장하는 변수
    private Shape currentShape;

    //현재 설정된 도형 정보
    Shape.ShapeType shapeType = Shape.ShapeType.Line;
    int thickness = 3;
    int strokeColor = Color.RED;
    int fillColor = Color.GREEN;

    //그리기 도구 객체
    Paint paint = new Paint();

    public MyView(Context context) {
        super(context);
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //화면이 갱신될 필요가 있다면 자동으로 호출되는 메서드
    @Override
    protected void onDraw(Canvas canvas) {

        //그리기 도구
        Paint paint = new Paint();

        for (Shape shape : shapes) {
            shape.doDraw(canvas, paint);//도형 그리기
        }

        if (currentShape != null) {
            currentShape.doDraw(canvas, paint); //미리 보기 그리기
        }

    }

    //이 뷰를 Touch했을 때 자동으로 호츨되는 메서드
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //첫 번째 좌표 저장
            currentShape = new Shape();
            currentShape.thickness = thickness;
            currentShape.strokeColor = strokeColor;
            currentShape.fillColor= fillColor;
            currentShape.setPreview(true);//미리보기 설정
            currentShape.setShapeType(shapeType);
            currentShape.getPoints().add(new PointF(event.getX(), event.getY()));
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            List<PointF> points = currentShape.getPoints();
            if (points.size() == 1) {
                points.add(new PointF(event.getX(), event.getY()));
            } else {
                points.get(1).set(event.getX(), event.getY());
            }
            invalidate();//다시 그리기
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currentShape.setPreview(false);
            shapes.add(currentShape);
            currentShape = null;
            invalidate();//다시 그리기
        }

        //return false; //이후에 발생하는 이벤트는 생략
        return true;//down, move, up 중 다음 이벤트 처리를 수행하세요
    }
}












