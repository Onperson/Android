package com.fenjiread.learner.activity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fenjiread.learner.activity.adapter.TagCloudAdapter;
import com.fenjiread.learner.activity.widget.tagcloudview.TagCloudView;
import com.fenjiread.learner.R;

/**
 * 知识星球云标签界面
 */
public class TagCloudActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_cloud);
        TagCloudView mTagCloudView = findViewById(R.id.tcv_tag_cloud_view);
        TagCloudAdapter mTextTagsAdapter = new TagCloudAdapter(getBaseContext(), new String[50]);
        mTagCloudView.setAdapter(mTextTagsAdapter);
    }
}
