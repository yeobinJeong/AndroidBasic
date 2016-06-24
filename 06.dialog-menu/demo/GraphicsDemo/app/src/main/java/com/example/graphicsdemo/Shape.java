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

    private ArrayList<PointF> points = new ArrayList<>();
    private ShapeType shapeType;

    int thickness = DEFAULT_PEN_THICKNESS;
    int strokeColor = DEFAULT_LINE_COLOR;
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

    public void doDraw(Canvas canvas, Paint paint) {

        if (points.size() < 2) return;

        RectF rect = new RectF();
        setRect(rect);

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
                // 작은X와 작은Y를 Left top 큰 X와 큰 Y를 Right bottom 으로 설정하기위한 set
            points.get(0).x < points.get(1).x ? points.get(0).x : points.get(1).x,
            points.get(0).y < points.get(1).y ? points.get(0).y : points.get(1).y,
            points.get(0).x > points.get(1).x ? points.get(0).x : points.get(1).x,
            points.get(0).y > points.get(1).y ? points.get(0).y : points.get(1).y);
    }


}
