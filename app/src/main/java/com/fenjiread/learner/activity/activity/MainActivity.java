package com.fenjiread.learner.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fenjiread.learner.activity.model.BookPayPostInfo;
import com.fenjiread.learner.activity.model.OrederSendInfo;
import com.fenjiread.learner.activity.model.PrepayIdInfo;
import com.fenjiread.learner.activity.utils.NetWorkFactory;
import com.fenjiread.learner.activity.utils.WXpayUtils;
import com.fenjiread.learner.R;
import com.thoughtworks.xstream.XStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private PrepayIdInfo bean;
    private AppCompatButton btn_prepare,btn_pay,btn_book_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_prepare = findViewById(R.id.btn_prepare);
        btn_pay = findViewById(R.id.btn_pay);
        btn_book_pay = findViewById(R.id.btn_book_pay);

        btn_pay.setEnabled(false);

        btn_prepare.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_book_pay.setOnClickListener(this);
        //跳转到翻转卡片的界面
        findViewById(R.id.btn_flip_card).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlipCradActivity.class);
            startActivity(intent);
        });
        //跳转到仿蚂蚁森林的动画
        findViewById(R.id.btn_as_ant_collent).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CollectPointActivity.class);
            startActivity(intent);
        });
        //跳转到知识星球界面
        findViewById(R.id.btn_tag_cloud).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,TagCloudActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_credit_system).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SixCreditViewActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_recycle_view).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_expand_view).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DropdownViewActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_handler_test).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HandlerTestActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_prepare:
                //生成预支付Id

                Date d = new Date();
                System.out.println(d);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateNowStr = sdf.format(d);
                System.out.println("格式化后的日期：" + dateNowStr);

                OrederSendInfo sendInfo = new OrederSendInfo(Constants.APP_ID,Constants.MCH_ID, WXpayUtils.genNonceStr(),"鹅豆-旅游2",dateNowStr,"1","127.0.0.1","www.baidu.com","APP");
                NetWorkFactory.UnfiedOrder(sendInfo, new NetWorkFactory.Listerner() {
                    @Override
                    public void Success(String data) {
                        Toast.makeText(getBaseContext(),"生成预支付Id成功",Toast.LENGTH_LONG).show();
                        btn_pay.setEnabled(true);
                        XStream stream = new XStream();
                        stream.processAnnotations(PrepayIdInfo.class);
                        bean = (PrepayIdInfo) stream.fromXML(data);
                    }

                    @Override
                    public void Faiulre(String data) {
                    }
                });
                break;
            case R.id.btn_pay:
                btn_pay.setEnabled(false );
                Log.e(">>>>>>>>>","bean.getPrepay_id():"+bean.getPrepay_id());
                WXpayUtils.Pay(bean.getPrepay_id(), getApplicationContext());
                break;
            case R.id.btn_book_pay: //调用签约中支付接口
                BookPayPostInfo bookPayPostInfo = new BookPayPostInfo(Constants.APP_ID, Constants.MCH_ID, Constants.MCH_ID, Constants.APP_ID, "123456", "013467007045764"
                        , "5K8264ILTKCH16CQ2502SI8ZNMTM67VS", "Ipad mini 16G 白色", "Ipad mini 16G 白色", "深圳分店", "https:www.baidu.com"
                        , 888, "123.12.12.123", "20091225091010", "20091227091010", "WXG", "JSAPI"
                        , "12235413214070356458058", "no_credit", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o", 123, "100001256"
                        , 1695, "123", "https:www.baidu.com", "E1EE61A91C8E90F299DE6AE075D60A2D");
               NetWorkFactory.UnfiedBookOrder(bookPayPostInfo, new NetWorkFactory.Listerner() {
                   @Override
                   public void Success(String data) {
                       Log.e(">>>>>>>>>>","data:"+data.toString());
                   }

                   @Override
                   public void Faiulre(String data) {
                       Log.e(">>>>>>>>>>","data:"+data.toString());
                   }
               });
                break;
                default:
                    break;
        }
    }
}
