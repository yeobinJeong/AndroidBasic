package com.example.graphicsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-06-24.
 */
public class MyView extends View {



    public MyView(Context context) {

        super(context);
    }

    public MyView(Context context, AttributeSet attrs){


        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){

        Paint paint = new Paint();





        for(PointF p : points ){

            RectF rect = new RectF(p.x-50, p.y-50, p.x+50, p.y+50);



            paint.setColor(Color.RED);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawOval(rect, paint);


            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawOval(rect, paint);
        }




    }

    private List<PointF> points = new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event){

        //사용자의 touch 위치를 따로 저 장 하 는 중 입 니 다
        PointF p = new PointF(event.getX(), event.getY());
        points.add(p);

        invalidate();

        return super.onTouchEvent(event);
    }


}
