package com.fenjiread.learner.activity.widget;


import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.activity.ScrollingActivity;
import com.fenjiread.learner.activity.utils.ObjectUtils;

/**
 * 任务可滑动的RecyclerView
 */
public class TaskboxRecyclerView  extends RecyclerView {

    private RectF mRectF;
    private int mMinWidth;
    private int mMaxWidth;
    private int mHalfWidth;
    private int mScreenWidth;
    private boolean mIsInitRectF = true;
    private static final float MIN_SCALE = .95f;
    private static final float MAX_SCALE = 1.15f;

    public TaskboxRecyclerView(@NonNull Context context) {
        super(context);
        initTaskBoxRecyclerView();
    }

    public TaskboxRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTaskBoxRecyclerView();
    }

    public TaskboxRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTaskBoxRecyclerView();
    }

    private void initTaskBoxRecyclerView() {
        //获取屏幕宽度
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mHalfWidth = mScreenWidth / 2;
        mMinWidth = (int) (mScreenWidth * 0.28f); //获取itme的最小宽度
        mMaxWidth = mScreenWidth - 2 * mMinWidth; // 获取item的最大宽度

        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 滑动事件监听
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();

            if (mIsInitRectF) {
                View fristItem = recyclerView.getChildAt(0);
                mRectF = new RectF(fristItem.getLeft() * 0.9f, fristItem.getTop(),
                        fristItem.getRight() * 1.2f, fristItem.getBottom());
                mIsInitRectF = false;
            }

            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);

                AppCompatImageView itemImg = child.findViewById(R.id.iv_pic);


                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                lp.rightMargin = 5;
                lp.height = 180;


                int left = child.getLeft();
                int right = mScreenWidth - child.getRight();
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                // Log.e(">>>>>>>>>>>", "percent = " + percent);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                // Log.e(">>>>>>>>>>>", "percent = " + percent);
                int width = (int) ((mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth)) * 0.6f);
                // Log.e(">>>>>>>>>>>", "要放大的宽度:" + width);

                float childHalfWidth = child.getWidth() / 2; //获取每个item的宽度的一半
                int childLeft = child.getLeft(); //左边的坐标点
                int childRight = child.getRight(); //右边的坐标点
                Log.e(">>>>>>>>>>>>>>>", "childLeft:" + childLeft);
                Log.e(">>>>>>>>>>>>>>>", "+childRight:" + childRight);

                if (ObjectUtils.isNotEmpty(mRectF)) {
                    if (childLeft >= mRectF.left && childRight <= mRectF.right) {
                        itemImg.setImageResource(R.drawable.ic_selected_box);
                        // child.scrollTo((int)mRectF.left,(int)mRectF.bottom);

                    } else {
                        itemImg.setImageResource(R.drawable.ic_normal_box);
                    }
                    Log.e(">>>>>>>>>>>>>>>", "+mRectF.right:" + mRectF.right);
                    Log.e(">>>>>>>>>>>>>>>", "mRectF.left:" + mRectF.left);
                }

                lp.width = width;
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);

                //  itemImg.setImageResource(R.drawable.ic_selected_box);

               /* if (percent > 1f / 3) {
                    ((TextView) child.getChildAt(1)).setTextColor(Color.BLUE);
                } else {ScrollingActivity
                    ((TextView) child.getChildAt(1)).setTextColor(Color.BLACK);
                }*/
            }
        }
    };

}