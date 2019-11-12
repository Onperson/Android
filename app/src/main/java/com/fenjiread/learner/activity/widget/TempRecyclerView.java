package com.fenjiread.learner.activity.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 仿画廊的RecyclerView
 */

public class TempRecyclerView extends RecyclerView {

    RelativeLayout mCentreView;
    LinearLayoutManager linearLayoutManager;


    public TempRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public TempRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public void getCentreView(RelativeLayout relativeLayout) {
        this.mCentreView = relativeLayout;
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        linearLayoutManager = (LinearLayoutManager) layout;
    }

    int viewHeight = 0;
    @Override
    public void onScrollStateChanged(int state) {
        Log.e(">>>>>>>>>>>>>","state:"+state);
        super.onScrollStateChanged(state);
        if(state == 0){
            int postion = linearLayoutManager.findFirstVisibleItemPosition();
            View view = linearLayoutManager.findViewByPosition(postion);
            int top = view.getTop();
            int offset = 0;
            if(viewHeight == 0){
                viewHeight = view.getHeight();
            }
            if(top == 0){
                return;
            }
            else if(-top < viewHeight/2){
                offset = top;
            }
            else {
                offset = viewHeight+top;
            }
            smoothScrollBy(0, offset);
        }
    }

    int offsetY = 0;


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        Log.e(">>>>>>>>>>>>>","onScrolled:"+dx);
        offsetY+=dy;

        int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        View firstview = linearLayoutManager.findViewByPosition(first);

        float centreViewX = mCentreView.getX();
        Log.e(">>>>>>>>>>>>","centreViewX:"+centreViewX);

        if(viewHeight == 0){
            viewHeight = firstview.getHeight();
        }
        int offseta = firstview.getTop();
        float sx = 1f+(float) offseta/viewHeight;
        if(offsetY == 0){
           // View view = linearLayoutManager.findViewByPosition(first+1);
           // view.setScaleX(1.5f);
         //   View fristItem = linearLayoutManager.getChildAt(0);

           // fristItem.setScaleX(1.5f);
        }

        firstview.setScaleX(sx);
        View lastview = linearLayoutManager.findViewByPosition(last);
        offseta = getHeight()-lastview.getBottom();
        sx = 1f+(float) offseta/viewHeight;
        lastview.setScaleX(sx);
    }


}