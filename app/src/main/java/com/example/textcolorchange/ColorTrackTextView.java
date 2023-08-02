package com.example.textcolorchange;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Scanner;

public class ColorTrackTextView extends AppCompatTextView {

    private Paint mOriginPaint;
    private Paint mChangePaint;
    private float mCurrentProgress = 0f;
    private Direction mDirection;
    public enum Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }


    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mChangePaint = getPaintByColor(changeColor);
        mOriginPaint = getPaintByColor(originColor);

        array.recycle();
    }

    public Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        //防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //根据进度把中间值算出来
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection== Direction.LEFT_TO_RIGHT) {
            //绘制变色的
            drawText(canvas,  mChangePaint, 0, middle);
            //绘制不变色的
            drawText(canvas, mOriginPaint, middle, getWidth());
        }
        else {
            //绘制变色的
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            //绘制不变色的

            drawText(canvas,  mOriginPaint, 0, getWidth()-middle);
        }
    }

    /**
     * 绘制文字
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(@NonNull Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //  裁剪矩形
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        //字体宽度
        Rect bound = new Rect();
        mOriginPaint.getTextBounds(text, 0, text.length(), bound);
        // x是字体从哪里开始画
        int x = getWidth() / 2 - bound.width() / 2;
        Paint.FontMetrics metrics = mOriginPaint.getFontMetrics();
        int dy = (int) ((metrics.bottom - metrics.top) / 2 - metrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.mDirection = direction;
    }

    public void setCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

    public void setChangeColor(int changeColor) {
        mChangePaint.setColor(changeColor);
    }

    public void setOriginColor(int originColor) {
        mOriginPaint.setColor(originColor);
    }

}
