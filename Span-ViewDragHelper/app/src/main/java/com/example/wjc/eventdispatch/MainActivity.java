package com.example.wjc.eventdispatch;

import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    Button button;
    Button button1;
    Button button2;
    SpannableStringBuilder spannableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item1_layout);
        /**
         * Span demo
         */
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.text);
//        button=findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RoundSpan span = RoundSpan.getBuilder(MainActivity.this)
//                        .backgroundColor(R.color.color_1eb955)
//                        .textSize(12)
//                        .otherTextSize(20)
//                        .textColor(R.color.color_000000)
//                        .strokeSize(1)
//                        .strokeColor(R.color.colorAccent)
//                        .marginLeft(5)
//                        .marginRight(5)
//                        .radius(2)
//                        .builder();
//                RoundSpan span1 = RoundSpan.getBuilder(MainActivity.this)
//                        .backgroundColor(R.color.color_1eb955)
//                        .textSize(12)
//                        .otherTextSize(20)
//                        .textColor(R.color.color_000000)
//                        .builder();
////                spannableString.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////                spannableString.setSpan(span1, 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////                spannableString.setSpan(new AbsoluteSizeSpan(DipPixUtil.sp2px(MainActivity.this, 20)), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannableString.insert(4,"新加文本");
//                textView.setText(spannableString);
//            }
//        });
//        RoundSpan span = RoundSpan.getBuilder(MainActivity.this)
//                .backgroundColor(R.color.color_1eb955)
//                .textSize(26)
//                .textColor(R.color.color_ffffff)
//                .paddingLeft(3)
//                .paddingRight(3)
//                .paddingTop(1)
//                .paddingBottom(1)
//                .otherTextSize(11)
//                .radius(2)
//                .builder();
//        RoundSpan span1 = RoundSpan.getBuilder(MainActivity.this)
//                .textSize(12)
//                .otherTextSize(32)
//                .textColor(R.color.color_000000)
//                .builder();
//        spannableString = new SpannableStringBuilder("联报优惠二十一天集中训练营之口语训练营发发发发发发发发发发发付付");
////        spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#1eb955")),0,4,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
////        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")),0,4,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
////        spannableString.setSpan(new AbsoluteSizeSpan(DipPixUtil.sp2px(this,11)),0,4,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        spannableString.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        spannableString.setSpan(new AbsoluteSizeSpan(DipPixUtil.sp2px(this,11)),4,32,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.setText(spannableString);
        /**
         * ViewDragHelper demo
         */
        relativeLayout = findViewById(R.id.mylayout);
        relativeLayout1 = findViewById(R.id.relative_layout);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击触发-辣椒", Toast.LENGTH_SHORT).show();
            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击触发-无文字", Toast.LENGTH_SHORT).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击button1", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击button2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    private void startScaleAnimotion(final float startValue, final float endValue, int pivotX, int pivotY, int duration) {
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(relativeLayout, "scaleX", startValue, endValue);
//        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(relativeLayout, "scaleY", startValue, endValue);
//        relativeLayout.setPivotX(pivotX);
//        relativeLayout.setPivotY(DipPixUtil.dip2px(MainActivity.this, pivotY));
//        set.setDuration(duration);
//        set.play(objectAnimator1).with(objectAnimator2);
//        set.removeAllListeners();
//        set.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                if (startValue < endValue) {
//                    relativeLayout.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (startValue > endValue) {
//                    relativeLayout.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        set.start();
//    }
}
