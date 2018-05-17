package com.miaxis.escort.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private VerifyBoxAdapter verifyBoxAdapter;
    private IVerifyBoxPresenter verifyBoxPresenter;
    private MaterialDialog materialDialog;
    private SoundPool spBeep;
    private Map<Integer, Integer> soundMap;

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
        spBeep = new SoundPool(21, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<>();
        soundMap.put(1, spBeep.load(this, R.raw.my_beep, 1));
    }

    @SuppressLint("CheckResult")
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
            }
        });
        RxView.clicks(btnVerifyStart)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(final Object o) throws Exception {
                        if (btnVerifyStart.getText().toString().equals("开始验证")) {
                            startVerify();
                        } else if (btnVerifyStart.getText().toString().equals("结束验证")) {
                            new MaterialDialog.Builder(VerifyBoxActivity.this)
                                    .title("确认结束验证？")
                                    .negativeText("取消")
                                    .positiveText("确认")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            verifyBoxPresenter.pause();
                                            btnVerifyComplete.setEnabled(true);
                                            btnVerifyStart.setText("开始验证");
                                        }
                                    })
                                    .show();
                        }
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
    protected void onResume() {
        super.onResume();
        startVerify();
    }

    private void startVerify() {
        llPrompt.setVisibility(View.VISIBLE);
        tvVerifyBoxCount.setText("" + verifyBoxAdapter.getCheckSize());
        updateUnverified();
        Toasty.info(VerifyBoxActivity.this, "开始验证", 0, true).show();
        verifyBoxPresenter.verifyBox(verifyBoxAdapter.getDataListCopy());
        btnVerifyStart.setText("结束验证");
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
        new MaterialDialog.Builder(this)
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
    }

    @Override
    public void updateBoxList(List<BoxBean> boxBeanList) {
        verifyBoxAdapter.setDataList(boxBeanList);
        verifyBoxAdapter.resetCheck();
        verifyBoxAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int count = Integer.parseInt(tvVerifyCount.getText().toString()) + 1;
                tvVerifyCount.setText("" + count);
            }
        });
    }

    @Override
    public void updateVerify(final BoxBean boxBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                verifyBoxAdapter.setBoxCheck(boxBean);
                verifyBoxAdapter.notifyDataSetChanged();
                if (verifyBoxAdapter.isAllCheck()) {
                    Toasty.success(VerifyBoxActivity.this, "物品验证完成", Toast.LENGTH_LONG, true).show();
                    btnVerifyComplete.setEnabled(true);
                } else {
                    btnVerifyComplete.setEnabled(false);
                }
                tvVerifyBoxCount.setText("" + verifyBoxAdapter.getCheckSize());
                spBeep.play(soundMap.get(1), 1.0f, 1.0f, 1, 0, 1.0f);
                updateUnverified();
            }
        });
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
