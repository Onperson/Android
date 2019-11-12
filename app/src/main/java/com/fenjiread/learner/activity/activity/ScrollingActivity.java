package com.fenjiread.learner.activity.activity;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.utils.ObjectUtils;

public class ScrollingActivity extends AppCompatActivity {




    private  RectF mRectF;
    private int mMinWidth;
    private int mMaxWidth;
    private int mHalfWidth;
    private int mScreenWidth;
    private boolean mIsInitRectF = true;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private static final float MIN_SCALE = .95f;
    private static final float MAX_SCALE = 1.15f;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mRecycleView = (RecyclerView) findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        //获取屏幕宽度
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mHalfWidth = mScreenWidth / 2 ;
        mMinWidth = (int) (mScreenWidth * 0.28f); //获取itme的最小宽度
        mMaxWidth = mScreenWidth - 2 * mMinWidth; // 获取item的最大宽度
        String[] names = new String[20];
        for (int i = 0; i < names.length; i++) {
            names[i] = "任务" + (i + 1);
        }

        mAdapter = new MyAdapter(names);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(mOnScrollListener);
    }

    /**
     * 滑动事件监听
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();

            if(mIsInitRectF){
                View fristItem = recyclerView.getChildAt(0);
                mRectF = new RectF(fristItem.getLeft()*0.9f,fristItem.getTop(),
                        fristItem.getRight()*1.2f,fristItem.getBottom());
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
                Log.e(">>>>>>>>>>>>>>>","childLeft:"+childLeft);
                Log.e(">>>>>>>>>>>>>>>","+childRight:"+childRight);

                if(ObjectUtils.isNotEmpty(mRectF)) {
                    if (childLeft >= mRectF.left && childRight <= mRectF.right) {
                        itemImg.setImageResource(R.drawable.ic_selected_box);
                       // child.scrollTo((int)mRectF.left,(int)mRectF.bottom);

                    }else {
                        itemImg.setImageResource(R.drawable.ic_normal_box);
                    }
                    Log.e(">>>>>>>>>>>>>>>","+mRectF.right:"+mRectF.right);
                    Log.e(">>>>>>>>>>>>>>>","mRectF.left:"+mRectF.left);
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

    /**
     * RecyclerView 的 Adapter
     */
    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private String[] mDatas;

        public MyAdapter(String[] mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ScrollingActivity.this).inflate(R.layout.layout_item_recycler, null);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RelativeLayout.LayoutParams paramLayout = new RelativeLayout.LayoutParams(-2,-2);

            if(position ==0 && holder.getAdapterPosition() ==0 ){
                paramLayout.leftMargin = 300;

            }else if(position ==mDatas.length -1 && holder.getAdapterPosition() ==mDatas.length -1 ){
                paramLayout.leftMargin = 130;
                holder.itemView.setVisibility(View.INVISIBLE);
            }else{
                if(position >0) {
                    paramLayout.leftMargin = 0;
                    holder.itemView.setVisibility(View.VISIBLE);
                }
            }

            holder.itemView.setLayoutParams(paramLayout);
            AppCompatImageView iv = holder.itemView.findViewById(R.id.iv_pic);
            AppCompatTextView tv = holder.itemView.findViewById(R.id.tv_title);
            iv.setImageResource(R.drawable.ic_normal_box);
            tv.setText(mDatas[position]);
        }

        @Override
        public int getItemCount() {
            return mDatas.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}