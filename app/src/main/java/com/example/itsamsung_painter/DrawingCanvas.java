package com.example.itsamsung_painter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.LinkedList;

public class DrawingCanvas extends View {

    private Paint paint;
    private Path path;

    LinkedList<Paint> paintsList;
    LinkedList<Path> pathsList;

//    Режимы рисования
    boolean rectangleMode;
    boolean circleMode;
    boolean lineMode;

    public static int pathColor;

    public DrawingCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        Сохраняет все пути и краски
        pathsList = new LinkedList<>();
        paintsList = new LinkedList<>();

//        Объявляем связанные списки настраиваемых фигур
        Circle.circleList = new LinkedList<>();
        //Rectangle.rectangleList = new LinkedList<>();

        paint = new Paint();
        paint.setColor(pathColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        path = new Path();
    }

    @Override
    public void onDraw(Canvas canvas){

//        Обработчик путей ondraw
        if(pathsList.size() > 0){
            for(int i = 0; i < pathsList.size(); i++){
                canvas.drawPath(pathsList.get(i), paintsList.get(i));
                super.onDraw(canvas);
            }
        }

//        Обработчик путей Circle
        if(Circle.circleList.size() > 0){
            for(int i = 0; i < Circle.circleList.size(); i++){
                canvas.drawCircle(Circle.circleList.get(i).posX, Circle.circleList.get(i).posY, Circle.circleList.get(i).radius, paintsList.get(i));
                super.onDraw(canvas);
            }
        }

//        Попытка реализовать режим прямоугольника
//        if(Rectangle.rectangleList.size() > 0){
//            for(int i = 0; i < Rectangle.rectangleList.size(); i++){
//                canvas.drawRect();
//                super.onDraw(canvas);
//            }
//        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
//        Получает количество пальцев на экране
        int touchCounter = touchEvent.getPointerCount();

//        Обработчик компонентов линейного режима
        if(lineMode && pathsList.size() > 0){
            paint.setColor(pathColor);
            pathsList.getLast().lineTo(touchEvent.getX(), touchEvent.getY());
            paintsList.addLast(paint);

            invalidate();
        }else if(circleMode){

            paint.setColor(pathColor);
            Circle.circleList.add(new Circle(this.getContext(), touchEvent.getX(0), touchEvent.getY(0), 100 ));
            paintsList.addLast(paint);

            invalidate();
//            Один палец на экране (событие)
        } else if(touchCounter == 1){
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        paint.setColor(pathColor);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeJoin(Paint.Join.ROUND);
                        paint.setStrokeCap(Paint.Cap.ROUND);
                        paint.setStrokeWidth(10);

                        pathsList.addLast(path);
                        paintsList.addLast(paint);

                        //                Двигает кисть в область клика
                        pathsList.getLast().moveTo(touchEvent.getX(), touchEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //                От новой позиции к старой
                        pathsList.getLast().lineTo(touchEvent.getX(), touchEvent.getY());
                        //                Призыв к действию в очереди на onDraw
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
//                        Клик за пределы
                        paint = new Paint();
                        path = new Path();

                        break;
                }
//            Два пальца на экране

        }else if(touchCounter == 2){
                switch(touchEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        paint.setColor(pathColor);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeJoin(Paint.Join.ROUND);
                        paint.setStrokeCap(Paint.Cap.ROUND);
                        paint.setStrokeWidth(10);

                        pathsList.addLast(path);
                        paintsList.addLast(paint);

//                        Стартовая позиция первого пальца
                        pathsList.getLast().moveTo(touchEvent.getX(0), touchEvent.getY(0));
//                       Линия от стартового пальца ко второму
                        pathsList.getLast().lineTo(touchEvent.getX(1), touchEvent.getY(1));
                        invalidate();

                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Просто создает больше линий между точками в каждой точке движения
                        pathsList.getLast().moveTo(touchEvent.getX(0), touchEvent.getY(0));
                        pathsList.getLast().lineTo(touchEvent.getX(1), touchEvent.getY(1));
                        invalidate();

                        break;
                }
//                3 пальца на экране
            }else if(touchCounter == 3){
                switch (touchEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        paint.setColor(pathColor);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeJoin(Paint.Join.ROUND);
                        paint.setStrokeCap(Paint.Cap.ROUND);
                        paint.setStrokeWidth(10);

                        pathsList.addLast(path);
                        paintsList.addLast(paint);

//                        Стартовый палец
                        pathsList.getLast().moveTo(touchEvent.getX(0), touchEvent.getY(0));
//                       Линия от стартового ко второму
                        pathsList.getLast().lineTo(touchEvent.getX(1), touchEvent.getY(1));

                        pathsList.getLast().moveTo(touchEvent.getX(1), touchEvent.getY(1));
                        pathsList.getLast().lineTo(touchEvent.getX(2), touchEvent.getY(2));
                        invalidate();

                        break;
                    case MotionEvent.ACTION_MOVE:
//                      Просто создает больше линий между точками в каждой точке движения
                        pathsList.getLast().moveTo(touchEvent.getX(0), touchEvent.getY(0));
                        pathsList.getLast().lineTo(touchEvent.getX(1), touchEvent.getY(1));

                        pathsList.getLast().moveTo(touchEvent.getX(1), touchEvent.getY(1));
                        pathsList.getLast().lineTo(touchEvent.getX(2), touchEvent.getY(2));
                        invalidate();

                        break;
                }
            }

        return true;
    }
}
