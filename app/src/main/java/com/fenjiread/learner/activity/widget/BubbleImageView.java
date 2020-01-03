package com.fenjiread.learner.activity.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


import com.fenjiread.learner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 气泡View
 * @author ：rtmap
 */
public class BubbleImageView extends ViewGroup {
    private Context mContext;
    private List<Drawable> mStarDrawable;
    private List<Interpolator> mInterpolators;
    private int mWidth;
    private int mHeight;
    //定义贝塞尔曲线的数据点和两个控制点
    private PointF mStartPoint, mEndPoint, mControllPointOne, mControllPointTwo;

    private Random random = new Random();

    public BubbleImageView(Context context) {
        this(context, null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mStarDrawable = new ArrayList<>();

        mInterpolators = new ArrayList<>();
        mStartPoint = new PointF();
        mEndPoint = new PointF();
        mControllPointOne = new PointF();
        mControllPointTwo = new PointF();

        //初始化图片资源
        mStarDrawable.add(ContextCompat.getDrawable(mContext, R.drawable.icon_home_coin));
//        mStarDrawable.add(ContextCompat.getDrawable(mContext, R.mipmap.icon_home_coin));

        //初始化插补器
        mInterpolators.add(new LinearInterpolator());
        mInterpolators.add(new AccelerateDecelerateInterpolator());
        mInterpolators.add(new AccelerateInterpolator());
        mInterpolators.add(new DecelerateInterpolator());

        ImageView image_heard = new ImageView(mContext);
        image_heard.setImageDrawable(mStarDrawable.get(0));

        image_heard.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        image_heard.setVisibility(INVISIBLE);

        addView(image_heard);

        handler.sendEmptyMessageDelayed(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 初始化各个点

        //借用第一个子view控件中的宽高
        View child = getChildAt(0);
        int childW = child.getMeasuredWidth();
        int childH = child.getMeasuredHeight();

        mStartPoint.x = (mWidth - childW) / 2;
        mStartPoint.y = mHeight - childH;
        mEndPoint.x = (mWidth - childW) / 2;
        mEndPoint.y = 0 - childH;

        mControllPointOne.x = random.nextInt(mWidth / 2);
        mControllPointOne.y = random.nextInt(mHeight / 2) + mHeight / 2;

        mControllPointTwo.x = random.nextInt(mWidth / 2) + mWidth / 2;
        mControllPointTwo.y = random.nextInt(mHeight / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取view的宽高测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //保存测量高度
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();
            child.layout((mWidth - childW) / 2, (mHeight - childH), (mWidth - childW) / 2 + childW, mHeight);
        }
    }

//    /**
//     * 开始动画
//     */
//    public void startRunning() {
//        BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(mControllPointOne, mControllPointTwo);
//        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierTypeEvaluator, mStartPoint, mEndPoint);
//        valueAnimator.addUpdateListener(animation -> {
//            PointF pointF = (PointF) animation.getAnimatedValue();
//            getChildAt(0).setX(pointF.x);
//            getChildAt(0).setY(pointF.y);
//        });
//
//        valueAnimator.setDuration(3000);
//        valueAnimator.start();
//    }

    public class BezierTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF mControllPoint1, mControllPoint2;

        public BezierTypeEvaluator(PointF mControllPointOne, PointF mControllPointTwo) {
            mControllPoint1 = mControllPointOne;
            mControllPoint2 = mControllPointTwo;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointCur = new PointF();
            pointCur.x = mStartPoint.x * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.x * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.x * (1 - fraction) * fraction * fraction + endValue.x * fraction * fraction * fraction;// 实时计算最新的点X坐标
            pointCur.y = mStartPoint.y * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint1.y * fraction * (1 - fraction) * (1 - fraction) + 3
                    * mControllPoint2.y * (1 - fraction) * fraction * fraction + endValue.y * fraction * fraction * fraction;// 实时计算最新的点Y坐标
            return pointCur;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //添加气泡到布局文件并开始动画
            final ImageView image_random = new ImageView(mContext);
            image_random.setImageDrawable(mStarDrawable.get(0));

            image_random.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            image_random.setVisibility(INVISIBLE);
            addView(image_random);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(image_random, View.SCALE_X, 0.3f, 0.6f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(image_random, View.SCALE_Y, 0.3f, 0.6f);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(image_random, View.ALPHA, 0.4f, 1f);
            AnimatorSet enterAnimatorSet = new AnimatorSet();
            enterAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
            enterAnimatorSet.setDuration(500);
            enterAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    image_random.setVisibility(VISIBLE);
                }
            });

            invalidate();

            if (mWidth <= 0 ) {
                sendEmptyMessageDelayed(0, 1000);
                return;
            }
            //开始做动画效果
            PointF endPointRandom = new PointF(random.nextInt(mWidth), mEndPoint.y);
//                BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(mControllPointOne, mControllPointTwo);
            BezierTypeEvaluator bezierTypeEvaluator = new BezierTypeEvaluator(new PointF(random.nextInt(mWidth), random.nextInt(mHeight)), new PointF(random.nextInt(mWidth), random.nextInt(mHeight)));
            ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierTypeEvaluator, mStartPoint, endPointRandom);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF pointF = (PointF) animation.getAnimatedValue();
                    image_random.setX(pointF.x);
                    image_random.setY(pointF.y);
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    removeView(image_random);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            TimeInterpolator[] timeInterpolator = {new LinearInterpolator(), new AccelerateDecelerateInterpolator(), new DecelerateInterpolator(), new AccelerateInterpolator()};
            valueAnimator.setInterpolator(timeInterpolator[new Random().nextInt(timeInterpolator.length)]);
            ObjectAnimator translateAlphaAnimator = ObjectAnimator.ofFloat(image_random, View.ALPHA, 1f, 0f);
            translateAlphaAnimator.setInterpolator(new DecelerateInterpolator());
            ObjectAnimator translateScaleYAnimator = ObjectAnimator.ofFloat(image_random, View.SCALE_Y, 0.6f, 1f);
            translateScaleYAnimator.setInterpolator(new DecelerateInterpolator());
            ObjectAnimator translateScaleXAnimator = ObjectAnimator.ofFloat(image_random, View.SCALE_X, 0.6f, 1f);
            translateScaleXAnimator.setInterpolator(new DecelerateInterpolator());
            AnimatorSet translateAnimatorSet = new AnimatorSet();
            translateAnimatorSet.playTogether(valueAnimator, translateAlphaAnimator, translateScaleXAnimator, translateScaleYAnimator);
            translateAnimatorSet.setDuration(4000);

            AnimatorSet allAnimator = new AnimatorSet();
            allAnimator.playSequentially(enterAnimatorSet, translateAnimatorSet);
            allAnimator.start();

            sendEmptyMessageDelayed(0, 2000);
        }
    };
}
