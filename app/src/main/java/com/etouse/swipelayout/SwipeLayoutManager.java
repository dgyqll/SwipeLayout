package com.etouse.swipelayout;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SwipeLayoutManager {
    private static SwipeLayoutManager swipeLayoutManager = new SwipeLayoutManager();
    private SwipeLayout swipeLayout;

    public static SwipeLayoutManager getInstance(){
        return swipeLayoutManager;
    }

    public void setSwipeLayout(SwipeLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }

    public boolean isCanSwipe(SwipeLayout swipeLayout) {
        if (this.swipeLayout == swipeLayout || this.swipeLayout == null) {
            return  true;
        }
        return false;
    }

    public void close(){
        if (swipeLayout != null) {
            swipeLayout.close();
        }

    }

    public void open(){
        if (swipeLayout != null) {
            swipeLayout.open();
        }
    }

    public SwipeLayout getSwipeLayout(){
        return swipeLayout;
    }
}
