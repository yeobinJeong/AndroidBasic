package com.example.graphicsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-24.
 */
public class MyView extends View {

    private List<PointF> points = new ArrayList<>();
    //touch up 이벤트가 발생했을 때 도형정보를 저장하는 변 수
    private List<Shape> shapes = new ArrayList();

    private Shape currentShape;

    private Shape.ShapeType shapeType = Shape.ShapeType.Line;
    private int thickness =3;
    private int strokeColor = Color.RED;
    private int fillColor = Color.GREEN;

    private Paint paint = new Paint();
    private GestureDetector gestureDetector;
    public MyView(Context context) {

        super(context);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public MyView(Context context, AttributeSet attrs){


        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas){



        for(Shape shape : shapes){
            shape.doDraw(canvas, paint);
        }


        if(currentShape != null){
            currentShape.doDraw(canvas, paint);
        }


       /* for(PointF p : points ){

            RectF rect = new RectF(p.x-50, p.y-50, p.x+50, p.y+50);



            paint.setColor(Color.RED);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawOval(rect, paint);


            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawOval(rect, paint);
        }*/




    }



    @Override
    public boolean onTouchEvent(MotionEvent event){

        gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            currentShape = new Shape();
            currentShape.thickness = thickness;
            currentShape.strokeColor = strokeColor;
            currentShape.fillColor= fillColor;
            currentShape.setPreview(true);
            currentShape.setShapeType(shapeType);
            currentShape.getPoints().add(new PointF(event.getX(), event.getY()));
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            List<PointF> points = currentShape.getPoints();
            if (points.size() == 1) {
                points.add(new PointF(event.getX(), event.getY()));
            } else {
                points.get(1).set(event.getX(), event.getY());
            }
            //미리보기 요청
            /*doDrawPreview();*/
            invalidate();

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currentShape.setPreview(false);
            shapes.add(currentShape);
            currentShape = null;
            //도형 그리기 요청
            /*doDraw();*/
            invalidate();
        }
        /*//사용자의 touch 위치를 따로 저 장 하 는 중 입 니 다
        PointF p = new PointF(event.getX(), event.getY());

        Shape shape = new Shape();
        points.add(p);

        invalidate();*/
        //하나의 Event에 move down up이 다 들어오는건데 그 다음 처리를 수행하세요 라는 뜻입니다
        //만약 false 값일 경우 이후의 이벤트는 생략하라는 얘기
        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {



        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            shapes.clear();
            return true;
        }
    }


}
