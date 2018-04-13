package com.miaxis.escort.view.activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.view.fragment.UpTaskFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WorkerDetailActivity extends BaseActivity {

    @BindView(R.id.worker_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_worker_code)
    EditText etWorkerCode;
    @BindView(R.id.et_worker_name)
    EditText etWorkerName;
    @BindView(R.id.ll_first_finger_print)
    LinearLayout llFirstFingerPrint;
    @BindView(R.id.ll_second_finger_print)
    LinearLayout llSecondFingerPrint;
    @BindView(R.id.btn_add_worker_finish)
    Button btnAddWorkerFinish;

    @Override
    protected int setContentView() {
        return R.layout.activity_worker_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("新增操作员");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RxView.clicks(btnAddWorkerFinish)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        new MaterialDialog.Builder(WorkerDetailActivity.this)
                                .title("确认添加？")
                                .negativeText("取消")
                                .positiveText("确认")
                                .show();
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
