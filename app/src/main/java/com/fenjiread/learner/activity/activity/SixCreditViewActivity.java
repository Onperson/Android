package com.fenjiread.learner.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.widget.SesameCreditView;

/**
 * 仿支付宝信用评分体系正六边形
 */
public class SixCreditViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_credit_view);

        SesameCreditView creditView = findViewById(R.id.sesame_credit_view);

        creditView.setViewAnimatorFinalValue(0.4f,0.5f, 0.85f, 0.5f
        ,0.31f,0.52f);
    }
}
