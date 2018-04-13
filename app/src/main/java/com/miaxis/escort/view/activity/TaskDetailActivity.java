package com.miaxis.escort.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchEscortAdapter;
import com.miaxis.escort.adapter.TaskDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskDetailActivity extends BaseActivity {

    @BindView(R.id.task_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_task_detail)
    RecyclerView rvTaskDetail;

    private TaskDetailAdapter taskDetailAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("任务详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<String> dataList = new ArrayList<>();
        dataList.add("箱包1");
        dataList.add("箱包2");
        dataList.add("箱包3");
        dataList.add("箱包4");
        dataList.add("箱包5");
        dataList.add("箱包6");
        taskDetailAdapter = new TaskDetailAdapter(this, dataList);
        rvTaskDetail.setAdapter(taskDetailAdapter);
        rvTaskDetail.setLayoutManager(new LinearLayoutManager(this));
        taskDetailAdapter.setOnItemClickListener(new TaskDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
