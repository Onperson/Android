package com.fenjiread.learner.activity.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.fenjiread.learner.activity.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 动态计分的TextView
 * @author guotianhui
 */
public class CountScoreTextView extends AppCompatTextView{

    int scoreIndex = 0; //集合取值的角标
    private Timer mTimer; //定时计数器
    private int TOTAL_SCORE = 0; //获取的总词能
    private ArrayList<Integer> mScoreList; //词能集合
    private OnFinishCollectWords mOnFinishCollectListener;


    public CountScoreTextView(Context context) {
        super(context);
    }

    public CountScoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountScoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 创建一个Handler,不断发送消息让Textview的分数累加，直到完成
     */
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    if(msg.arg1 >0) {
                        int score = TOTAL_SCORE + msg.arg1;
                        setText(String.valueOf(score));
                        TOTAL_SCORE = score;
                        if(ObjectUtils.isNotEmpty(mOnFinishCollectListener)){  //回传总词能
                            mOnFinishCollectListener.onFinishCollect(String.valueOf(score));
                        }
                    }
                    break;
                default:{

                        }
                        break;
            }
        }
    };

    /**
     * 创建一个定时任务，循环把集合中的数值取出，并发送消息
     */
    public void startCountScore(){
        if(mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(mScoreList != null && mScoreList.size() >= scoreIndex) {
                        final Message message = new Message();
                        message.what = 100;
                        message.arg1 = getCountScoreListItem(scoreIndex);
                        mHandler.sendMessage(message);
                        scoreIndex++;
                    }else{//取完所有数值之后，停止计数并清空集合和重置角标
                        stopCountScore();
                        scoreIndex =0;
                    }
                }
            }, 0, 30);
        }
    }
    /**
     * 提供一个方法接收需要添加的数字集合
     */
    public void setCountScoreList(ArrayList<Integer> scoreList){
         this.mScoreList = scoreList;
         startCountScore();
    }

    /**
     * 根据角标获取到集合中的能量值并返回
     * @param scoreIndex
     * @return
     */
    public int getCountScoreListItem(int scoreIndex) {
      if(mScoreList!=null && mScoreList.size() > scoreIndex){
          return mScoreList.get(scoreIndex);
       }else {
          return 0;
      }
    }
    /**
     * 设置计数的起始分数
     * @param totalScore
     */
    public void setTotalScore(int totalScore) {
        this.TOTAL_SCORE = totalScore;
        setText(String.valueOf(TOTAL_SCORE));
    }

    /**
     * 停止计数
     */
    public void stopCountScore(){
        if(ObjectUtils.isNotEmpty(mTimer)) {
            mTimer.cancel();
            mTimer = null; //把计数器释放，下次进来重新创建
        }
    }
    /**
     * 提供一个方法设置回调监听
     */
    public void setOnFinishCollectListener(OnFinishCollectWords listener){
        this.mOnFinishCollectListener = listener;
    }
    /**
     * 提供一个回调接口，收集完词能翻转卡片
     */
    public interface OnFinishCollectWords{
         void onFinishCollect(String totalScord);
    }
}
