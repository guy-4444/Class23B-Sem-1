package com.guy.simplegraph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Deque;
import java.util.LinkedList;

public class GraphView extends View {

    private Paint paint;
    Context context;

    float width = 200;
    float height = 240;

    Deque<Float> data = new LinkedList<>();

    private float SZ = 48f;
    private float MN = 0;
    private float MX = 40;



    public void setRange(int min, int max) {
        MN = min;
        MX = max;
        invalidate();
    }

    public void setNumOfPoints(int sz) {
        SZ = sz;
        data = new LinkedList<>();
        consInit(context);
    }

    public void refreshData(Deque<Float> data) {
        this.data = data;
        float max = data.getFirst();
        float min = data.getFirst();
        for (Float f : data) {
            max = Math.max(max, f);
            min = Math.min(min, f);
        }



        float diff = max - min;
        float padd = diff / 10;

        MX = max + padd;
        MN = min - padd;

        invalidate();
    }
    public void newPoint(float value) {
        data.pollFirst();
        data.add(value);
    }

    public GraphView(Context context) {
        super(context);
        consInit(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.GraphView);
        int size = typedArray.getInt(R.styleable.GraphView_size, 50);
        SZ = size;
        consInit(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        consInit(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        consInit(context);
    }

    private void consInit(Context context) {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        this.context = context;

        for (int i = 0; i < SZ; i++) {
            data.add(0f);
        }
    }


    private float[] point(float x, float y, float xpad, float ypad) {
        return new float[]{
                xpad + (width / (SZ - 1)) * x,
                ypad + height - (height / ((MX - MN) - 1)) * (y - MN)
        };
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        width = getWidth() - (2 * xpad);
        height = getHeight() - (2 * ypad);

        paint.setColor(Color.GRAY);
        paint.setTextSize(50);
        //canvas.drawText("Bilal",45f,55f, paint);

//        float[][] points = new float[][]{
//                new float[]{0, 1},
//                new float[]{1, 3},
//                new float[]{2, 9},
//                new float[]{3, 4},
//                new float[]{4, 0},
//        };
//        for (int i = 0; i < points.length; i++) {
//            float[] point = point(points[i][0], points[i][1]);
//            xStopPointsLine1[runningIndex] = point[0];
//            yStopPointsLine1[runningIndex] = point[1];
//        }


        float[] xStopPointsLine1 = new float[(int) SZ];
        float[] yStopPointsLine1 = new float[(int) SZ];

        int runningIndex = 0;
        for (float value : data) {
            float[] point = point(runningIndex, value, xpad, ypad);
            xStopPointsLine1[runningIndex] = point[0];
            yStopPointsLine1[runningIndex] = point[1];
            runningIndex++;
        }



        //float[] xStopPointsLine1 = new float[]{0f,200.1f,450.5f,650f,850f};
        //float[] yStopPointsLine1 = new float[]{100f,380f,540f,400f,720f};
        //float[] xStopPointsLine2 = new float[]{20f,170.1f,350.5f,480f,650f};
        //float[] yStopPointsLine2 = new float[]{200f,480f,240f,600f,380f};

        final float POINT_RADIUS = 4f;
        final float LINE_WIDTH = 2f;
        for(int i=0; i<yStopPointsLine1.length; i++){
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(LINE_WIDTH);
            if(i==0){
                canvas.drawLine(xStopPointsLine1[i],yStopPointsLine1[i],xStopPointsLine1[i+1],yStopPointsLine1[i+1], paint);
                paint.setColor(Color.GREEN);
                //canvas.drawLine(xStopPointsLine2[i],yStopPointsLine2[i],xStopPointsLine2[i+1],yStopPointsLine2[i+1], paint);
                paint.setColor(Color.RED);
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], POINT_RADIUS, paint);
                paint.setColor(Color.GREEN);
                //canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12, paint);
            }
            else if(i>0 && i<yStopPointsLine1.length-1)
            {
                canvas.drawLine(xStopPointsLine1[i],yStopPointsLine1[i],xStopPointsLine1[i+1],yStopPointsLine1[i+1], paint);
                paint.setColor(Color.RED);
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], POINT_RADIUS, paint);
                paint.setColor(Color.GREEN);
                //canvas.drawLine(xStopPointsLine2[i],yStopPointsLine2[i],xStopPointsLine2[i+1],yStopPointsLine2[i+1], paint);
                paint.setColor(Color.GREEN);
                //canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12, paint);
            }
            else if(i == yStopPointsLine1.length-1){
                paint.setColor(Color.RED);
                canvas.drawCircle(xStopPointsLine1[i], yStopPointsLine1[i], POINT_RADIUS, paint);
                paint.setColor(Color.GREEN);
                //canvas.drawCircle(xStopPointsLine2[i], yStopPointsLine2[i], 12, paint);
            }
        }
    }
}
