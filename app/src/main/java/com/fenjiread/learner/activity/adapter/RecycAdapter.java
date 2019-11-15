package com.fenjiread.learner.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 可以展开的RecyclerView Adapter
 */
public class RecycAdapter  extends RecyclerView.Adapter<DrapDownHolder> {

    private Context mContext;

    public RecycAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public DrapDownHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView  textView = new TextView(mContext);
        textView.setTextSize(18);
        DrapDownHolder drapDownHolder = new DrapDownHolder(textView);

        return drapDownHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrapDownHolder drapDownHolder, int i) {
       TextView itemView = (TextView) drapDownHolder.itemView;
        itemView.setText("我是位置："+i);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}

class DrapDownHolder extends RecyclerView.ViewHolder{

    public DrapDownHolder(@NonNull View itemView) {
        super(itemView);
    }
}