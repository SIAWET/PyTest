package com.example.pytest.app.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pytest.R;
import com.example.pytest.app.base.BaseActivity;
import com.example.pytest.app.base.BaseApplication;
import com.example.pytest.app.util.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class TestActivity extends BaseActivity<TestPresenter> implements TestView, View.OnClickListener {
    private TestPresenter presenter;
    private RecyclerView recyclerView;
    private String roomId;
    private LinearLayout noData;
    private SmartRefreshLayout refreshLayout;
    private TestAdapter adapter;
    private ArrayList<TestBean> list = new ArrayList<>();
    private LogUtil log = LogUtil.getInstance();

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter(this, this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        log.e("还没创建P");
        initData();

    }

    private void initData() {
        presenter = createPresenter();
    }

    private void initView() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        noData = findViewById(R.id.noData);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.draftRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BaseApplication.get());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TestAdapter(BaseApplication.get());
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new TestAdapter.OnTestItemClickListener() {
            @Override
            public void onTestItem(int position) {
                if (list.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("title", list.get(position).getTitle());
                    intent.putExtra("content", list.get(position).getContent());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onDelTestBean(int position) {
                if (list.size() > 0) {
                    presenter.showExitDialog(TestActivity.this, position);
                }
            }
        });
    }

    @Override
    public void delDraft(int position) {

    }
}
