package com.example.wjc.eventdispatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

/**
 * Created by wjc on 2020/1/8.
 */

public class RoundSpan extends ReplacementSpan {
    private int mStrokeSize;
    private int mStrokeColor;
    private int mOtherTextSize;
    private int mTextSize;
    private int mTextColor;
    private int mRadius;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private int mMarginLeft;
    private int mMarginRight;
    private int mWidth;
    private int mBackgroundColor;

    public RoundSpan(int strokeSize, int strokeColor, int textSize, int textColor, int radius, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom, int marginLeft, int marginRight, int backgroundColor, int otherTextSize, int diffSize) {
        super();
        mStrokeSize = strokeSize;
        mStrokeColor = strokeColor;
        mTextSize = textSize;
        mTextColor = textColor;
        mRadius = radius;
        mPaddingLeft = paddingLeft;
        mPaddingTop = paddingTop;
        mPaddingRight = paddingRight;
        mPaddingBottom = paddingBottom;
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
        mBackgroundColor = backgroundColor;
        mOtherTextSize = otherTextSize;
    }

    public static RoundSpan.Builder getBuilder(Context context) {
        return new RoundSpan.Builder(context);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Paint paint1 = new Paint();
        paint1.setTextSize(mTextSize);
        mWidth = (int) paint1.measureText(text.subSequence(start, end).toString()) + mRadius * 2 + mStrokeSize * 2 + mPaddingLeft + mPaddingRight + mMarginLeft + mMarginRight;
        if (fm != null) {
            fm.ascent = (int) paint1.ascent();
            fm.descent = (int) paint1.descent();
            fm.top = (int) paint1.getFontMetrics().top;
            fm.bottom = (int) paint1.getFontMetrics().bottom;
        }
        return mWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        //1.边框画笔
        Paint paint1 = new Paint();
        paint1.setColor(mStrokeColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(mStrokeSize);
        paint1.setAntiAlias(true);
        //2.背景画笔
        Paint paint2 = new Paint();
        paint2.setColor(mBackgroundColor);
        paint2.setAntiAlias(true);
        //3.字体画笔
        Paint paint3 = new Paint();
        paint3.setColor(mTextColor);
        paint3.setTextSize(mTextSize);
        paint3.setAntiAlias(true);
        //4.辅助线
//        Paint paint4 = new Paint();
//        paint4.setColor(Color.parseColor("#ff6c00"));
//        paint4.setStyle(Paint.Style.STROKE);
//        paint4.setStrokeWidth(2);
//        paint4.setAntiAlias(true);
        //0.同行其它字体画笔
        float offset = 0;
        Paint paint5 ;
        if (mOtherTextSize != 0) {
            paint5 = new Paint();
            paint5.setTextSize(mOtherTextSize);
            paint5.setAntiAlias(true);
            //获取同行文字对齐基线需要向上偏移的距离
            offset = (paint5.descent() - paint5.ascent() - paint3.descent() + paint3.ascent()) / 2 - paint5.descent() + paint3.descent();
        }

        //画边框
        if (mStrokeColor != 0) {
            RectF rectF1 = new RectF(x + mMarginLeft+mStrokeSize, y + paint3.ascent() - mPaddingTop +mStrokeSize - offset, x + mWidth - mMarginRight-mStrokeSize, y + paint3.descent() + mPaddingBottom-mStrokeSize  - offset);
            canvas.drawRoundRect(rectF1, mRadius, mRadius, paint1);
        }
        //画背景

        if (mBackgroundColor != 0) {
            RectF rectF2 = new RectF(x + mMarginLeft + mStrokeSize, y + paint3.ascent() - mPaddingTop + mStrokeSize - offset, x + mWidth - mMarginRight - mStrokeSize, y + paint3.descent() + mPaddingBottom - mStrokeSize - offset);
            canvas.drawRoundRect(rectF2, mRadius, mRadius, paint2);
        }
        //画字体
        canvas.drawText(text.toString(), start, end, x + mRadius + mMarginLeft + mPaddingLeft + mStrokeSize, y - offset, paint3);
        //辅助线验证
//        canvas.drawLine(start, y + paint3.descent(), start + mWidth / 2, y + paint3.descent(), paint4);
//        canvas.drawLine(start, y + paint3.ascent(), start + mWidth / 2, y + paint3.ascent(), paint4);
//        if (paint5 != null) {
//            paint4.setColor(Color.parseColor("#000000"));
//            canvas.drawLine(start + mWidth / 2, y + paint5.descent(), start + mWidth, y + paint5.descent(), paint4);
//            canvas.drawLine(start + mWidth / 2, y + paint5.ascent(), start + mWidth, y + paint5.ascent(), paint4);
//        }

    }

    public static class Builder {

        private Context context;
        private int strokeSize;
        private int strokeColor;
        private int originTextSize;//未转化前字体大小 单位：sp
        private int originOtherTextSize;//未转化前同行其他字体大小 单位：sp
        private int otherTextSize;
        private int textSize;
        private int textColor;
        private int radius;
        private int paddingLeft;
        private int paddingRight;
        private int paddingTop;
        private int paddingBottom;
        private int marginLeft;
        private int marginRight;
        private int backgroundColor;

        private Builder(Context context) {
            this.context = context;
        }

        /**
         * 边框粗度
         *
         * @param dp
         * @return
         */
        public RoundSpan.Builder strokeSize(float dp) {
            if (dp < 0) {
                dp = 1;
            }
            strokeSize = DipPixUtil.dip2px(context, dp);
            return this;
        }

        /**
         * 边框颜色
         *
         * @param colorRes 资源id
         * @return
         */
        public RoundSpan.Builder strokeColor(@ColorRes int colorRes) {
            //默认为0
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
        public RoundSpan.Builder textSize(int sp) {
            textSize = DipPixUtil.sp2px(context, sp);
            originTextSize = sp;
            return this;
        }

        /**
         * 同行字体大小
         *
         * @param sp
         * @return
         */
        public RoundSpan.Builder otherTextSize(int sp) {
            otherTextSize = DipPixUtil.sp2px(context, sp);
            originOtherTextSize = sp;
            return this;
        }

        /**
         * 文字颜色
         *
         * @param colorRes 资源id
         * @return
         */
        public RoundSpan.Builder textColor(@ColorRes int colorRes) {
            if (colorRes != 0)
                textColor = context.getResources().getColor(colorRes);
            return this;
        }

        /**
         * 角度
         *
         * @param radius 单位dp
         * @return
         */
        public RoundSpan.Builder radius(int radius) {
            this.radius = DipPixUtil.dip2px(context, radius);
            return this;
        }

        /**
         * 设置文字距左边框的距离
         *
         * @param paddingLeft 单位dp
         * @return
         */
        public RoundSpan.Builder paddingLeft(int paddingLeft) {
            this.paddingLeft = DipPixUtil.dip2px(context, paddingLeft);
            return this;
        }

        /**
         * 设置文字距右边框的距离
         *
         * @param paddingRight 单位dp
         * @return
         */
        public RoundSpan.Builder paddingRight(int paddingRight) {
            this.paddingRight = DipPixUtil.dip2px(context, paddingRight);
            return this;
        }

        /**
         * 设置文字距上边框的距离
         *
         * @param paddingTop 单位dp
         * @return
         */
        public RoundSpan.Builder paddingTop(int paddingTop) {
            this.paddingTop = DipPixUtil.dip2px(context, paddingTop);
            return this;
        }

        /**
         * 设置文字距下边框的距离
         *
         * @param paddingBottom 单位dp
         * @return
         */
        public RoundSpan.Builder paddingBottom(int paddingBottom) {
            this.paddingBottom = DipPixUtil.dip2px(context, paddingBottom);
            return this;
        }

        /**
         * 当前span距左边的距离
         *
         * @param marginLeft 单位dp
         * @return
         */
        public RoundSpan.Builder marginLeft(int marginLeft) {
            this.marginLeft = DipPixUtil.dip2px(context, marginLeft);
            return this;
        }

        /**
         * 当前span距右边的距离
         *
         * @param marginRight 单位dp
         * @return
         */
        public RoundSpan.Builder marginRight(int marginRight) {
            this.marginRight = DipPixUtil.dip2px(context, marginRight);
            return this;
        }

        public RoundSpan.Builder backgroundColor(@ColorRes int colorRes) {
            if (colorRes != 0) {
                backgroundColor = context.getResources().getColor(colorRes);
            }
            return this;
        }

        public RoundSpan builder() {
            return new RoundSpan(strokeSize, strokeColor, textSize, textColor, radius, paddingLeft, paddingTop, paddingRight, paddingBottom, marginLeft, marginRight, backgroundColor, otherTextSize, originOtherTextSize - originTextSize);
        }
    }
}
