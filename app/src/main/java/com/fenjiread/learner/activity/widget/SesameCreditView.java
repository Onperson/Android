package com.fenjiread.learner.activity.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fenjiread.learner.R;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Administrator on 2015/8/5.
 */
public class SesameCreditView extends View {

    private int mCenterX;
    private int mCenterY;
    private int mRadius;  //六边形的半径
    int mCountRadius= 0;  //保存六边形的默认半径
    private int mViewWidth = 230;
    private int mViewHeight = 230;

    private Paint mBitmapPaint = new Paint();
    private Point mCenterPoint = new Point();
    private Paint mVisivlePaint = new Paint(); //可见区域的画笔
    private Paint mStrokePaint = new Paint(); //可见区域的画笔外框
    private Paint mLineDefaultPaint = new Paint(); //默认画线


    private float mFristValue;
    private float mSecondValue;
    private float mThridValue;
    private float mFourValue;
    private float mFiveValue;
    private float mSixValue;

    private float mDefaultRatio = 0.0f;
    private int mPicAndViewSpacing = 20;
    private ArrayList<Bitmap> mPicBitmap;
    private ArrayList<Region> mPicAreas = new ArrayList<Region>();

    private String[] mStringResIds = new String[]{ "提取关键信息\n40%%", "理解重点词句\n40%",
            "归纳文章大意\n80%","分析篇章结构\n60%", "推断隐含信息\n60%", "评价鉴赏文本\n80%"};

    private int[] mPicResIds = new int[]{
            R.drawable.ic_register_timer, R.drawable.ic_register_timer,
            R.drawable.ic_register_timer, R.drawable.ic_register_timer,
            R.drawable.ic_register_timer, R.drawable.ic_register_timer,
    };


    private final ValueAnimator mFristAnimator = new ValueAnimator();
    private final ValueAnimator mSecondAnimator = new ValueAnimator();
    private final ValueAnimator mThridAnimator = new ValueAnimator();
    private final ValueAnimator mFourAnimator = new ValueAnimator();
    private final ValueAnimator mFiveAnimator = new ValueAnimator();
    private final ValueAnimator mSixAnimator = new ValueAnimator();


    public SesameCreditView(Context context) {
        this(context, null);
    }

