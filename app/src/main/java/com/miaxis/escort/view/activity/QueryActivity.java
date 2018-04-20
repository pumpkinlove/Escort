package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class QueryActivity extends BaseActivity {

    @BindView(R.id.query_toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_escort_query)
    LinearLayout llEscortQuery;
    @BindView(R.id.ll_task_query)
    LinearLayout llTaskQuery;
    @BindView(R.id.ll_box_query)
    LinearLayout llBoxQuery;

    @Override
    protected int setContentView() {
        return R.layout.activity_query;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("业务查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(llEscortQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(QueryActivity.this, SearchEscortActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llTaskQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(QueryActivity.this, SearchTaskActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llBoxQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(QueryActivity.this, SearchBoxActivity.class);
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
