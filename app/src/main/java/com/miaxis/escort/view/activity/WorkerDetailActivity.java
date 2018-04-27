package com.miaxis.escort.view.activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IWorkerDetailPresenter;
import com.miaxis.escort.presenter.WorkerDetailPresenter;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.fragment.UpTaskFragment;
import com.miaxis.escort.view.viewer.IWorkerDetailView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WorkerDetailActivity extends BaseActivity implements IWorkerDetailView{

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
    @BindView(R.id.tv_first_collect)
    TextView tvFirstCollect;
    @BindView(R.id.tv_second_collect)
    TextView tvSecondCollect;

    private int flag;
    private WorkerBean workerBean;

    private IWorkerDetailPresenter workerDetailPresenter;
    private MaterialDialog materialDialog;

    private String finger1 = "";
    private String finger2 = "";

    @Override
    protected int setContentView() {
        return R.layout.activity_worker_detail;
    }

    @Override
    protected void initData() {
        workerDetailPresenter = new WorkerDetailPresenter(this, this);
        flag = getIntent().getIntExtra(StaticVariable.FLAG, 0);
        if (flag == 1) {
            workerBean = new WorkerBean();
        } else {
            workerBean = (WorkerBean) getIntent().getSerializableExtra(StaticVariable.WORKER);
        }
    }

    @Override
    protected void initView() {
        materialDialog = new MaterialDialog.Builder(this)
                .title("请稍后...")
                .content("正在上传中...")
                .progress(true,100)
                .cancelable(false)
                .build();
        //区分查看和新增操作员页面（其实干嘛不做两个呢？）
        if (flag == 2) {
            tvFirstCollect.setText("已采集");
            tvSecondCollect.setText("已采集");
            etWorkerCode.setEnabled(false);
            etWorkerCode.setText(workerBean.getWorkno());
            etWorkerName.setEnabled(false);
            etWorkerName.setText(workerBean.getName());
            llFirstFingerPrint.setClickable(false);
            llFirstFingerPrint.setFocusable(false);
            llSecondFingerPrint.setClickable(false);
            llSecondFingerPrint.setFocusable(false);
        }
        if (flag == 1 || flag == 2) {
            toolbar.setTitle("新增操作员");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            RxView.clicks(btnAddWorkerFinish)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(final Object o) throws Exception {
                            new MaterialDialog.Builder(WorkerDetailActivity.this)
                                    .title("确认添加？")
                                    .negativeText("取消")
                                    .positiveText("确认")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            if (checkNotNull()) {
                                                if (!workerDetailPresenter.isDuplicate(etWorkerCode.getText().toString())) {
                                                    Toasty.error(WorkerDetailActivity.this, "员工编号重复", 0, true).show();
                                                } else {
                                                    Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                                                    WorkerBean opUser = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
                                                    workerBean.setDeptno(config.getOrgCode());
                                                    workerBean.setWorkno(etWorkerCode.getText().toString());
                                                    workerBean.setName(etWorkerName.getText().toString());
                                                    workerBean.setFinger0(finger1);
                                                    workerBean.setFinger1(finger2);
                                                    workerBean.setOpdate(DateUtil.getToday());
                                                    workerBean.setOpuser(opUser.getWorkno());
                                                    workerBean.setOpusername(opUser.getName());
                                                    workerDetailPresenter.addWorker(workerBean);
                                                    materialDialog.show();
                                                }
                                            } else {
                                                Toasty.error(WorkerDetailActivity.this, "请填写相关信息", 0, true).show();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    });
            if (flag != 2) {
                RxView.clicks(llFirstFingerPrint)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Intent intent = new Intent(WorkerDetailActivity.this, FingerActivity.class);
                                intent.putExtra("finger1", StaticVariable.FINGER1ST);
                                startActivityForResult(intent, StaticVariable.FINGER1ST);
                            }
                        });
                RxView.clicks(llSecondFingerPrint)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .compose(this.bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Intent intent = new Intent(WorkerDetailActivity.this, FingerActivity.class);
                                intent.putExtra("finger2", StaticVariable.FINGER2ND);
                                startActivityForResult(intent, StaticVariable.FINGER2ND);
                            }
                        });
            }
        } else if (flag == 0) {
            toolbar.setTitle("操作员详情");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tvFirstCollect.setText("已采集");
            tvSecondCollect.setText("已采集");
            etWorkerCode.setEnabled(false);
            etWorkerCode.setText(workerBean.getWorkno());
            etWorkerName.setEnabled(false);
            etWorkerName.setText(workerBean.getName());
            btnAddWorkerFinish.setText("删除");
            btnAddWorkerFinish.setBackgroundColor(getResources().getColor(R.color.crimson));
            llFirstFingerPrint.setClickable(false);
            llFirstFingerPrint.setFocusable(false);
            llSecondFingerPrint.setClickable(false);
            llSecondFingerPrint.setFocusable(false);
            RxView.clicks(btnAddWorkerFinish)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(final Object o) throws Exception {
                            String content = "";
                            if (workerDetailPresenter.isSelf()) {
                                content = "    确认删除自身？" + "\n" + "    删除后将退出登陆";
                            } else {
                                content = "    确认删除？";
                            }
                            new MaterialDialog.Builder(WorkerDetailActivity.this)
                                    .title("确认")
                                    .content(content)
                                    .negativeText("取消")
                                    .positiveText("确认")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            materialDialog.show();
                                            workerDetailPresenter.deleteWorker(workerBean.getWorkno());
                                        }
                                    })
                                    .show();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            switch (requestCode) {
                case StaticVariable.FINGER1ST:
                    finger1 = data.getStringExtra("finger");
                    tvFirstCollect.setText("已采集");
                        break;
                case StaticVariable.FINGER2ND:
                    finger2 = data.getStringExtra("finger");
                    tvSecondCollect.setText("已采集");
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!checkEmpty() && flag == 1) {
            new MaterialDialog.Builder(WorkerDetailActivity.this)
                    .title("确认退出？")
                    .negativeText("取消")
                    .positiveText("确认")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .show();
        } else {
            finish();
        }
    }

    private boolean checkNotNull() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (etWorkerCode.getText().toString().equals("") || etWorkerName.getText().toString().equals("")) {
            return false;
        }
        if ("".equals(finger1) || "".equals(finger2)) {
            return false;
        }
        return true;
    }

    private boolean checkEmpty() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (etWorkerCode.getText().toString().equals("") && etWorkerName.getText().toString().equals("")) {
            return true;
        }
        if ("".equals(finger1) && "".equals(finger2)) {
            return true;
        }
        return false;
    }

    @Override
    public void addWorkerSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "添加成功", 0, true).show();
        finish();
    }

    @Override
    public void addWorkerFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(EscortApp.getInstance().getApplicationContext(), message, 0, true).show();
    }

    @Override
    public void delWorkerSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "删除成功", 0, true).show();
        finish();
    }

    @Override
    public void delWorkerFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(EscortApp.getInstance().getApplicationContext(), message, 0, true).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workerDetailPresenter.doDestroy();
    }
}
