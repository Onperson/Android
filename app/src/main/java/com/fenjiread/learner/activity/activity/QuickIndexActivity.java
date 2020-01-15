package com.fenjiread.learner.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.model.Person;
import com.fenjiread.learner.activity.widget.QuickIndexView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuickIndexActivity extends AppCompatActivity {

        //实例化
        private ListView lv_main;
        private TextView tv_word;
        private QuickIndexView iv_words;

        private Handler handler = new Handler(); //用于隐藏切换后的字母,在主线程中运行

        /**
         * 联系人集合
         */
        private ArrayList<Person> persons;

        private IndexAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quick_index);

            //实例化
            lv_main= (ListView)findViewById(R.id.lv_main);
            tv_word= (TextView)findViewById(R.id.tv_word);
            iv_words= (QuickIndexView) findViewById(R.id.iv_words);

//        //方法一：设置监听字母下标索引的变化
//        iv_words.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {  //写的内部类(OnIndexChangeListener)
//            /**
//             *
//             * @param word 字母（A~Z）
//             */
//            @Override
//            public void onIndexChange(String word) {
//                updateWord(word);
//
//            }
//        });

            //设置监听字母下标索引的变化
           iv_words.setOnIndexChangeListener(new MyOnIndexChangeListener());

            //准备数据
            initData();

            //设置适配器
            adapter = new IndexAdapter();  //最好适配器new成变量,以后就不用改了
            lv_main.setAdapter(adapter);
        }

     public class IndexAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return persons.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null){
                    convertView = View.inflate(QuickIndexActivity.this,R.layout.layout_english_index,null);

                    viewHolder = new ViewHolder();
                    viewHolder.tv_word = (TextView)convertView.findViewById(R.id.tv_word);
                    viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
                    convertView.setTag(viewHolder);  //有set就有get
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                //得到姓名和拼音
                String name = persons.get(position).getName(); //阿福
                String word = persons.get(position).getPinyin().substring(0,1); //AFU变成A(substring(0,1),截取只剩下一个A
                viewHolder.tv_word.setText(word);
                viewHolder.tv_name.setText(name);

                if (position == 0){  //若每种信息字母的第一行显示
                    viewHolder.tv_word.setVisibility(View.VISIBLE); //显示
                }else {
                    //得到前一个位置对应的字母，如果当前的字母和上一个相同，隐藏TextView；否则就显示
                    String preWord = persons.get(position - 1).getPinyin().substring(0, 1); //得到上一个字母A~Z
                    if (word.equals(preWord)){  //若word和preWord相同
                        viewHolder.tv_word.setVisibility(View.GONE); //隐藏
                    }else {
                        viewHolder.tv_word.setVisibility(View.VISIBLE); //显示
                    }
                }

                return convertView;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        }

        //优化
        static class ViewHolder{
            TextView tv_word;
            TextView tv_name;
        }

        //方法二：设置监听字母下标索引的变化
   public class MyOnIndexChangeListener implements QuickIndexView.OnIndexChangeListener {
            /**
             * @param word 字母（A~Z）
             */
            @Override
            public void onIndexChange(String word) {
                updateWord(word);
                updateListView(word);//A～Z字母
            }
        }

        private void updateListView(String word) {
            for (int i =0;i<persons.size();i++){
                String listWord = persons.get(i).getPinyin().substring(0,1); //YANGGUANGFU-(转成)->Y
                if (word.equals(listWord)){
                    //i是ListView中的位置
                    lv_main.setSelection(i);  //定位到ListVeiw中的某个位置
                    return;
                }
            }
        }

        private void updateWord(String word) {
            //显示
            tv_word.setVisibility(View.VISIBLE);
            tv_word.setText(word);
            handler.removeCallbacksAndMessages(null); //先把每次的消息移除
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //因为handler在主线程中运行，Runnable方法也是运行在主线程
                    //（打日志System.out.println是判断Runnable在哪个线程中运行，出现main证明就是在主线程中运行）
                    System.out.println(Thread.currentThread().getName() +"-------------------");
                    tv_word.setVisibility(View.GONE); ////3秒后隐藏
                }
            },3000); //3秒后隐藏
        }

        /**
         * 初始化数据
         */
        private void initData() {

            persons = new ArrayList<>();
            persons.add(new Person("张小光"));  //将人名添加到集合中
            persons.add(new Person("杨大雷"));
            persons.add(new Person("胡继开"));
            persons.add(new Person("刘三"));

            persons.add(new Person("钟兴"));
            persons.add(new Person("尹顺"));
            persons.add(new Person("安杰"));
            persons.add(new Person("张骞"));

            persons.add(new Person("温小松"));
            persons.add(new Person("李凤"));
            persons.add(new Person("杜甫"));
            persons.add(new Person("娄志超"));
            persons.add(new Person("张飞"));

            persons.add(new Person("王杰"));
            persons.add(new Person("李三"));
            persons.add(new Person("孙二娘"));
            persons.add(new Person("唐小雷"));
            persons.add(new Person("牛二"));
            persons.add(new Person("姜光刃"));

            persons.add(new Person("刘能"));
            persons.add(new Person("张四"));
            persons.add(new Person("张五"));
            persons.add(new Person("侯大帅"));
            persons.add(new Person("刘洪"));

            persons.add(new Person("乔三"));
            persons.add(new Person("徐达健"));
            persons.add(new Person("吴洪亮"));
            persons.add(new Person("王兆雷"));

            persons.add(new Person("阿四"));
            persons.add(new Person("李洪磊"));


            //排序
            Collections.sort(persons, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return lhs.getPinyin().compareTo(rhs.getPinyin());  //根据拼音排序
                }
            });
        }
}
