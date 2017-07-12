package com.example.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alison.rl on 29/06/2017.
 */

public class CustomView extends View {

    private Paint mPaintImage;
    private Paint mPaintPath;
    private Path mPath;
    private float mImageX, mImageY;
    private Bitmap image;
    private Bitmap image_canvas;
    private Canvas canvas;
    private Boolean on = false;


    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        mPaintImage = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setColor(Color.RED);
        mPaintPath.setStrokeWidth(15);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.cd18);
        image = getResizeBitmap(image);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (mImageX == 0f || mImageY == 0f) {
            mImageX = (getWidth() - image.getWidth()) / 2;
            mImageY = (getHeight() - image.getHeight()) / 2;
        }

        canvas.save();
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(image, mImageX, mImageY, mPaintImage);
        canvas.drawPath(mPath, mPaintPath);
        canvas.restore();

        image_canvas = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image_canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        int id = event.getActionIndex();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (on == true) {
                    return true;
                }
                else {
                    mPath.moveTo(event.getX(id), event.getY(id));
                    postInvalidate();
                    return true;
                }
            }


            case MotionEvent.ACTION_MOVE: {

                float mx = event.getX(id);
                float my = event.getY(id);

                if (on == true) {

                    if (mx >= mImageX && mx <= (mImageX + image.getWidth())) {
                        if (my >= mImageY && my <= (mImageY + image.getHeight())) {
                            mImageX = mx - (image.getWidth() / 2);
                            mImageY = my - (image.getHeight() / 2);
                            postInvalidate();
                            return false;
                        }
                    }
                } else {
                    mPath.lineTo(event.getX(), event.getY());
                    postInvalidate();
                    return false;
                }
            }
        }
        return value;
    }

    public void getBooleanClick(Boolean click) { on = click;}

    public Bitmap getResizeBitmap(Bitmap bitmap){
        float aspect_ratio = bitmap.getWidth()/bitmap.getHeight();
        int mImageWidth = 1300;
        int mImageHeight = Math.round(mImageWidth*aspect_ratio);
        bitmap = Bitmap.createScaledBitmap(bitmap,mImageWidth,mImageHeight,true);
        return bitmap.copy(Bitmap.Config.ARGB_8888,false);
    }

    public Bitmap getCanvasImage(){
        return image_canvas;
    }
}
