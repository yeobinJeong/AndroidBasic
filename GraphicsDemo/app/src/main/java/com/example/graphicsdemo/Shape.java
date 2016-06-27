package com.example.graphicsdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

public class Shape {

    static final int DEFAULT_PEN_THICKNESS = 3;
    static final int DEFAULT_LINE_COLOR = Color.BLACK;
    static final int DEFAULT_FILL_COLOR = Color.WHITE;

    public enum ShapeType {
        Line, Oval, Rectangle, FreeLine
    }

    //도형의 좌표 정보
    private ArrayList<PointF> points = new ArrayList<>();

    //도형종류를 저장하는 변수
    private ShapeType shapeType;
    //선두께를 저장하는 변수
    int thickness = DEFAULT_PEN_THICKNESS;
    //선색상을 저장하는 변수
    int strokeColor = DEFAULT_LINE_COLOR;
    //면색상을 저장하는 변수
    int fillColor = DEFAULT_FILL_COLOR;

    private boolean preview;
    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public ArrayList<PointF> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointF> points) {
        this.points = points;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    //저장된 도형 및 그리기 정보를 이용해서 그리기 수행하는 메서드
    public void doDraw(Canvas canvas, Paint paint) {

        if (points.size() < 2) return;

        RectF rect = new RectF();
        setRect(rect);//점의 위치를 이용해서 사각형 구성

        if (shapeType == ShapeType.Line) {

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setColor(strokeColor);

            canvas.drawLine(points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y, paint);

        } else if (shapeType == ShapeType.Oval) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawOval(rect, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setColor(strokeColor);
            canvas.drawOval(rect, paint);
        } else if (shapeType == ShapeType.Rectangle) {

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawRect(rect, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setColor(strokeColor);
            canvas.drawRect(rect, paint);
        }
    }

    public void showPreview(Canvas canvas, Paint paint) {
        if (points.size() < 2) return;

        RectF rect = new RectF();
        setRect(rect);

        if (shapeType == ShapeType.Line) {

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
            paint.setColor(strokeColor);

            canvas.drawLine(points.get(0).x, points.get(0).y,
                    points.get(1).x, points.get(1).y, paint);

        } else if (shapeType == ShapeType.Oval) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
            paint.setColor(strokeColor);
            canvas.drawOval(rect, paint);
        } else if (shapeType == ShapeType.Rectangle) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(thickness);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
            paint.setColor(strokeColor);
            canvas.drawRect(rect, paint);
        }
    }

    private void setRect(RectF rect) {
        rect.set(
            points.get(0).x < points.get(1).x ? points.get(0).x : points.get(1).x,
            points.get(0).y < points.get(1).y ? points.get(0).y : points.get(1).y,
            points.get(0).x > points.get(1).x ? points.get(0).x : points.get(1).x,
            points.get(0).y > points.get(1).y ? points.get(0).y : points.get(1).y);
    }


}
