package com.fenjiread.learner.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.fenjiread.learner.activity.widget.DayRecommendLayout;
import com.fenjiread.learner.R;

/**
 * 翻转卡片的动画效果
 */
public class FlipCradActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);
        DayRecommendLayout mDayRecomment = findViewById(R.id.drl_day_recommend);
        findViewById(R.id.btn_flip_card).setOnClickListener(v -> {
            mDayRecomment.flipCard();
        });
    }
}
