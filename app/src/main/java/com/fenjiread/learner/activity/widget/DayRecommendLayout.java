package com.fenjiread.learner.activity.widget;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fenjiread.learner.activity.adapter.DayRegisterAdapter;
import com.fenjiread.learner.activity.model.RegisterItem;
import com.fenjiread.learner.R;

import java.util.ArrayList;

/**
 * 每日推荐弹框
 * @author guotianhui
 */
public class DayRecommendLayout extends FrameLayout{

    private final Context mContext;
    private boolean mIsShowBack;
    boolean isLightRegister =true; //点亮签到
    private LinearLayout mFlCardBack;
    private AnimatorSet mRightOutSet,mLeftInSet;
    private RelativeLayout mFlCardFront;
    private DayRegisterAdapter mRegisterAdapter;
    private ArrayList<RegisterItem> mRegisterDayList;

    public DayRecommendLayout(Context context) {
        super(context);
        this.mContext = context;
        initDayRecommendLayout();
    }

    public DayRecommendLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initDayRecommendLayout();
    }

    public DayRecommendLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initDayRecommendLayout();
    }

    private void initDayRecommendLayout(){
        //签到完成之后的每日推荐名言
        View mFrontView = View.inflate(mContext, R.layout.layout_day_recommend, null);
        AppCompatTextView mStartDay = mFrontView.findViewById(R.id.tv_start_day);
        AppCompatTextView mEndDay = mFrontView.findViewById(R.id.tv_end_day);
        AppCompatTextView mRecommendWords = mFrontView.findViewById(R.id.tv_recommend_words);
        AppCompatTextView mWordsAuthor = mFrontView.findViewById(R.id.tv_words_author);
        mFlCardFront = mFrontView.findViewById(R.id.rl_recommend_layout);
        mFrontView.setVisibility(View.GONE);
        addView(mFrontView);

        //每日签到的布局文件
        View backgroundView = View.inflate(mContext, R.layout.layout_book_register, null);
        RecyclerView mRecyclerView = backgroundView.findViewById(R.id.rv_register_recyclerview);
        mRegisterDayList = new ArrayList<>();

        //测试数据
        for(int i=0; i<2; i++){ //已经签到数据
            RegisterItem registerItem = new RegisterItem();
            registerItem.setRegister(true);
            registerItem.setShowTwoDot(true);
            registerItem.setRegisterDay("第"+i+"天");
            mRegisterDayList.add(registerItem);
        }
        for(int i=2; i<3; i++){ //已经签到数据
            RegisterItem registerItem = new RegisterItem();
            registerItem.setRegister(false);
            registerItem.setShowTwoDot(true);
            registerItem.setRegisterDay("第"+i+"天");
            mRegisterDayList.add(registerItem);
        }
        RegisterItem registerItem = new RegisterItem();
        registerItem.setRegister(false);
        registerItem.setShowTwoDot(false);
        registerItem.setRegisterDay("第"+mRegisterDayList.size()+1+"天");
        mRegisterDayList.add(registerItem);

        mRegisterAdapter = new DayRegisterAdapter(R.layout.layout_day_register_item, mRegisterDayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRegisterAdapter);
        setLastItemHideTwoDot(mRecyclerView);
        mFlCardBack = backgroundView.findViewById(R.id.ll_back_layout);
        backgroundView.setVisibility(View.VISIBLE);
        addView(backgroundView);

        setAnimators();
        setCameraDistance();
    }
    // 设置动画
    private void setAnimators() {
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_out);
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_in);

    }
    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }
    /**
     * 提供一个方法，点亮签到
     */
    public void lightRegisterBook(){

    }
    /**
     * 监听最后一条数据
     */
    public void setLastItemHideTwoDot(RecyclerView mRecyclerView){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //设置什么布局管理器,就获取什么的布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition ,角标值
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //所有条目,数量值
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //隐藏最的两个点

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //dx>0:向右滑动,dx<0:向左滑动
                //dy>0:向下滑动,dy<0:向上滑动
                isSlidingToLast =  (dy > 0) ;
            }
        });
    }
    /**
     * 翻转卡片
     */
    public void flipCard() {

        if(isLightRegister) {
            RegisterItem registerItem = mRegisterAdapter.getItem(2);
            assert registerItem != null;
            registerItem.setShowTwoDot(true);
            registerItem.setRegister(true);
            mRegisterAdapter.notifyDataSetChanged();
            isLightRegister = false;
        }
        mFlCardBack.postDelayed(() -> {
            // 正面朝上
            if (!mIsShowBack) {
                mRightOutSet.setTarget(mFlCardBack);
                mLeftInSet.setTarget(mFlCardFront);
                mFlCardBack.setVisibility(View.GONE);
                mFlCardFront.setVisibility(View.VISIBLE);
                mRightOutSet.start();
                mLeftInSet.start();
                mIsShowBack = true;
            } else { // 背面朝上
                mRightOutSet.setTarget(mFlCardFront);
                mLeftInSet.setTarget(mFlCardBack);
                mFlCardBack.setVisibility(View.VISIBLE);
                mFlCardFront.setVisibility(View.GONE);
                mRightOutSet.start();
                mLeftInSet.start();
                mIsShowBack = false;
            }
        }, 200);
    }
}
