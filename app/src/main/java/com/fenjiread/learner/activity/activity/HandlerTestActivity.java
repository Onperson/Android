package com.fenjiread.learner.activity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.fenjiread.learner.R;

import static java.lang.Thread.sleep;

/**
 * 通过代码示例说明Handler的使用和意义
 * 问题1：安卓中子线程一定不能更改UI吗？ 答：不是，只要不在子线程中做耗时任务操作，安卓子线程是可以更新UI的。
 *
 */
public class HandlerTestActivity  extends AppCompatActivity {

    private AppCompatTextView mLoggerPanel;
    private StringBuffer mStringBuffer = new StringBuffer();
    /**
     *正常使用，在主线程(UI线程)创建一个Handler
     */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    mStringBuffer.append("我自己给自己发消息玩～,延迟一秒\n");
                    mLoggerPanel.setText(mStringBuffer.toString());
                    mHandler.sendEmptyMessageDelayed(100,1000);
                    Log.e(">>>>>>>>>>>>>","我自己给自己发消息玩～,延迟一秒");
                    break;
                    default:
                        break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_handler_test);
         mLoggerPanel = findViewById(R.id.tv_logger_panel);
        /**
         * 使用场景1：通过mHandler自己给自己发消息来实现消息轮询
         */
      //  mHandler.sendEmptyMessage(100);
       Log.e(">>>>>>>>>>>>>","当前线程:"+Thread.currentThread());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 当前线程:Thread[main,5,main]
                //当前子线程:Thread[Thread-3,5,main]
                Log.e(">>>>>>>>>>>>>","当前子线程:"+Thread.currentThread());
            try {
                // android.view.ViewRootImpl$CalledFromWrongThreadException:
                // Only the original thread that created a view hierarchy can touch its views.
                sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //不做耗时操作的情况下，子线程可以更改UI界面
           //  mLoggerPanel.setText("我想在子线程中更改UI界面");
                /**
                 * 通过在子线程中获取mHandler对象，发送消息到主线程来更改UI界面
                 */
                mHandler.sendEmptyMessage(100);

            }
        });
        thread.start();
        androidHandlerUsesViewTwo();
    }
   private Handler mThreadHandler;
    /**
     * 使用场景二： 通过在子进程创建Handler对象，并让另外一个子线程获取到这个Handler对象来进行线程间消息传递
     */
    private void androidHandlerUsesViewTwo(){
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 100;
                message.obj = "今晚一起去打球吧？";
                Log.e(">>>>>>>>>>>>>>","我是另一个子线程,我正和子线程通信");
                mThreadHandler.sendMessage(message);

            }
        });
        new Thread(new Runnable() {
            @SuppressLint("HandlerLeak")
            @Override
            public void run() {
                // java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
                Looper.prepare();
                mThreadHandler = new Handler(){
                     @Override
                     public void handleMessage(Message msg) {
                         super.handleMessage(msg);
                         Log.e(">>>>>>>>>>>","子线程的Handler处理消息");
                         switch (msg.what){
                             case 100:
                                 Log.e(">>>>>>>>>>>>>>","我是子线程的Handler,我正在和另一个子线程通信，它发的消息是："+msg.obj);
                                 break;
                                 default:
                                     break;
                         }
                     }
                 };

                threadTwo.start(); //开启发送消息的线程

                Looper.loop(); //这里是取消息的过程，轮询查询是否有新消息进入
            }

        }).start();


    }
}
