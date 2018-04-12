package com.miaxis.escort.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.WorkerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WorkerManageActivity extends BaseActivity {

    @BindView(R.id.worker_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_worker_manage)
    RecyclerView rvWorkerManage;
    @BindView(R.id.btn_add_worker)
    Button btnAddWorker;

    private WorkerAdapter workerAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_worker_manage;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        List<String> dataList = new ArrayList<>();
        dataList.add("操作员1");
        dataList.add("操作员2");
        dataList.add("操作员3");
        dataList.add("操作员4");
        dataList.add("操作员5");
        dataList.add("操作员6");
        workerAdapter = new WorkerAdapter(this, dataList);
        rvWorkerManage.setAdapter(workerAdapter);
        rvWorkerManage.setLayoutManager(new LinearLayoutManager(this));
        workerAdapter.setOnItemClickListener(new WorkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
