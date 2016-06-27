package com.example.graphicsdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyView extends View {

    final int BACKGROUND_COLOR = Color.WHITE;

    ArrayList<PointF> points = new ArrayList<>();
    ArrayList<Shape> shapes = new ArrayList<>();//그려진 도형 목록
    Shape currentShape;//현재 그리고 있는 도형 정보 저장

    Shape.ShapeType shapeType = Shape.ShapeType.Line;
    int thickness = 3;
    int strokeColor = Color.RED;
    int fillColor= Color.GREEN;

    Paint paint = new Paint();

    Bitmap mBitmap;
    Canvas mCanvas;

    public MyView(Context context){
        super(context);
    }

    //layout에서 사용될 때 호출되는 생성자 메서드
    public MyView(Context context, AttributeSet set){
        super(context, set);
    }

    //화면이 다시 그려져야 할 경우 호출되는 메서드
    @Override
    protected void onDraw(Canvas canvas) {

        paint.reset();
        paint.setAntiAlias(true);

        for (Shape shape : shapes) {
            shape.doDraw(canvas, paint);
        }

//        if (mBitmap != null) {
//            canvas.drawBitmap(mBitmap, 0, 0, null);
//        }

        if (currentShape != null) {
            currentShape.showPreview(canvas, paint);//guideline 그리기
        }

        super.onDraw(canvas);
    }

    //View를 상속한 클래스에서 이벤트 처리는 재정의 메서드를 통해서 구현하는 것이 좋습니다.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //첫 번째 좌표 저장
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
            doDrawPreview();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currentShape.setPreview(false);
            shapes.add(currentShape);
            currentShape = null;
            doDraw();
        }

        return true;
    }

    private void doDraw() {
        paint.reset();
        paint.setAntiAlias(true);

        for (Shape shape : shapes) {
            shape.doDraw(mCanvas, paint);
        }
        invalidate();
    }

    private void doDrawPreview() {
        invalidate();
    }

    ////////////////////////////////////////////////////////

    public void newImage(int width, int height)
    {
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);

        mBitmap = img;
        mCanvas = canvas;

        drawBackground(mCanvas);

        invalidate();
    }

    public void setImage(Bitmap newImage)
    {
        setImageSize(newImage.getWidth(),newImage.getHeight(),newImage);
        invalidate();
    }

    public void setImageSize(int width, int height, Bitmap newImage)
    {
        if (mBitmap != null){
            if (width < mBitmap.getWidth()) width = mBitmap.getWidth();
            if (height < mBitmap.getHeight()) height = mBitmap.getHeight();
        }

        if (width < 1 || height < 1) return;

        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        drawBackground(canvas);

        if (newImage != null) {
            canvas.setBitmap(newImage);
        }

        if (mBitmap != null) {
            mBitmap.recycle();
            mCanvas.restore();
        }

        mBitmap = img;
        mCanvas = canvas;

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (w > 0 && h > 0) {
            newImage(w, h);
        }

    }

    public void drawBackground(Canvas canvas)
    {
        if (canvas != null) {
            canvas.drawColor(BACKGROUND_COLOR);
        }
    }

}
