package com.fenjiread.learner.activity.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fenjiread.learner.activity.model.WordEnergy;
import com.fenjiread.learner.activity.utils.ObjectUtils;
import com.fenjiread.learner.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * 收集词能的自定义动画
 * @author guotianhui
 */
public class CollectWordsLayout extends RelativeLayout {


    private int mDefaultEnergyScore;
    private String mTotalEnergyScore;
    private boolean isFlipCard = true;
    private final LayoutInflater mInflater;
    private boolean isOpenAnimation;//是否开启动画0
    private boolean isCancelAnimation;//是否销毁动画
    private boolean isInitLayout =true;
    private FrameLayout mBtnCollectWords;
    private final Random mRandom = new Random();
    private boolean isCollectWords = true;
    private ArrayList<WordEnergy> mEnergyList; //设置词能集合
    private RelativeLayout mCollectWordLayout;
    private CountScoreTextView mTvCollectScore;
    private AppCompatTextView mTvClickCollect;
    private static final int WHAT_ADD_PROGRESS = 1;
    private static final int INIT_ANIMATION = 2;
    private static final int CHANGE_RANGE = 10;  //view变化的y抖动范围
    public static final int PROGRESS_DELAY_MILLIS = 12; // 控制抖动动画执行的快慢，人眼不能识别16ms以下的
    public static final int REMOVE_DELAY_MILLIS = 1500; // 控制移除view的动画执行时间
    private AppCompatTextView mTvTotalEnergyScore,mTotalEnergy;
    private final ArrayList<Integer> mWordsEnergy = new ArrayList<>();
    private ArrayList<Point> mViewPoint = new ArrayList<>(); //创建一个界面的坐标点位置
    private OnAnimationCloseListener mOnAnimationCloseListener; // 动态收起布局的回调
    public static final int ANIMATION_SHOW_VIEW_DURATION = 500; //添加水滴时动画显示view执行的时间
    private final ArrayList<Integer> mRandomViewList = new ArrayList<>(); // 随机的词能列表
    private final ArrayList<WordsItemLayout> mViewList = new ArrayList<>();
    private final ArrayList<WordEnergy> mCollectEnergyList  = new ArrayList<>(); //收起词能的集合
    private final List<Float> mSpds = Arrays.asList(0.5f, 0.3f, 0.4f, 0.6f); //控制水滴动画的快慢
    private WordsItemLayout mWordsItemOne,mWordsItemTwo,mWordsItemThere,mWordsItemFour,mWordsItemFive,
            mWordsItemSix,mWordsItemSeven,mWordsItemEight,mWordsItemNine,mWordsItemTen,mWordsItemEleven;



    public CollectWordsLayout(@NonNull Context context) {
        this(context, null);
        initCollectWordsLayout();
    }

