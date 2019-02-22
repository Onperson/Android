package com.fenjiread.learner.activity.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.fenjiread.learner.R;


/**
 * 词云养成的词布局
 * @author guotianhui
 */
public class WordsItemLayout extends ConstraintLayout {

    private final Context mContext;
    private AppCompatTextView mCollectWord;
    private AppCompatTextView mWordsEnergy;

    public WordsItemLayout(Context context) {
        super(context);
        this.mContext = context;
        initWordsItemLayout();
    }

    public WordsItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWordsItemLayout();
    }

    public WordsItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initWordsItemLayout();
    }


    private void initWordsItemLayout(){
        View view = View.inflate(mContext, R.layout.layout_words_item, null);
        mCollectWord = view.findViewById(R.id.tv_collect_word);
        mWordsEnergy = view.findViewById(R.id.tv_words_energy);
        addView(view);
    }

    /**
     * 提供一个方法，更改文字
     */
    public void setCollectWord(String word){
        mCollectWord.setText(word);
    }
    /**
     * 提供一个方法，更改文字能量
     */
    public void setCollectWordEnergy(int wordEnergy){

        mWordsEnergy.setText(String.valueOf(wordEnergy).concat("+"));
    }
    /**
     * 提供一个方法，更改词能背景色
     */
    public void setWordEnergyBackground(Drawable backGroundColor){
        mWordsEnergy.setBackgroundDrawable(backGroundColor);
    }
}
