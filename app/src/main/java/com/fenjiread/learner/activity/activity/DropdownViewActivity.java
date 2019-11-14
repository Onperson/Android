package com.fenjiread.learner.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fenjiread.learner.R;
import com.fenjiread.learner.activity.adapter.RecycAdapter;
import com.fenjiread.learner.activity.widget.DropdownLayout;


public class DropdownViewActivity extends AppCompatActivity {

    private DropdownLayout mDropdownView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_slide_menu);

        mDropdownView = (DropdownLayout) findViewById(R.id.dropdown_layout);

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        RecycAdapter recycAdapter = new RecycAdapter(getBaseContext());
        mRecyclerView.setAdapter(recycAdapter);

    }

    public void onClick(View v){
        mDropdownView.toggle( mRecyclerView);
    }
}
