package com.fenjiread.learner.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fenjiread.learner.activity.widget.tagcloudview.TagsAdapter;
import com.fenjiread.learner.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 词云标签球Adapter
 * @author guotianhui
 */
public class TagCloudAdapter extends TagsAdapter {

    private final Context mContext;
    private final List<String> dataSet = new ArrayList<>();

    public TagCloudAdapter(Context context, @NonNull String... data) {
        dataSet.clear();
        this.mContext = context;
        Collections.addAll(dataSet, data);
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        View tagCloudItem = View.inflate(context, R.layout.layout_cloud_tag, null);
        AppCompatTextView mTvTagCloudText = tagCloudItem.findViewById(R.id.tv_tag_cloud_text);
        LinearLayout mTagCloudTips = tagCloudItem.findViewById(R.id.ll_tag_cloud_tips);
        View mTagDat = tagCloudItem.findViewById(R.id.view_tag_dat);
        View rootView = tagCloudItem.getRootView();
        rootView.setOnLongClickListener((v) -> {
                if(mTagCloudTips.getVisibility() ==View.VISIBLE){
                    mTagCloudTips.setVisibility(View.GONE);
                }else{
                    mTagCloudTips.setVisibility(View.VISIBLE);
                }
                return true;
        });
        return tagCloudItem;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor, float alpha) {

    }
    /**
     * 获取新增加的词能到云标签球上
     */
    public void updataTagsCloudToShow(List<String> dataList){
        this.dataSet.addAll(dataList);
        notifyDataSetChanged();
    }
}
