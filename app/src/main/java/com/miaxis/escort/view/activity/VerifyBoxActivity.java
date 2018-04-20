package com.miaxis.escort.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.VerifyBoxAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IVerifyBoxPresenter;
import com.miaxis.escort.presenter.VerifyBoxPresenterImpl;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IVerifyBoxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class VerifyBoxActivity extends BaseActivity implements IVerifyBoxView {

    @BindView(R.id.verify_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_verify_box)
    RecyclerView rvVerifyBox;
    @BindView(R.id.btn_verify_start)
    Button btnVerifyStart;
    @BindView(R.id.btn_verify_complete)
    Button btnVerifyComplete;
    @BindView(R.id.ll_prompt)
    LinearLayout llPrompt;
    @BindView(R.id.tv_verify_count)
    TextView tvVerifyCount;
    @BindView(R.id.tv_verify_box_count)
    TextView tvVerifyBoxCount;
    @BindView(R.id.tv_unverified_box_count)
    TextView tvUnverifiedBoxCount;

    private TaskBean task;
    private EscortBean escort1st;
    private EscortBean escort2nd;
    private WorkerBean verifyWorker;

    private boolean lock = true;

    private VerifyBoxAdapter verifyBoxAdapter;
    private IVerifyBoxPresenter verifyBoxPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_verify_box;
    }

    @Override
    protected void initData() {
        verifyBoxPresenter = new VerifyBoxPresenterImpl(this, this);
        task = (TaskBean) getIntent().getSerializableExtra("task");
        escort1st = (EscortBean) getIntent().getSerializableExtra("escort1st");
        escort2nd = (EscortBean) getIntent().getSerializableExtra("escort2nd");
        verifyWorker = (WorkerBean) getIntent().getSerializableExtra("verifyWorker");
        verifyBoxPresenter.loadBox(task);
    }

    @Override
    protected void initView() {
        materialDialog = new MaterialDialog.Builder(this)
                .title("请稍后...")
                .progress(true, 100)
                .content("正在上传中...")
                .cancelable(false)
                .build();
        toolbar.setTitle(StaticVariable.getTasktypeName(task.getTasktype(), task.getTasklevel()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        verifyBoxAdapter = new VerifyBoxAdapter(this, new ArrayList<BoxBean>());
        rvVerifyBox.setAdapter(verifyBoxAdapter);
        rvVerifyBox.setLayoutManager(new LinearLayoutManager(this));
        verifyBoxAdapter.setOnItemClickListener(new VerifyBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (lock) {
                    return;
                }
                Boolean b = verifyBoxAdapter.getCheck(position);
                ImageView imageView = view.findViewById(R.id.iv_check);
                if (b) {
                    verifyBoxAdapter.setCheck(position, false);
                    imageView.setImageResource(R.drawable.ic_uncheck_black_24dp);
                    Toasty.error(VerifyBoxActivity.this, "未完成", Toast.LENGTH_SHORT, true).show();
                } else {
                    verifyBoxAdapter.setCheck(position, true);
                    imageView.setImageResource(R.drawable.ic_check_black_24dp);
                    Toasty.success(VerifyBoxActivity.this, "已完成", Toast.LENGTH_SHORT, true).show();
                }
                if (verifyBoxAdapter.isAllCheck()) {
                    btnVerifyComplete.setEnabled(true);
                } else {
                    btnVerifyComplete.setEnabled(false);
                }
                addCount();
                updateVerify();
                updateUnverified();
            }
        });
        RxView.clicks(btnVerifyStart)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(final Object o) throws Exception {
                        llPrompt.setVisibility(View.VISIBLE);
                        lock = false;
                        updateVerify();
                        updateUnverified();
                        Toasty.info(VerifyBoxActivity.this, "开始验证", 0, true).show();
                    }
                });
        RxView.clicks(btnVerifyComplete)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(final Object o) throws Exception {
                        new MaterialDialog.Builder(VerifyBoxActivity.this)
                                .title("确认提交？")
                                .negativeText("取消")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        materialDialog.show();
                                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                                        WorkerBean workerBean = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
                                        TaskExeBean taskExeBean = new TaskExeBean();
                                        taskExeBean.setTaskno(task.getTaskcode());
                                        taskExeBean.setTasktype(task.getTasktype());
                                        taskExeBean.setDeptno(config.getOrgCode());
                                        taskExeBean.setWorkno(workerBean.getWorkno() + "," + verifyWorker.getWorkno());
                                        taskExeBean.setWorkname(workerBean.getName() + "," + verifyWorker.getName());
                                        taskExeBean.setEscode1(escort1st.getEscortno());
                                        taskExeBean.setEsname1(escort1st.getName());
                                        taskExeBean.setEscode2(escort2nd.getEscortno());
                                        taskExeBean.setEsname2(escort2nd.getName());
                                        taskExeBean.setCarcode(task.getCarid());
                                        taskExeBean.setPlateno(task.getPlateno());
                                        taskExeBean.setTasktime(DateUtil.getCurDateTime24());
                                        taskExeBean.setStatus("");
                                        taskExeBean.setBoxes(verifyBoxAdapter.getBoxString());
                                        verifyBoxPresenter.upTaskExe(taskExeBean);
                                    }
                                })
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
    public void updateBoxList(List<BoxBean> boxBeanList) {
        verifyBoxAdapter.setDataList(boxBeanList);
        verifyBoxAdapter.resetCheck();
        verifyBoxAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCount() {
        int count = Integer.parseInt(tvVerifyCount.getText().toString()) + 1;
        tvVerifyCount.setText("" + count);
    }

    @Override
    public void updateVerify() {
        tvVerifyBoxCount.setText("" + verifyBoxAdapter.getCheckSize());
    }

    @Override
    public void updateUnverified() {
        tvUnverifiedBoxCount.setText("" + verifyBoxAdapter.getUncheckSize());
    }

    @Override
    public void uploadSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "上传成功", 0, true).show();
        finish();
    }

    @Override
    public void uploadFailed(String message) {
        //TODO：续传~~~~~~~~~~~~~~~~~~~~~~~~~~
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        Toasty.error(EscortApp.getInstance().getApplicationContext(), "上传失败", 0, true).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyBoxPresenter.doDestroy();
    }
}