    public CollectWordsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initCollectWordsLayout();
    }

    public CollectWordsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(getContext());
        initCollectWordsLayout();
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_ADD_PROGRESS:
                    if (isCancelAnimation) { //根据isCancelAnimtion来标识是否退出，防止界面销毁时，再一次改变UI
                        return;
                    }
                    setWordsOffSet();  //改变View的Y轴坐标位置，实现上下移动的效果
                    mHandler.sendEmptyMessageDelayed(WHAT_ADD_PROGRESS, PROGRESS_DELAY_MILLIS);
                    break;
                case INIT_ANIMATION:
                    startAnimation();
                    break;
                default:
                    break;
            }
        }
    };

    private void initCollectWordsLayout() {
        if(isInitLayout) {
            View view = mInflater.inflate(R.layout.layout_collect_words, this, false);
            mCollectWordLayout = view.findViewById(R.id.rl_collect_words_layout);
            mWordsItemOne = view.findViewById(R.id.wil_words_item_one);
            mWordsItemTwo = view.findViewById(R.id.wil_words_item_two);
            mWordsItemThere = view.findViewById(R.id.wil_words_item_there);
            mWordsItemFour = view.findViewById(R.id.wil_words_item_four);
            mWordsItemFive = view.findViewById(R.id.wil_words_item_five);
            mWordsItemSix = view.findViewById(R.id.wil_words_item_six);
            mWordsItemSeven = view.findViewById(R.id.wil_words_item_seven);
            mWordsItemEight = view.findViewById(R.id.wil_words_item_eight);
            mWordsItemNine = view.findViewById(R.id.wil_words_item_nine);
            mWordsItemTen = view.findViewById(R.id.wil_words_item_ten);
            mWordsItemEleven = view.findViewById(R.id.wil_words_item_eleven);

            mViewList.clear(); //先清空列表，再添加数据
            mViewList.add(mWordsItemOne);
            mViewList.add(mWordsItemTwo);
            mViewList.add(mWordsItemThere);
            mViewList.add(mWordsItemFour);
            mViewList.add(mWordsItemFive);
            mViewList.add(mWordsItemSix);
            mViewList.add(mWordsItemSeven);
            mViewList.add(mWordsItemEight);
            mViewList.add(mWordsItemNine);
            mViewList.add(mWordsItemTen);
            mViewList.add(mWordsItemEleven);
            initOtherViewLayout(view); //抽取方法
            isInitLayout =false; //解决初始化两次的问题
        }
    }

    private void initOtherViewLayout(View view) {
        mBtnCollectWords = view.findViewById(R.id.fl_click_collect_words);
        mTvCollectScore = view.findViewById(R.id.tv_collect_score);
        mTvClickCollect = view.findViewById(R.id.tv_click_collect);
        mTotalEnergy = view.findViewById(R.id.tv_total_energy);
        mTvTotalEnergyScore = view.findViewById(R.id.tv_total_energy_score);
        initLayoutListener();
        addView(view);
        addShowViewAnimation(view);
        mHandler.sendEmptyMessageDelayed(INIT_ANIMATION, 200);
    }

    /**
     * 设置点击事件
     */
    private void initLayoutListener() {
        mBtnCollectWords.setOnClickListener((view) -> {
            if (isCollectWords) {
                for (int i=0; i< mViewList.size(); i++) {
                    removeViewAnimation(i);
                }
                flipAnimatorXViewShow(mTvClickCollect, mTvCollectScore, 500, 0);
            }
        });
        //统计词能结束，翻转卡片
        mTvCollectScore.setOnFinishCollectListener(totalScore -> {
            mTotalEnergyScore = totalScore; //返回总的词能分数
            mDefaultEnergyScore = Integer.valueOf(totalScore);
            mTvTotalEnergyScore.setText(mTotalEnergyScore);
            if (isFlipCard) {
                isFlipCard = false;
                flipAnimatorXViewShow(mTvCollectScore, mTvClickCollect, 500, 1500);
            }
        });
    }

    /**
     * 添加显示动画
     * @param view
     */
    private void addShowViewAnimation(View view) {
        view.setAlpha(0);
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().alpha(1).scaleX(1).scaleY(1).setDuration(ANIMATION_SHOW_VIEW_DURATION).start();
    }

    /**
     * 设置所有子view的加速度
     */
    private void setViewsSpeed() {
        for (int i = 0; i < mViewList.size(); i++) {
            View view = mViewList.get(i);
            setWordsSpeed(view);
        }
    }

    /**
     * 设置View的运动速度
     * @param view
     */
    private void setWordsSpeed(View view) {
        float spd = mSpds.get(mRandom.nextInt(mSpds.size()));
        view.setTag(R.string.spd, spd);
    }

    /**
     * 设置偏移
     */
    private void setWordsOffSet() {
        for (int i = 0; i < mViewList.size(); i++) {
            View view = mViewList.get(i);
            //拿到上次view保存的速度
            float spd = (float) view.getTag(R.string.spd);
            //水滴初始的位置
            Point point = mViewPoint.get(i);
            float original = point.y;
            boolean isUp = (boolean) view.getTag(R.string.isUp);
            float translationY;
            //根据水滴tag中的上下移动标识移动view
            if (isUp) {
                translationY = view.getY() - spd;
            } else {
                translationY = view.getY() + spd;
            }
            //对水滴位移范围的控制
            if (translationY - original > CHANGE_RANGE) {
                translationY = original + CHANGE_RANGE;
                view.setTag(R.string.isUp, true);
            } else if (translationY - original < -CHANGE_RANGE) {
                translationY = original - CHANGE_RANGE;
                // FIXME:每次当水滴回到初始点时再一次设置水滴的速度，从而达到时而快时而慢
                setWordsSpeed(view);
                view.setTag(R.string.isUp, false);
            }
            view.setY(translationY);
        }
    }

    /**
     * 移除动画
     */
    private void removeViewAnimation(int i) {
        WordsItemLayout view = mViewList.get(i);
        view.clearAnimation();   //清除抖动动画
        Interpolator Interpolator = new LinearInterpolator();
        float distanceX = getTarnslationX(view);
        float distanceY = getTarnslationY(view);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", distanceY);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(view, "translationX", distanceX);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(Interpolator);
        set.playTogether(transYAnim, transXAnim, scaleXAnim, scaleYAnim, alphaAnim);
        set.setDuration(REMOVE_DELAY_MILLIS);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.clearAnimation();
                addShowViewAnimation(view); //还原动画效果
                //取出原始坐标,让词能位置还原
                Point point = mViewPoint.get(i);
                float originalY = point.y;
                float originalX = point.x;
                view.setY(originalY);
                view.setX(originalX);
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * 计算两点移动的X轴距离
     * @param view
     * @return
     */
    private float getTarnslationX(View view) {
        float viewX = view.getRight();
        float btnX = mBtnCollectWords.getRight();
        return btnX - viewX;
    }

    /**
     * 计算两点移动的Y轴距离
     * @param view
     * @return
     */
    private float getTarnslationY(View view) {
        float viewY = view.getBottom();
        float btnY = mBtnCollectWords.getBottom();
        return btnY - viewY;
    }

    /**
     * 开启水滴抖动动画
     */
    public void startAnimation() {
        if (isOpenAnimation) {
            return;
        }
        for (WordsItemLayout wordsItemLayout : mViewList) { // 添加上下移动的动画
            setViewsSpeed();
            addShowViewAnimation(wordsItemLayout);
            wordsItemLayout.setTag(R.string.isUp, false); //随机设置view动画的方向
            Point point = new Point((int) wordsItemLayout.getX(), (int) wordsItemLayout.getY());
            mViewPoint.add(point);
        }
        isOpenAnimation = true;
        isCancelAnimation = false;
        mHandler.sendEmptyMessage(WHAT_ADD_PROGRESS);
    }

    /**
     * 初始化词能数据显示
     */
    private void initWordsEnergyData() {
        if(mEnergyList.size()  >=11) {  //集合大于11时，先显示一屏的数据
            for(int i=0 ; i< 11; i++) { //先取出一屏显示
                displayWordEnergyByIndex(i,false);
            }
        }else{
            for(int i=0 ; i< mEnergyList.size(); i++) {
                initRandomArrayListByBound(i);//初始化不重复的随机数组
                displayWordEnergyByIndex(i,true);
            }
        }
    }
    /**
     * 根据角标获取显示的词能位置
     * @param i
     */
    private void displayWordEnergyByIndex(int i, boolean isRandom) {
        WordsItemLayout wordsItemLayout;
        if(isRandom){
            int randomIndex = mRandomViewList.get(i);
            wordsItemLayout = mViewList.get(randomIndex);
        }else{
            wordsItemLayout = mViewList.get(i);
        }
        WordEnergy wordEnergy = mEnergyList.get(i); //一个词能对应一个View
        wordsItemLayout.setVisibility(View.VISIBLE);
        wordsItemLayout.setCollectWord(wordEnergy.getWordName());
        wordsItemLayout.setCollectWordEnergy(wordEnergy.getWordsEnergy());
        mCollectEnergyList.add(wordEnergy); //本地保存已经显示过的数据
        mWordsEnergy.add(wordEnergy.getWordsEnergy());
    }
    /**
     * 获取一个不会重复的随机数，范围0-10
     */
    private void initRandomArrayListByBound(int randomSize) {
        int[] a = new int[randomSize];//初始化数组
        int count = 0;//记录有效的随机数个数
        while(count < a.length){
            boolean flag = true;//用来标志的变量
            int r = mRandom.nextInt(mViewList.size());
            for(int i : a){
                if(r == i){
                    flag = false;
                    break;
                }
            }
            if(flag){
                a[count] = r;
                count++;
                mRandomViewList.add(r); //把不重复的角标值放入集合
            }
        }
    }
    /**
     * 提供一个方法，判断显示过的词能集合和网络返回的词能列表是否相同
     */
    private boolean compareLocalWordsNumber(){
        if(mEnergyList.size() >mCollectEnergyList.size()){
            int otherSceenList = mEnergyList.size() - mCollectEnergyList.size();
            int secondIndex = mCollectEnergyList.size(); //取出第二屏数据的起始位置
            mWordsEnergy.clear();//清空上一次的词能
            if(otherSceenList >=11){
                for (int i = 0; i < 11; i++) {
                    showAnotherSceenWords(secondIndex, i,false);
                }
            }else {
                for (int i = 0; i < otherSceenList; i++) {
                    initRandomArrayListByBound(otherSceenList);//初始化不重复的随机数组
                    showAnotherSceenWords(secondIndex, i,true);
                }
            }
             startAnimation(); //重新开启动画
            return  true;
        }else{
            return false;
        }
    }

    /**
     * 显示其外一屏数据
     * @param secondIndex
     * @param i
     */
    private void showAnotherSceenWords(int secondIndex, int i,boolean isRandom) {
        WordsItemLayout wordsItemLayout;
        if(isRandom){
            int randomIndex = mRandomViewList.get(i);
            wordsItemLayout = mViewList.get(randomIndex);
        }else{
            wordsItemLayout = mViewList.get(i);
        }
        WordEnergy wordEnergy = mEnergyList.get(i + secondIndex);
        wordsItemLayout.setVisibility(View.VISIBLE);
        wordsItemLayout.setCollectWord(wordEnergy.getWordName());
        wordsItemLayout.setCollectWordEnergy(wordEnergy.getWordsEnergy());
        mCollectEnergyList.add(wordEnergy);
        mWordsEnergy.add(wordEnergy.getWordsEnergy());
    }

    /**
     * 销毁
     */
    private void onDestroy() {
        isCancelAnimation = true;
        mHandler.removeCallbacksAndMessages(this);
    }

    /**
     * 界面销毁时回调
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    /**
     * 翻转卡片动画
     * @param oldView
     * @param newView
     * @param duringTime
     * @param delayeTime
     */
    private void flipAnimatorXViewShow(final View oldView, final View newView, final long duringTime, long delayeTime) {
        mBtnCollectWords.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationX", 0, 90);
                final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationX", -90, 0);
                animator2.setInterpolator(new OvershootInterpolator(2.0f));
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        oldView.setVisibility(View.GONE);
                        animator2.setDuration(duringTime).start();
                        newView.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animator2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (isCollectWords) {
                            isFlipCard = true;
                            isCollectWords = false;
                            mTvCollectScore.setTotalScore(mDefaultEnergyScore); //初始化词能
                            mTvCollectScore.setCountScoreList(mWordsEnergy);//添加能量值
                        }else{
                            isFlipCard = true;
                            isCollectWords = true;
                            boolean isCompareLocal = compareLocalWordsNumber();//重新初始化词能布局
                            if(!isCompareLocal){
                                if(ObjectUtils.isNotEmpty(mOnAnimationCloseListener)){  //动态收起布局
                                    isCancelAnimation = true;
                                    mOnAnimationCloseListener.onAnimationClose(); //回调动态收起布局
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    for(WordEnergy wordEnergy : mEnergyList){
                                        arrayList.add(wordEnergy.getWordName());
                                    }
                                    mOnAnimationCloseListener.onAddCollectWordsToTagCloud(arrayList);
                                }
                            }
                        }
                    }
                });
                animator1.setDuration(duringTime).start();
            }
        }, delayeTime);
    }
    /**
     * 提供一个方法接收词能集合，并初始化词能值
     */
    public void setWordsEnergyAndTotalScore(ArrayList<WordEnergy> energyList, int totalScore){
        this.mEnergyList = energyList;
        this.mDefaultEnergyScore = totalScore;
        //显示数据
        initWordsEnergyData();
    }
    /**
     * 动态收起布局方法
     * @param view
     */
    public void animateCloseLayout(final RelativeLayout view) {
        view.post(() ->{
            int origHeight = view.getHeight();
            ValueAnimator animator = createDropAnimator(view, origHeight, ObjectUtils.dp2px(90,getContext()));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCollectWordLayout.clearAnimation();
                    mCollectWordLayout.removeAllViews();
                    mCollectWordLayout.addView(mTotalEnergy);
                    mCollectWordLayout.addView(mTvTotalEnergyScore);
                    mTotalEnergy.setVisibility(View.VISIBLE);
                    mTvTotalEnergyScore.setVisibility(View.VISIBLE);
                    mTvTotalEnergyScore.setText(mTotalEnergyScore);
                    mCollectWordLayout.setBackground(getResources().getDrawable(R.drawable.ic_total_score_bg));
                }
            });
            animator.start();
        });
    }
    /**
     * 动态改变布局高度的属性动画
     * @param view
     * @param start
     * @param end
     * @return
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener((animation1) ->{
            int value = (int) animation1.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.setLayoutParams(layoutParams);
        });
        return animator;
    }

    /**
     * 设置回调监听
     * @param listener
     */
   public void setOnAnimationCloseLayout(OnAnimationCloseListener listener){
        this.mOnAnimationCloseListener = listener;
   }
    /**
     * 提供一个回调接口，动态的收起布局
     */
    public interface OnAnimationCloseListener{
        void onAnimationClose();
        void onAddCollectWordsToTagCloud(List<String> tagsList);
    }
}

