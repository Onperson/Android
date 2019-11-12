package com.fenjiread.learner.activity.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.adapter.TaskBoxAdapter;
import com.fenjiread.learner.activity.widget.TaskboxRecyclerView;

public class ScrollingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        TaskboxRecyclerView mRecycleView =  findViewById(R.id.rv);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        String[] names = new String[20];
        for (int i = 0; i < names.length; i++) {
            names[i] = "任务" + (i + 1);
        }
        TaskBoxAdapter mAdapter = new TaskBoxAdapter(getBaseContext(),names);
        mRecycleView.setAdapter(mAdapter);
    }


}