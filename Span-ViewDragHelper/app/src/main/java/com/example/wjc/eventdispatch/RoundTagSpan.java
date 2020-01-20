package com.example.wjc.eventdispatch;

import android.text.style.ReplacementSpan;

/**
 * Created by wjc on 2020/1/7.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by iori on 18/11/1.
 */

public class RoundTagSpan extends ReplacementSpan {
    private int mStrokeSize;
    private int mStrokeColor;
    private int mOtherTextSize;
    private int mTextSize;
    private int mTextColor;
    private int mRadius;
    private int mPadding;
    private int mMarginLeft;
    private int mMarginRight;
    private int mWidth;
    private int mBackgroundColor;


    public static Builder getBuilder(Context context) {
        return new Builder(context);
    }

    /**
     * @param rimSize     边框粗度
     * @param rimColor    边框颜色
     * @param textSize    文字大小
     * @param textColor   文字颜色
     * @param radius      角度
     * @param padding     文字距左右边框的距离
     * @param marginLeft  span距左边文字的距离
     * @param marginRight span距右边文字的距离
     */
    private RoundTagSpan(int rimSize, int rimColor, int textSize, int textColor, int radius, int padding, int marginLeft, int marginRight, int backgroundColor, int otherTextSize) {
        super();
        mStrokeSize = rimSize;
        mStrokeColor = rimColor;
        mTextSize = textSize;
        mTextColor = textColor;
        mRadius = radius;
        mPadding = padding;
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
        mBackgroundColor = backgroundColor;
        mOtherTextSize = otherTextSize;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (mTextSize == 0) {
            mTextSize = (int) (paint.getTextSize() / 3 * 2);
        }
        Rect textRect = new Rect();
        Paint p = new Paint();
        p.setTextSize(mTextSize);
        p.getTextBounds(text.toString(), start, end, textRect);
        mWidth = textRect.width() + mStrokeSize * 3 + mMarginLeft + mMarginRight + mPadding * 2;
//        Log.e("s_tag", "width=" + mWidth + "(rect.width=" + textRect.width() + "  marginLeft=" + mMarginLeft + "  marginRight=" + mMarginRight + "  padding=" + mPadding + ")");
        return mWidth;
    }

    /**
     * @param y baseline
     */
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (mTextSize == 0) {
            mTextSize = (int) (paint.getTextSize() / 3 * 2);
        }
        float offsetHeight = 0;

        paint.setTextSize(mTextSize);
        paint.setColor(mTextColor);
        paint.setAntiAlias(true);
        Paint otherPaint = new Paint();
        otherPaint.setTextSize(mOtherTextSize);
        otherPaint.setAntiAlias(true);
        if (mOtherTextSize > mTextSize) {
            offsetHeight = (otherPaint.descent() - otherPaint.ascent() - paint.descent() + paint.ascent())*4;
        }
        Log.d("leading", String.valueOf(paint.getFontMetrics().leading));
        float height = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
        float heightOffset = height / 10 * 1;

        if (mStrokeColor != 0) {
            //绘制圆角矩形
            Paint rectPaint = new Paint();
            rectPaint.setColor(mStrokeColor);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(mStrokeSize);
            rectPaint.setAntiAlias(true);
            RectF rectF;
//        Log.e("s_tag", "x=" + x  + " / y=" + y + " / ascent=" + paint.getFontMetrics().ascent + " / descent=" + paint.descent() + " / textSize=" + mTextSize);
            if (mOtherTextSize > mTextSize) {
                rectF = new RectF(x + mMarginLeft + mStrokeSize, y + paint.ascent() - offsetHeight, x + mWidth - mMarginRight, y + paint.descent() + offsetHeight);
            } else {
                rectF = new RectF(x + mMarginLeft + mStrokeSize, y + paint.ascent(), x + mWidth - mMarginRight, y + paint.descent());
            }
            canvas.drawRoundRect(rectF, mRadius, mRadius, rectPaint);
        }

