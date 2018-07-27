package com.miaxis.escort.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.device.Device;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IVerifyTaskDialogPresenter;
import com.miaxis.escort.presenter.VerifyTaskDialogPresenterImpl;
import com.miaxis.escort.view.viewer.IVerifyTaskDialogView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by 一非 on 2018/4/24.
 */

public class VerifyTaskDialogFragment extends BaseDialogFragment implements IVerifyTaskDialogView, TextToSpeech.OnInitListener {

    @BindView(R.id.tv_dialog_text)
    TextView tvDialigText;
    @BindView(R.id.iv_dialog_picture1)
    ImageView ivDialogPicture1;
    @BindView(R.id.iv_dialog_picture2)
    ImageView ivDialogPicture2;
    @BindView(R.id.btn_dialog_dismiss)
    Button btnDialogDismiss;
    @BindView(R.id.btn_dialog_next)
    Button btnDialogNext;
    @BindView(R.id.tv_car_plate_no)
    TextView tvCarPlateNo;
    @BindView(R.id.iv_car_photo)
    ImageView ivCarPhoto;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;
    @BindView(R.id.ll_escorts)
    LinearLayout llEscorts;
    @BindView(R.id.ll_car)
    LinearLayout llCar;

    private int step = 1;
    private TaskBean taskBean;
    private OnVerifyTaskDialogListener listener;
    private IVerifyTaskDialogPresenter verifyTaskDialogPresenter;
    private List<EscortBean> escortBeanList;
    private List<EscortBean> mList;
    private WorkerBean workerBean;

    private WeakReference<TextToSpeech> ttsRef;

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
        escortBeanList = new ArrayList<>();
        mList = new ArrayList<>();
        verifyTaskDialogPresenter = new VerifyTaskDialogPresenterImpl(this, this);
        verifyTaskDialogPresenter.initPause();
        taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
        for (TaskEscortBean taskEscortBean : taskBean.getEscortList()) {
            taskEscortBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
//            escortBeanList.add(taskEscortBean.getEscortBean());
            mList.add(taskEscortBean.getEscortBean());
        }
        ttsRef = new WeakReference<TextToSpeech>(new TextToSpeech(this.getContext(), this));
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
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
                                        if (step == 1) {
                                            verifyTaskDialogPresenter.workerPause();
                                        } else if (step == 2) {
                                            verifyTaskDialogPresenter.escortPause();
                                        }
                                        VerifyTaskDialogFragment.this.dismiss();
                                        listener.updateStep(step);
                                        if (step > 1 && workerBean != null) {
                                            listener.updateWorker(workerBean);
                                        }
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
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.618));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshStepView();
    }

    private void refreshStepView() {
        Log.e("===", "refreshStepView");
        btnDialogNext.setEnabled(false);
        if (step == 1) {
//            llCar.setVisibility(View.VISIBLE);
//            tvWorkerName.setVisibility(View.GONE);
//            llEscorts.setVisibility(View.GONE);
//            tvDialigText.setText("验证车辆");
//            verifyTaskDialogPresenter.verifyCar(taskBean);
//            btnDialogNext.setEnabled(true);
//        } else if (step == 2) {
            llCar.setVisibility(View.GONE);
            tvWorkerName.setVisibility(View.VISIBLE);
            llEscorts.setVisibility(View.GONE);
            verifyTaskDialogPresenter.carPause();
            tvDialigText.setText("验证网点员工");
            verifyTaskDialogPresenter.verifyWorker();
        } else if (step == 2) {
            llCar.setVisibility(View.GONE);
            tvWorkerName.setVisibility(View.GONE);
            llEscorts.setVisibility(View.VISIBLE);
            tvDialigText.setText("验证押运员一");
            verifyTaskDialogPresenter.verifyEscort(mList);
//        } else if (step == 4) {
//            llCar.setVisibility(View.GONE);
//            tvWorkerName.setVisibility(View.GONE);
//            llEscorts.setVisibility(View.VISIBLE);
//            tvDialigText.setText("验证押运员二");
//            verifyTaskDialogPresenter.verifyEscort(mList);
        }else if (step == 3) {
            btnDialogNext.setEnabled(true);
            tvDialigText.setText("验证完毕");
            listener.verifySuccess(taskBean, workerBean, escortBeanList);
        } else {
            tvDialigText.setText("验证完毕");
            listener.verifySuccess(taskBean, workerBean, escortBeanList);
        }
    }

    @Override
    public void verifySuccess() {
        Toasty.success(EscortApp.getInstance().getApplicationContext(), "验证成功", 0, true).show();
        if (step == 1) {
//            playVoiceMessage("车辆验证成功");
//            if (taskBean.getCarPhoto() != null) {
//                Glide.with(VerifyTaskDialogFragment.this)
//                        .load(taskBean.getCarPhoto())
//                        .into(ivCarPhoto);
//                tvCarPlateNo.setText(taskBean.getPlateno());
//            }
//        } else if (step == 2) {
            playVoiceMessage("通过");
            tvWorkerName.setText(workerBean.getName());
            btnDialogNext.setText("下一步");
            btnDialogNext.performClick();
        } else if (step == 2) {
//            playVoiceMessage("通过");
//            btnDialogNext.performClick();
//            btnDialogNext.setText("下一步");
//        } else if (step == 4) {
            playVoiceMessage("通过");
            btnDialogNext.setText("确认人员");
//            step++;
        }
        btnDialogNext.setEnabled(true);
    }

    @Override
    public void verifyFailed(String message) {
        playVoiceMessage("失败，请取消后重试");
        Toasty.error(EscortApp.getInstance().getApplicationContext(), message, 0, true).show();
        closeDevice();
    }

    private void closeDevice() {
        byte[] message = new byte[200];
        Device.closeRfid(message);
        Device.closeFinger(message);
    }

    @Override
    public void showToasty(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.error(EscortApp.getInstance().getApplicationContext(), message, 0, true).show();
            }
        });
    }

    @Override
    public void removeVerified(EscortBean escortBean) {
        //单押运员 无车 验证时 step改为2   双押运员 有车 验证应该为3
        if (step == 2 || step == 3) {
            //ivDialogPicture1.setImageBitmap(bmp);
            Glide.with(this).load(escortBean.getPhotoUrl()).into(ivDialogPicture1);
        } else if (step == 4) {
            //ivDialogPicture2.setImageBitmap(bmp);
            Glide.with(this).load(escortBean.getPhotoUrl()).into(ivDialogPicture2);
        }

        mList.remove(escortBean);
        escortBeanList.add(escortBean);
    }

    @Override
    public void setVerifyWorker(WorkerBean worker) {
        workerBean = worker;
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsRef.get().setLanguage(Locale.CHINESE);
        }
    }

    @Override
    public void playVoiceMessage(final String message) {
        ttsRef.get().speak(message, TextToSpeech.QUEUE_FLUSH, null, "login");
    }

    @Override
    public void playVoiceMessageOnUIThread(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ttsRef.get().speak(message, TextToSpeech.QUEUE_FLUSH, null, "login");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        verifyTaskDialogPresenter.doDestroy();
        ttsRef.get().shutdown();
    }

    public interface OnVerifyTaskDialogListener {
        void updateStep(int step);

        void verifySuccess(TaskBean taskBean, WorkerBean workerBean, List<EscortBean> escortBeanList);

        void verifyFailed();

        void updateWorker(WorkerBean workerBean);
    }

}
