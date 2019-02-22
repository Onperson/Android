package com.fenjiread.learner.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.fenjiread.learner.activity.model.RegisterItem;
import com.fenjiread.learner.R;

import java.util.ArrayList;

/**
 * 每日签到的Adapter
 * @author guotianhui
 */
public class DayRegisterAdapter extends BaseQuickAdapter<RegisterItem,BaseViewHolder> {

    private final ArrayList<RegisterItem> mDataList;

    public DayRegisterAdapter(int layoutResId, @Nullable ArrayList<RegisterItem> data) {
        super(layoutResId, data);
        this.mDataList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RegisterItem item) {
        helper.setText(R.id.tv_register_day,item.getRegisterDay());
        AppCompatTextView tvTowDot = helper.getView(R.id.view_line_one);
        if(item.isRegister()){
            helper.setImageResource(R.id.iv_register_image,R.drawable.ic_light_book);
        }else{
            helper.setImageResource(R.id.iv_register_image,R.drawable.ic_gray_book);
        }
        if(item.isShowTwoDot()){
            tvTowDot.setVisibility(View.VISIBLE);
        }else{
            tvTowDot.setVisibility(View.GONE);
        }
    }
}