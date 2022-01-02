package com.example.topikappv2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class X_drawing extends View{
    private Paint paint;
    public X_drawing(Context context) {super(context);}

    protected void onDraw(Canvas canvas){

        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10.0f);

        paint.setColor(Color.RED);

        canvas.drawLine(15,15,115,115,paint);
        canvas.drawLine(115,15,15,115,paint);
    }
}