        if (mBackgroundColor != 0) {
            Paint rectPaint = new Paint();
            rectPaint.setColor(mBackgroundColor);
            rectPaint.setStyle(Paint.Style.FILL);
            rectPaint.setAntiAlias(true);
            RectF rectF;
            if (mOtherTextSize > mTextSize) {
                rectF = new RectF(x + mMarginLeft + mStrokeSize + mStrokeSize, y + otherPaint.ascent() - offsetHeight + mStrokeSize, x + mWidth - mMarginRight - mStrokeSize, y + otherPaint.descent() + offsetHeight - mStrokeSize);
            } else {
                rectF = new RectF(x + mMarginLeft + mStrokeSize + mStrokeSize, y + paint.ascent() + mStrokeSize, x + mWidth - mMarginRight - mStrokeSize, y + paint.descent() - mStrokeSize);
            }
            canvas.drawRoundRect(rectF, mRadius, mRadius, rectPaint);
        }

        //绘制文字
        float startX = x + mMarginLeft + mPadding + mStrokeSize * 2; // x是左边起点
        float startY = height / 2 + (Math.abs(paint.ascent()) - paint.descent()) / 2; // y是baseLine的高度起点
//        Log.e("s_tag", "startX=" + startX + " / startY=" + startY + " / height=" + height + " / textHeight=" + textHeight);
        if (mOtherTextSize > mTextSize) {
            canvas.drawText(text, start, end, startX, y + offsetHeight, paint);
        } else {
            canvas.drawText(text, start, end, startX, y, paint);
        }
    }

    public static class Builder {

        private Context context;
        private float density;
        private int strokeSize;
        private int strokeColor;
        private int otherTextSize;
        private int textSize;
        private int textColor;
        private int radius;
        private int padding;
        private int marginLeft;
        private int marginRight;
        private int backgroundColor;

        private Builder(Context context) {
            this.context = context;
            density = context.getResources().getDisplayMetrics().density;
        }

        /**
         * 边框粗度
         *
         * @param dp
         * @return
         */
        public Builder strokeSize(float dp) {
            if (dp < 0) {
                dp = 1;
            }
            strokeSize = Math.round(density * dp);
            return this;
        }

        /**
         * 边框颜色
         *
         * @param colorRes 资源id
         * @return
         */
        public Builder strokeColor(@ColorRes int colorRes) {
            /*if (colorRes == 0) {
                strokeColor = Color.parseColor("#000000");
            } else {
                strokeColor = context.getResources().getColor(colorRes);
            }*/
            if (colorRes != 0) {
                strokeColor = context.getResources().getColor(colorRes);
            }
            return this;
        }

        /**
         * 文字大小
         *
         * @param sp
         * @return
         */
        public Builder textSize(int sp) {
            textSize = Math.round(density * sp);
            return this;
        }

        /**
         * 同行字体大小
         *
         * @param sp
         * @return
         */
        public Builder otherTextSize(int sp) {
            otherTextSize = Math.round(density * sp);
            return this;
        }

        /**
         * 文字颜色
         *
         * @param colorRes 资源id
         * @return
         */
        public Builder textColor(@ColorRes int colorRes) {
            if (colorRes == 0) {
                textColor = Color.parseColor("#000000");
            } else {
                textColor = context.getResources().getColor(colorRes);
            }
            return this;
        }

        /**
         * 角度
         *
         * @param radius 单位dp
         * @return
         */
        public Builder radius(int radius) {
            this.radius = Math.round(density * radius);
            return this;
        }

        /**
         * 设置文字距左右边框的距离
         *
         * @param padding 单位dp
         * @return
         */
        public Builder padding(int padding) {
            this.padding = Math.round(density * padding);
            return this;
        }

        /**
         * 当前span距左边的距离
         *
         * @param margin 单位dp
         * @return
         */
        public Builder marginLeft(int margin) {
            marginLeft = Math.round(density * margin);
            return this;
        }

        /**
         * 当前span距右边的距离
         *
         * @param margin 单位dp
         * @return
         */
        public Builder marginRight(int margin) {
            marginRight = Math.round(density * margin);
            return this;
        }

        public Builder backgroundColor(@ColorRes int colorRes) {
            if (colorRes != 0) {
                backgroundColor = context.getResources().getColor(colorRes);
            }
            return this;
        }

        public RoundTagSpan builder() {
            return new RoundTagSpan(strokeSize, strokeColor, textSize, textColor, radius, padding, marginLeft, marginRight, backgroundColor, otherTextSize);
        }
    }

}
