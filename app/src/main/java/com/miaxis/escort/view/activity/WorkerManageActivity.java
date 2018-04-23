package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.WorkerAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IWorkerManagePresenter;
import com.miaxis.escort.presenter.WorkerManagePresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IWorkerManageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WorkerManageActivity extends BaseActivity implements IWorkerManageView{

    @BindView(R.id.worker_toolbar)
    Toolbar workerToolbar;
    @BindView(R.id.srl_worker_manage)
    SwipeRefreshLayout srlWorkerManage;
    @BindView(R.id.rv_worker_manage)
    RecyclerView rvWorkerManage;
    @BindView(R.id.btn_add_worker)
    Button btnAddWorker;

    private WorkerAdapter workerAdapter;
    private IWorkerManagePresenter workerManagePresenter;

    @Override
    protected int setContentView() {
        return R.layout.activity_worker_manage;
    }

    @Override
    protected void initData() {
        workerManagePresenter = new WorkerManagePresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        workerToolbar.setTitle("操作员管理");
        setSupportActionBar(workerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        workerAdapter = new WorkerAdapter(this, new ArrayList<WorkerBean>());
        rvWorkerManage.setAdapter(workerAdapter);
        rvWorkerManage.setLayoutManager(new LinearLayoutManager(this));
        workerAdapter.setOnItemClickListener(new WorkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /**
                 * flag 0：已上传查看，1：新增，2：未上传
                 */
                int flag = 0;
                if ("未上传".equals(workerAdapter.getDataList().get(position).getStatus())) {
                    flag = 2;
                }
                Intent intent = new Intent(WorkerManageActivity.this, WorkerDetailActivity.class);
                intent.putExtra(StaticVariable.FLAG, flag);
                intent.putExtra(StaticVariable.WORKER, workerAdapter.getDataList().get(position));
                startActivity(intent);
            }
        });
        srlWorkerManage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlWorkerManage.setRefreshing(true);
                workerManagePresenter.downWorkerList();
            }
        });
        RxView.clicks(btnAddWorker)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(WorkerManageActivity.this, WorkerDetailActivity.class);
                        intent.putExtra(StaticVariable.FLAG, 1);
                        startActivity(intent);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        srlWorkerManage.post(new Runnable(){
            @Override
            public void run() {
                srlWorkerManage.setRefreshing(true);
            }
        });
        workerManagePresenter.downWorkerList();
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
    public void updateDataList(List<WorkerBean> workerBeanList) {
        workerAdapter.setDataList(workerBeanList);
        workerAdapter.notifyDataSetChanged();
        if (srlWorkerManage.isRefreshing()) {
            srlWorkerManage.setRefreshing(false);
            Toasty.success(this, "刷新成功",0, true).show();
            if (!workerManagePresenter.isSelf()) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void downWorkerSuccess() {

    }

    @Override
    public void downWorkerFailed(String message) {
        if (srlWorkerManage.isRefreshing()) {
            srlWorkerManage.setRefreshing(false);
        }
        workerManagePresenter.loadWorkerList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workerManagePresenter.doDestroy();
    }
}
