package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchTaskAdapter;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.presenter.ISearchTaskPresenter;
import com.miaxis.escort.presenter.SearchTaskPresenterImpl;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.view.viewer.ISearchTaskView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchTaskActivity extends BaseActivity implements ISearchTaskView{

    @BindView(R.id.search_task_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_search_date)
    TextView tvSearchDate;
    @BindView(R.id.ib_search_last_day)
    ImageButton ibSearchLastDay;
    @BindView(R.id.ib_search_next_day)
    ImageButton ibSearchNextDay;
    @BindView(R.id.rv_search_task)
    RecyclerView rvSearchTask;

    private SearchTaskAdapter searchTaskAdapter;
    private DatePickerPopWin datePickerPopWin;
    private ISearchTaskPresenter searchTaskPresenter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_task;
    }

    @Override
    protected void initData() {
        searchTaskPresenter = new SearchTaskPresenterImpl(this,this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("任务查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvSearchDate.setText(DateUtil.getDayType(DateUtil.getToday()));
        RxView.clicks(tvSearchDate)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        datePickerPopWin = new DatePickerPopWin.Builder(SearchTaskActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                            @Override
                            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                                tvSearchDate.setText(DateUtil.getDayType(dateDesc));
                                searchTaskPresenter.loadTaskByDate(tvSearchDate.getText().toString().substring(0,10));
                            }
                        })
                                .textConfirm("确认") //text of confirm button
                                .textCancel("取消") //text of cancel button
                                .btnTextSize(16) // button text size
                                .viewTextSize(25) // pick view text size
                                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                                .minYear(DateUtil.getYear() - 10) //min year in loop
                                .maxYear(DateUtil.getYear() + 10) // max year in loop
                                .dateChose(tvSearchDate.getText().toString().substring(0,10)) // date chose when init popwindow
                                .build();
                        datePickerPopWin.showPopWin(SearchTaskActivity.this);
                    }
                });
        RxView.clicks(ibSearchLastDay)
                .throttleFirst(250, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        tvSearchDate.setText(DateUtil.getDayType(DateUtil.getLastDay(tvSearchDate.getText().toString().substring(0,10))));
                        searchTaskPresenter.loadTaskByDate(tvSearchDate.getText().toString().substring(0,10));
                    }
                });
        RxView.clicks(ibSearchNextDay)
                .throttleFirst(250, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        tvSearchDate.setText(DateUtil.getDayType(DateUtil.getNextDay(tvSearchDate.getText().toString().substring(0,10))));
                        searchTaskPresenter.loadTaskByDate(tvSearchDate.getText().toString().substring(0,10));
                    }
                });
        searchTaskAdapter = new SearchTaskAdapter(this, new ArrayList<TaskBean>());
        rvSearchTask.setAdapter(searchTaskAdapter);
        rvSearchTask.setLayoutManager(new LinearLayoutManager(this));
        searchTaskAdapter.setOnItemClickListener(new SearchTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchTaskActivity.this, TaskDetailActivity.class);
                intent.putExtra("task", searchTaskAdapter.getDataList().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchTaskPresenter.loadTaskByDate(tvSearchDate.getText().toString().substring(0,10));
    }

    @Override
    public void updateDataList(List<TaskBean> taskBeanList) {
        searchTaskAdapter.setDataList(taskBeanList);
        searchTaskAdapter.notifyDataSetChanged();
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
        searchTaskPresenter.doDestroy();
    }
}
