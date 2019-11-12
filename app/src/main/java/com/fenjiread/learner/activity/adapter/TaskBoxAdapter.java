package com.fenjiread.learner.activity.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fenjiread.learner.R;

/**
 * RecyclerView 的 Adapter
 */
public  class TaskBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String[] mDatas;
    private Context mContext;

    public TaskBoxAdapter(Context context,String[] mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_recycler, null);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RelativeLayout.LayoutParams paramLayout = new RelativeLayout.LayoutParams(-2,-2);

        if(position ==0 && holder.getAdapterPosition() ==0 ){
            paramLayout.leftMargin = 300;

        }else if(position ==mDatas.length -1 && holder.getAdapterPosition() ==mDatas.length -1 ){
            paramLayout.leftMargin = 130;
            holder.itemView.setVisibility(View.INVISIBLE);
        }else{
            if(position >0) {
                paramLayout.leftMargin = 0;
                holder.itemView.setVisibility(View.VISIBLE);
            }
        }

        holder.itemView.setLayoutParams(paramLayout);
        AppCompatImageView iv = holder.itemView.findViewById(R.id.iv_pic);
        AppCompatTextView tv = holder.itemView.findViewById(R.id.tv_title);
        iv.setImageResource(R.drawable.ic_normal_box);
        tv.setText(mDatas[position]);
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
