package com.example.itsamsung_painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;


public class Rectangle extends View
{

    public static LinkedList<Circle> rectangleList;

    final Paint rectanglePaint;

    {
        rectanglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setStyle(Paint.Style.STROKE);
        rectanglePaint.setStrokeWidth(40);
        rectanglePaint.setColor(DrawingCanvas.pathColor);
        rectanglePaint.setStyle(Paint.Style.FILL);
    }
    public float posX;
    public float posY;
    public float xPos;
    public float yPos;
    public float radius;

    public Rectangle(Context con, float xPos, float yPos, float posX, float posY)
    {
        super(con);
        this.posX = posX;
        this.posY = posY;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}