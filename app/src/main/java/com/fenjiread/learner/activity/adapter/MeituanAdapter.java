package com.fenjiread.learner.activity.adapter;

import android.content.Context;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.model.MeiTuanBean;
import com.fenjiread.learner.activity.utils.CommonAdapter;
import com.fenjiread.learner.activity.utils.ViewHolder;

import java.util.List;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class MeituanAdapter extends CommonAdapter<MeiTuanBean> {
    public MeituanAdapter(Context context, int layoutId, List<MeiTuanBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());
    }
}