    public SesameCreditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SesameCreditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        loadPicBitmap();
    }

    /**
     * 获取加载图片
     */
    private void loadPicBitmap() {
        mPicBitmap = new ArrayList<Bitmap>();
        for (int i = 0; i < mPicResIds.length; i++) {
            mPicBitmap.add(BitmapFactory.decodeResource(getResources(),mPicResIds[i]));
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        mLineDefaultPaint.setDither(true);
        mLineDefaultPaint.setAntiAlias(true);
        mLineDefaultPaint.setStrokeWidth(1f);
        mLineDefaultPaint.setColor(Color.parseColor("#D2D2D2"));
        mLineDefaultPaint.setStyle(Paint.Style.STROKE);

        mVisivlePaint.setDither(true);
        mVisivlePaint.setAntiAlias(true);
       // mVisivlePaint.setStrokeWidth(5f);
        mVisivlePaint.setColor(Color.parseColor("#0DD4A6"));
        mVisivlePaint.setAlpha(100);
        mVisivlePaint.setStyle(Paint.Style.FILL);

        mStrokePaint.setDither(true);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStrokeWidth(3f);
        mStrokePaint.setStrikeThruText(true);
        mStrokePaint.setColor(Color.parseColor("#09B78F"));
        mStrokePaint.setAlpha(255);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 大小改变事件监听
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mCenterPoint.set(mCenterX, mCenterY);
        mRadius = mViewWidth > mViewHeight ? mViewHeight  : mViewWidth ;

        mCountRadius = mRadius; //保存一份原始的正六边形半径
        mFristValue = mCountRadius * mDefaultRatio;
        mSecondValue = mCountRadius * mDefaultRatio;
        mThridValue  = mCountRadius * mDefaultRatio;
        mFourValue  = mCountRadius * mDefaultRatio;
        mFiveValue = mCountRadius * mDefaultRatio;
        mSixValue = mCountRadius * mDefaultRatio;


        postDelayed(new Runnable() {
            @Override
            public void run() {
                mFristAnimator.start();
                mSecondAnimator.start();
                mThridAnimator.start();
                mFourAnimator.start();
                mFiveAnimator.start();
                mSixAnimator.start();

            }
        }, 500);
    }
    /**
     * 绘制图形
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        mRadius = mViewWidth > mViewHeight ? mViewHeight  : mViewWidth ;
        //绘制文字
        int mPicValue = mRadius ;
        ArrayList<PointF> mPICDefaultPointF = getPoints(mCenterPoint, mPicValue, mPicValue+mPicAndViewSpacing,
              mPicValue+mPicAndViewSpacing,   mPicValue,mPicValue+mPicAndViewSpacing,
                mPicValue+mPicAndViewSpacing);
        drawBitmap(canvas, mPICDefaultPointF);
        Log.e(">>>>>>>>>>>>>","onDraw>>>>>>>>>>>>>>>");
        int squareSpace = 33;
        /**
         * 绘制六个正六边形
         */
        for(int i=0; i<5; i++) {
            mRadius = mRadius - squareSpace; // 递减缩小正六边形面积
            ArrayList<PointF> mDefaultPointF = getPoints(mCenterPoint, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius);
            ArrayList<Path> mDefaultPath = getPaths(mCenterPoint, mDefaultPointF,false);
            drawView(canvas, mDefaultPath, false);

            ArrayList<PointF> mLineDefaultPointF = getPoints(mCenterPoint, mRadius, mRadius, mRadius, mRadius, mRadius, mRadius);
            ArrayList<Path> mLineDefaultPath = getPaths(mCenterPoint, mLineDefaultPointF,false);
            drawView(canvas, mLineDefaultPath, false);
        }
        Log.e(">>>>>>>>>>>>>","mRadius:"+mRadius);

        //绘制显示色块，初始半径的五分之一
        ArrayList<PointF> mVisivlePointF = getPoints(mCenterPoint, mFristValue, mSecondValue,
                mThridValue, mFourValue, mFiveValue,mSixValue);
        ArrayList<Path> mVisivlePath = getPaths(mCenterPoint, mVisivlePointF,true);
        ArrayList<PointF> mStrokePointF = getPoints(mCenterPoint, mFristValue, mSecondValue,
                mThridValue, mFourValue, mFiveValue,mSixValue);
        ArrayList<Path> mStrokePath = getPaths(mCenterPoint, mStrokePointF,false);
        for (int i = 0; i < mVisivlePath.size(); i++) {
            canvas.drawPath(mVisivlePath.get(i), mVisivlePaint);
        }
        for (int i = 0; i < mStrokePath.size(); i++) {
            canvas.drawPath(mStrokePath.get(i), mStrokePaint);
        }
    }

    /**
     * 绘制描述文字
     * @param canvas
     * @param mPICDefaultPointF
     */
    private void drawBitmap(Canvas canvas, ArrayList<PointF> mPICDefaultPointF) {
        if (mPicBitmap == null || mPicBitmap.size()==0) {
            return;
        }
        if (mPICDefaultPointF == null || mPICDefaultPointF.size() == 0) {
            return;
        }
        mPicAreas.clear();
        for (int i = 0; i < mStringResIds.length; i++) {
            PointF point = mPICDefaultPointF.get(i);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.parseColor("#000000"));
            textPaint.setTextSize(20);
            textPaint.setAntiAlias(true);
            StaticLayout layout = new StaticLayout(mStringResIds[i], textPaint, 200,
                    Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
            // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
            canvas.save();
            canvas.translate(point.x -70 - mPicAndViewSpacing,point.y - mPicAndViewSpacing);//从100，100开始画
            layout.draw(canvas);
            canvas.restore();
            //画图形
            // canvas.drawBitmap(bitmap, point.x - bitmap.getWidth() / 2, point.y - bitmap.getHeight() / 2, mBitmapPaint);
        }

    }

    /**
     * 图形的绘制方法
     * @param mCanvas
     * @param paths
     * @param isDrowCentre
     */
    private void drawView(Canvas mCanvas, ArrayList<Path> paths, boolean isDrowCentre) {
        if (paths == null || paths.size() == 0) {
            return;
        }
        for (int i = 0; i < paths.size(); i++) {
            if(isDrowCentre){
                mCanvas.drawPath(paths.get(i), mVisivlePaint);
            }else {
                mCanvas.drawPath(paths.get(i), mLineDefaultPaint);
            }
        }
    }

    /**
     * 根据点来画线
     * @param center
     * @param points
     * @param mIsCentre
     * @return
     */
    private ArrayList<Path> getPaths(Point center, ArrayList<PointF> points,boolean mIsCentre) {
        if (points == null || points.size() == 0) {
            return null;
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        for (int i = 0; i < points.size(); i++) {
            Path path = new Path();
            path.reset();
            path.moveTo(points.get(i).x, points.get(i).y);
            if(mIsCentre) {
                 path.lineTo(center.x, center.y); //画链接中心点的线
            }
            path.lineTo(points.get(i == points.size() - 1 ? 0 : i + 1).x, points.get(i == points.size() - 1 ? 0 : i + 1).y);
            path.close();
            paths.add(path);
        }
        return paths;
    }

    /**
     * 获取各个点,从最上面开始，顺时针方向
     *
     */
    private ArrayList<PointF> getPoints(Point center, float fristPoint, float secondPoint,
                                        float thridPoint, float fourPoint, float fivePoint, float sixPoint) {
        ArrayList<PointF> points = new ArrayList<PointF>();

        points.add(new PointF(center.x, toFloat(center.y - fristPoint))); //最上面的点

        points.add(new PointF(toFloat(center.x + Math.sin(Math.toRadians(60D)) * secondPoint),
                toFloat(center.y - Math.cos(Math.toRadians(60d)) * secondPoint)));//右边第一个点

        points.add(new PointF(toFloat(center.x + Math.cos(Math.toRadians(30D)) * thridPoint),
                toFloat(center.y + Math.sin(Math.toRadians(30d)) * thridPoint))); //右边第二 个点

        points.add(new PointF(center.x, toFloat(center.y + fourPoint)));// 最底部的点

        points.add(new PointF(toFloat(center.x - Math.cos(Math.toRadians(30D)) * fivePoint),
                toFloat(center.y + Math.sin(Math.toRadians(30d)) * fivePoint))); // 左边第一个点


        points.add(new PointF(toFloat(center.x - Math.sin(Math.toRadians(60D)) * sixPoint),
                toFloat(center.y - Math.cos(Math.toRadians(60d)) * sixPoint))); // 左边第二个点

        return points;
    }
    private float mFristPoint, mSecondPoint,mThirdPoint, mFourPoint, mFivePoint, mSixPoint;
    /**
     * 设置正六边形的动画效果图
     * 数值 0 < point < 0.85, 大于0。85会出现越界问题
     */
    public void setViewAnimatorFinalValue(float fristPoint, float secondPoint, float thridPont,
                                          float  fourPoint, float fivePoint,  float sixPoint){
        this.mFristPoint = fristPoint;
        this.mSecondPoint = secondPoint;
        this.mThirdPoint = thridPont;
        this.mFourPoint = fourPoint;
        this.mFivePoint = fivePoint;
        this.mSixPoint = sixPoint;
        initAnimator();
    }
    /**
     * 注册动画效果
     */
    private void initAnimator() {

        mFristAnimator.setFloatValues(mDefaultRatio, (mFristPoint < 0.85f ? mFristPoint: 0.85f));
        mSecondAnimator.setFloatValues(mDefaultRatio, (mSecondPoint< 0.85f ? mSecondPoint: 0.85f));
        mThridAnimator.setFloatValues(mDefaultRatio, (mThirdPoint< 0.85f ? mThirdPoint: 0.85f));
        mFourAnimator.setFloatValues(mDefaultRatio, (mFourPoint< 0.85f ? mFourPoint: 0.85f));
        mFiveAnimator.setFloatValues(mDefaultRatio, (mFivePoint < 0.85f ? mFivePoint: 0.85f));
        mSixAnimator.setFloatValues(mDefaultRatio, (mSixPoint< 0.85f ? mSixPoint: 0.85f));

        mFristAnimator.setDuration(1000L);
        mSecondAnimator.setDuration(1000L);
        mThridAnimator.setDuration(1000L);
        mFourAnimator.setDuration(1000L);
        mFiveAnimator.setDuration(1000L);
        mSixAnimator.setDuration(1000L);

        mFristAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 mFristValue = (Float) animation.getAnimatedValue() * mCountRadius;
               // InvalidateView();
            }
        });
        mSecondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                  mSecondValue = (Float) animation.getAnimatedValue() * mCountRadius;
                //InvalidateView();
            }
        });
        mThridAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 mThridValue = (Float) animation.getAnimatedValue() * mCountRadius;
               // InvalidateView();
            }
        });
        mFourAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFourValue = (Float) animation.getAnimatedValue() * mCountRadius;
               // InvalidateView();
            }
        });
        mFiveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFiveValue = (Float) animation.getAnimatedValue() * mCountRadius;
                InvalidateView();
            }
        });
        mSixAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSixValue = (Float) animation.getAnimatedValue() * mCountRadius;
                InvalidateView();
            }
        });
    }

    public float toFloat(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.floatValue();
    }

    /**
     * 刷新界面
     */
    public void InvalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}