package com.miaxis.escort.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskEscortBean;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by 一非 on 2018/4/24.
 */

public class VerifyTaskDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_dialog_text)
    TextView tvDialigText;
    @BindView(R.id.iv_dialog_picture)
    ImageView ivDialogPicture;
    @BindView(R.id.btn_dialog_dismiss)
    Button btnDialogDismiss;
    @BindView(R.id.btn_dialog_next)
    Button btnDialogNext;

    private int step;
    private TaskBean taskBean;
    private OnVerifyTaskDialogListener listener;

    public VerifyTaskDialogFragment() {

    }

    public static VerifyTaskDialogFragment newInstance(TaskBean taskBean, int step, OnVerifyTaskDialogListener listener) {
        VerifyTaskDialogFragment verifyTaskDialogFragment = new VerifyTaskDialogFragment();
        verifyTaskDialogFragment.setTaskBean(taskBean);
        verifyTaskDialogFragment.setStep(step);
        verifyTaskDialogFragment.setListener(listener);
        return verifyTaskDialogFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_verify_task_dialog;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        refreshStepView();
        /**Test*/
        RxView.clicks(ivDialogPicture)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        btnDialogNext.setEnabled(true);
                        if (step == 1) {
                            if (taskBean.getCarPhoto() != null) {
                                ivDialogPicture.setImageBitmap(BitmapFactory.decodeByteArray(taskBean.getCarPhoto(), 0, taskBean.getCarPhoto().length));
                            }
                        } else if (step == 2) {
                            ivDialogPicture.setImageResource(R.mipmap.ic_launcher);
                        } else if (step ==3) {
                            taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
                            TaskEscortBean taskEscortBean = taskBean.getEscortList().get(0);
                            taskEscortBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
                            EscortBean escortBean = taskEscortBean.getEscortBean();
                            ivDialogPicture.setImageBitmap(BitmapFactory.decodeByteArray(escortBean.getPhoto(), 0 ,escortBean.getPhoto().length));
                        }else if (step == 4) {
                            taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
                            TaskEscortBean taskEscortBean = taskBean.getEscortList().get(1);
                            taskEscortBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
                            EscortBean escortBean = taskEscortBean.getEscortBean();
                            ivDialogPicture.setImageBitmap(BitmapFactory.decodeByteArray(escortBean.getPhoto(), 0 ,escortBean.getPhoto().length));
                        } else {
                        }
                    }
                });
        RxView.clicks(btnDialogDismiss)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        new MaterialDialog.Builder(VerifyTaskDialogFragment.this.getContext())
                                .title("确认退出？")
                                .negativeText("取消")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        VerifyTaskDialogFragment.this.dismiss();
                                        listener.updateStep(step);
                                    }
                                })
                                .show();
                    }
                });
        RxView.clicks(btnDialogNext)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        step++;
                        refreshStepView();
                        btnDialogNext.setEnabled(false);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), (int) (dm.heightPixels * 0.75));
        }
    }

    private void refreshStepView() {
        if (step == 1) {
            tvDialigText.setText("验证车辆");
        } else if (step == 2) {
            tvDialigText.setText("验证操作员");
        } else if (step ==3) {
            tvDialigText.setText("验证押运员1");
        }else if (step == 4) {
            tvDialigText.setText("验证押运员2");
        } else {
            tvDialigText.setText("验证完毕");
            listener.verifySuccess(taskBean);
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public TaskBean getTaskBean() {
        return taskBean;
    }

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    public OnVerifyTaskDialogListener getListener() {
        return listener;
    }

    public void setListener(OnVerifyTaskDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnVerifyTaskDialogListener {
        void updateStep(int step);
        void verifySuccess(TaskBean taskBean);
        void verifyFailed();
    }

}
