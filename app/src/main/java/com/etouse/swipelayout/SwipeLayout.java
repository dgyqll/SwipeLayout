package com.etouse.swipelayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SwipeLayout extends FrameLayout {

    private View contentView;
    private View swipeView;
    private int contentViewMeasuredWidth;
    private int contentViewMeasuredHeight;
    private int swipeViewMeasuredWidth;
    private int swipeViewMeasuredHeight;
    private ViewDragHelper dragHelper;


    private float offset = 0.0f;
    public SwipeLayout(@NonNull Context context) {
        this(context,null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, mCallBack);
    }

    ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == contentView || child == swipeView ;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //限制contentView的移动位置
            if (child == contentView) {
                if (left > 0) {
                    left = 0;
                }
                if (left < -swipeViewMeasuredWidth) {
                    left = -swipeViewMeasuredWidth;
                }
            }
            //限制swipeView的移动位置
            if (child == swipeView) {
                if (left > contentViewMeasuredWidth) {
                    left = contentViewMeasuredWidth;
                }
                if (left < contentViewMeasuredWidth - swipeViewMeasuredWidth) {
                    left = contentViewMeasuredWidth - swipeViewMeasuredWidth;
                }
            }

            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            offset = dx;

            //让swipeView做伴随移动
            if (changedView == contentView) {
                int newLeft = swipeView.getLeft() + dx;
                if (newLeft < contentViewMeasuredWidth - swipeViewMeasuredWidth) {
                    newLeft = contentViewMeasuredWidth - swipeViewMeasuredWidth;
                }
                if (newLeft > contentViewMeasuredWidth ) {
                    newLeft = contentViewMeasuredWidth;
                }
                swipeView.layout(newLeft, 0, newLeft + swipeViewMeasuredWidth, swipeViewMeasuredHeight);

            }

            //让contentView做伴随移动
            if (changedView == swipeView) {
                int newLeft = contentView.getLeft() + dx;
                if (newLeft > 0) {
                    newLeft = 0;
                }

                if (newLeft < -swipeViewMeasuredWidth) {
                    newLeft = -swipeViewMeasuredWidth;
                }
                contentView.layout(newLeft,0,newLeft + contentViewMeasuredWidth,contentViewMeasuredHeight);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if (offset < 0) {
                if (contentView.getLeft() < -swipeViewMeasuredWidth / 4) {  //向左边滑动
                    dragHelper.smoothSlideViewTo(contentView, -swipeViewMeasuredWidth, 0);
                    ViewCompat.postInvalidateOnAnimation(contentView);
                } else {    //向右边滑动
                    dragHelper.smoothSlideViewTo(contentView, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(contentView);

                }
            } else {
                if (contentView.getLeft() > -swipeViewMeasuredWidth * 3/ 4) {  //向右边滑动
                    dragHelper.smoothSlideViewTo(contentView, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(contentView);
                } else {    //向左边滑动
                    dragHelper.smoothSlideViewTo(contentView, -swipeViewMeasuredWidth, 0);
                    ViewCompat.postInvalidateOnAnimation(contentView);

                }
            }

        }
    };


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(contentView);
        }
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //初始化子控件
        contentView = getChildAt(0);
        swipeView = getChildAt(1);

    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        contentViewMeasuredWidth = contentView.getMeasuredWidth();
        contentViewMeasuredHeight = contentView.getMeasuredHeight();
        swipeViewMeasuredWidth = swipeView.getMeasuredWidth();
        swipeViewMeasuredHeight = swipeView.getMeasuredHeight();

        super.onLayout(changed, left, top, right, bottom);
        contentView.layout(0, 0, contentViewMeasuredWidth, contentViewMeasuredHeight);
        swipeView.layout(contentViewMeasuredWidth,0,contentViewMeasuredWidth + swipeViewMeasuredWidth,swipeViewMeasuredHeight );
        invalidate();
    }

    /**
     * 判断是否展开
     * @return
     */
    public boolean isExpand(){
        if (contentView.getLeft() == -swipeViewMeasuredWidth) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开
     */
    public void open(){
        if (isExpand()) {
            dragHelper.smoothSlideViewTo(contentView,  -swipeViewMeasuredWidth, 0);
            ViewCompat.postInvalidateOnAnimation(contentView);
        } else {
            dragHelper.smoothSlideViewTo(contentView,0, 0);
            ViewCompat.postInvalidateOnAnimation(contentView);
        }
    }

    /**
     * 关闭
     */
    public void close(){
        if (!isExpand()) {
            dragHelper.smoothSlideViewTo(contentView, -swipeViewMeasuredWidth, 0);
            ViewCompat.postInvalidateOnAnimation(contentView);
        } else {
            dragHelper.smoothSlideViewTo(contentView, 0, 0);
            ViewCompat.postInvalidateOnAnimation(contentView);
        }
    }
}
