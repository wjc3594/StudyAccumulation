package com.example.wjc.eventdispatch;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by bjhl on 2019/9/10.
 */

public class CustomRelativeLayout extends RelativeLayout {
    private ViewDragHelper helper;
    private RelativeLayout layout;
    private RelativeLayout layout1;

    public CustomRelativeLayout(Context context) {
        this(context, null);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.e("layout==child", String.valueOf(layout == child));
                return layout == child ;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - getPaddingRight() - leftBound;
                return Math.min(Math.max(left, leftBound), rightBound);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - getPaddingBottom() - topBound;
                return Math.min(Math.max(top, topBound), bottomBound);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return  1;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return  1 ;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //保存手指释放拖拽时view的布局参数，避免动画引起的重绘使view回到最初的位置
                if(releasedChild==layout1){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) releasedChild.getLayoutParams();
                    params.bottomMargin = getHeight()-releasedChild.getBottom();
                    params.leftMargin = releasedChild.getLeft();
                    releasedChild.setLayoutParams(params);
                }else if(releasedChild==layout){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) releasedChild.getLayoutParams();
                    params.topMargin = releasedChild.getTop();
                    params.leftMargin = releasedChild.getLeft();
                    releasedChild.setLayoutParams(params);
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (helper != null) {
            //拖拽子view并且被拖拽view设置为可拖拽时拦截事件，返回true
            //否则不拦截事件，返回false
            return helper.shouldInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (helper != null) {
            helper.processTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        layout = this.findViewById(R.id.mylayout);
        layout1 = this.findViewById(R.id.relative_layout);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("wjc111", "click");
//            }
//        });
    }
}
