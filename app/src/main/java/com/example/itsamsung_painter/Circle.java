package com.example.itsamsung_painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;


public class Circle extends View
{

    public static LinkedList<Circle> circleList;

    final Paint circlePaint;

    {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(40);
        circlePaint.setColor(DrawingCanvas.pathColor);
        circlePaint.setStyle(Paint.Style.FILL);
    }
    public float posX;
    public float posY;
    public float radius;

    public Circle(Context con, float xPos, float yPos, float radius)
    {
        super(con);
        this.posX = xPos;
        this.posY = yPos;
        this.radius = radius;
    }
}