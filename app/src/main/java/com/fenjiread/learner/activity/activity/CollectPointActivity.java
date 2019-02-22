package com.fenjiread.learner.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.fenjiread.learner.activity.model.WordEnergy;
import com.fenjiread.learner.activity.widget.CollectWordsLayout;
import com.fenjiread.learner.R;

import java.util.ArrayList;

/**
 * 仿蚂蚁森林收集水滴效果的动画
 */
public class CollectPointActivity extends AppCompatActivity{

    private CollectWordsLayout mCollectWordsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_point);
        mCollectWordsLayout = findViewById(R.id.cwl_collect_word);
        addWordsEnergyToScore();

    }
    /**
     * 增加词能计分
     */
    private void addWordsEnergyToScore() {
        ArrayList<WordEnergy> scoreList = new ArrayList<>();
        for(int i =0; i<18; i++){
            WordEnergy wordEnergy = new WordEnergy("凤毛麟角", 1);
            scoreList.add(wordEnergy);
        }
        mCollectWordsLayout.setWordsEnergyAndTotalScore(scoreList,0);
    }
}
