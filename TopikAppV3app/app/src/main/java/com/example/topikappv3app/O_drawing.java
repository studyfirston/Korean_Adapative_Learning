package com.example.topikappv3app;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class O_drawing extends View {

    private Paint paint;
    public O_drawing(Context context) {super(context);}

    protected void onDraw(Canvas canvas){

        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10.0f);

        paint.setColor(Color.RED);

        canvas.drawCircle(50,50,50,paint);

    }
}