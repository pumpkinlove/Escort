package com.miaxis.escort.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchEscortAdapter;
import com.miaxis.escort.adapter.TaskDetailAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.util.StaticVariable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_task_detail_car2)
    TextView tvTaskDetailCar2;
    @BindView(R.id.tv_task_detail_worker)
    TextView tvTaskDetailWorker;
    @BindView(R.id.tv_task_detail_date)
    TextView tvTaskDetailDate;
    @BindView(R.id.rv_task_detail)
    RecyclerView rvTaskDetail;

    private TaskDetailAdapter taskDetailAdapter;
    private TaskBean taskBean;

    @Override
    protected int setContentView() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initData() {
        taskBean = (TaskBean) getIntent().getSerializableExtra("task");
    }

    @Override
    protected void initView() {
        toolbar.setTitle("任务详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTaskDetailType.setText(StaticVariable.getTasktypeName(taskBean.getTasktype(), taskBean.getTasklevel()));
        tvTaskDetailStatus.setText(taskBean.getStatusName());
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
