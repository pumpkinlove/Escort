package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.WorkerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WorkerManageActivity extends BaseActivity {

    @BindView(R.id.worker_toolbar)
    Toolbar workerToolbar;
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
        workerToolbar.setTitle("操作员管理");
        setSupportActionBar(workerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        RxView.clicks(btnAddWorker)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(WorkerManageActivity.this, WorkerDetailActivity.class);
                        startActivity(intent);
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
