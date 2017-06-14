package com.etouse.swipelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MAdapter.OnDeleteButtonClickListener, MAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private List<String> mDatas = new ArrayList<>();
    private MAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MAdapter(MainActivity.this, mDatas);
        adapter.setOnDeleteButtonClickListener(this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        mDatas.add("WebView性能、体验分析与优化");
        mDatas.add("我赌5毛你没有见过这样的SpannableString");
        mDatas.add("控件架构与自定义控件");
        mDatas.add("网络请求异常处理");
        mDatas.add("模仿英雄联盟皮肤浏览效果");
        mDatas.add("让你的Kolin代码远离");
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDeleteClicked(int position, List<String> mDatas) {
        Toast.makeText(MainActivity.this, "点击了删除按钮，位置为：" + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position, List<String> mDatas) {
        Toast.makeText(MainActivity.this, "点击的条目：" + position,Toast.LENGTH_SHORT).show();
    }
}
