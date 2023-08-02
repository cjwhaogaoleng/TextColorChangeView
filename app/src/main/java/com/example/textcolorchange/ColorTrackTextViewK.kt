package com.example.textcolorchange

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

    class ColorTrackTextViewK  constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) :
        AppCompatTextView(context, attrs, defStyleAttr) {
        private var mOriginPaint: Paint? = null
        private var mChangePaint: Paint? = null
        private var mCurrentProgress = 0f
        private var mDirection: Direction? = null

        enum class Direction {
            LEFT_TO_RIGHT, RIGHT_TO_LEFT
        }

        init {
            initPaint(context, attrs)
        }

        @SuppressLint("CustomViewStyleable")
        private fun initPaint(context: Context, attrs: AttributeSet?) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView)
            val originColor =
                array.getColor(R.styleable.ColorTrackTextView_originColor, textColors.defaultColor)
            val changeColor =
                array.getColor(R.styleable.ColorTrackTextView_changeColor, textColors.defaultColor)
            mChangePaint = getPaintByColor(changeColor)
            mOriginPaint = getPaintByColor(originColor)
            array.recycle()
        }

        fun getPaintByColor(color: Int): Paint {
            val paint = Paint()
            paint.isAntiAlias = true
            paint.color = color
            //防抖动
            paint.isDither = true
            paint.textSize = textSize
            return paint
        }

        override fun onDraw(canvas: Canvas) {


            //根据进度把中间值算出来
            val middle = (mCurrentProgress * width).toInt()
            if (mDirection == Direction.LEFT_TO_RIGHT) {
                //绘制变色的
                drawText(canvas, mChangePaint, 0, middle)
                //绘制不变色的
                drawText(canvas, mOriginPaint, middle, width)
            } else {
                //绘制变色的
                drawText(canvas, mChangePaint, width - middle, width)
                //绘制不变色的
                drawText(canvas, mOriginPaint, 0, width - middle)
            }
        }

        /**
         * 绘制文字
         * @param canvas
         * @param paint
         * @param start
         * @param end
         */
        private fun drawText(canvas: Canvas, paint: Paint?, start: Int, end: Int) {
            canvas.save()
            //  裁剪矩形
            val rect = Rect(start, 0, end, height)
            canvas.clipRect(rect)
            val text = text.toString()
            //字体宽度
            val bound = Rect()
            mOriginPaint!!.getTextBounds(text, 0, text.length, bound)
            // x是字体从哪里开始画
            val x = width / 2 - bound.width() / 2
            val metrics = mOriginPaint!!.fontMetrics
            val dy = ((metrics.bottom - metrics.top) / 2 - metrics.bottom).toInt()
            val baseLine = height / 2 + dy
            canvas.drawText(text, x.toFloat(), baseLine.toFloat(), paint!!)
            canvas.restore()
        }

        fun setDirection(direction: Direction?) {
            mDirection = direction
        }

        fun setCurrentProgress(mCurrentProgress: Float) {
            this.mCurrentProgress = mCurrentProgress
            invalidate()
        }

        fun setChangeColor(changeColor: Int) {
            mChangePaint!!.color = changeColor
        }

        fun setOriginColor(originColor: Int) {
            mOriginPaint!!.color = originColor
        }
    }
