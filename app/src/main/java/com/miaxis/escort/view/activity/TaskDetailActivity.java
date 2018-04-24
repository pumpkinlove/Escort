package com.miaxis.escort.view.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchEscortAdapter;
import com.miaxis.escort.adapter.TaskDetailAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.presenter.ITaskDetailPresenter;
import com.miaxis.escort.presenter.TaskDetailPresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ITaskDetailView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class TaskDetailActivity extends BaseActivity implements ITaskDetailView{

    @BindView(R.id.task_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_task_detail_type)
    TextView tvTaskDetailType;
    @BindView(R.id.tv_task_detail_status)
    TextView tvTaskDetailStatus;
    @BindView(R.id.tv_task_detail_escort1)
    TextView tvTaskDetailEscort1;
    @BindView(R.id.tv_task_detail_car1)
    TextView tvTaskDetailCar1;
    @BindView(R.id.tv_task_detail_escort2)
    TextView tvTaskDetailEscort2;
    @BindView(R.id.tv_task_detail_worker)
    TextView tvTaskDetailWorker;
    @BindView(R.id.tv_task_detail_date)
    TextView tvTaskDetailDate;
    @BindView(R.id.rv_task_detail)
    RecyclerView rvTaskDetail;
    @BindView(R.id.btn_delete_task)
    Button btnDeleteTask;

    private TaskDetailAdapter taskDetailAdapter;
    private TaskBean taskBean;
    private ITaskDetailPresenter taskDetailPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initData() {
        taskBean = (TaskBean) getIntent().getSerializableExtra("task");
        taskDetailPresenter = new TaskDetailPresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("任务详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTaskDetailType.setText(StaticVariable.getTasktypeName(taskBean.getTasktype(), taskBean.getTasklevel()));
        tvTaskDetailStatus.setText(taskBean.getStatusName());
        taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
        TaskEscortBean taskEscortBean1 = taskBean.getEscortList().get(0);
        TaskEscortBean taskEscortBean2 = taskBean.getEscortList().get(1);
        taskEscortBean1.__setDaoSession(EscortApp.getInstance().getDaoSession());
        taskEscortBean2.__setDaoSession(EscortApp.getInstance().getDaoSession());
        tvTaskDetailEscort1.setText(taskEscortBean1.getEscortBean().getName());
        tvTaskDetailEscort2.setText(taskEscortBean2.getEscortBean().getName());
        tvTaskDetailCar1.setText(taskBean.getCarid());
        tvTaskDetailWorker.setText(taskBean.getOpusername());
        tvTaskDetailDate.setText(taskBean.getTaskdate());
        taskDetailAdapter = new TaskDetailAdapter(this, taskBean.getBoxList());
        rvTaskDetail.setAdapter(taskDetailAdapter);
        rvTaskDetail.setLayoutManager(new LinearLayoutManager(this));
        taskDetailAdapter.setOnItemClickListener(new TaskDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        materialDialog = new MaterialDialog.Builder(TaskDetailActivity.this)
                .title("请稍后...")
                .progress(true, 100)
                .cancelable(false)
                .content("正在删除任务...")
                .build();
        if ("1".equals(taskBean.getStatus()) || "2".equals(taskBean.getStatus())) {
            btnDeleteTask.setVisibility(View.VISIBLE);
            btnDeleteTask.setBackgroundColor(getResources().getColor(R.color.crimson));
            RxView.clicks(btnDeleteTask)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            new MaterialDialog.Builder(TaskDetailActivity.this)
                                    .title("确认删除？")
                                    .negativeText("取消")
                                    .positiveText("确认")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            materialDialog.show();
                                            taskDetailPresenter.deleteTask(taskBean);
                                        }
                                    })
                                    .show();
                        }
                    });
        }
    }

    @Override
    public void deleteSuccess() {
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "删除成功", 0, true).show();
        finish();
    }

    @Override
    public void deleteFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(this, message, 1, true).show();
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
        taskDetailPresenter.doDestroy();
    }
}
