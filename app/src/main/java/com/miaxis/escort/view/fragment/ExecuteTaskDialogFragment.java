package com.miaxis.escort.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.device.Device;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.event.ResultEvent;
import com.miaxis.escort.model.WorkerBeanModel;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.ExecuteTaskDialogPresenterImpl;
import com.miaxis.escort.presenter.IExecuteTaskDialogPresenter;
import com.miaxis.escort.presenter.IVerifyTaskDialogPresenter;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.activity.VerifyBoxActivity;
import com.miaxis.escort.view.viewer.IExecuteDialogView;
import com.miaxis.escort.view.viewer.IVerifyTaskDialogView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;

/**
 * Created by tang.yf on 2018/7/27.
 */

public class ExecuteTaskDialogFragment extends BaseDialogFragment implements IExecuteDialogView, TextToSpeech.OnInitListener{

    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.iv_dialog_picture)
    ImageView ivDialogPicture;
    @BindView(R.id.btn_dialog_cancel)
    Button btnDialogCancel;
    @BindView(R.id.btn_dialog_next)
    Button btnDialogNext;

    private TaskBean taskBean;
    private IExecuteTaskDialogPresenter presenter;
    private Step step;
    private WeakReference<TextToSpeech> ttsRef;

    //TODO: test
    private int verifyWorkerNum = 1;
    private int verifyEscortNum = 2;

    public static enum Step {
        worker, workerExe, escort, escortExe, over
    }

    public static ExecuteTaskDialogFragment newInstance(TaskBean taskBean) {
        ExecuteTaskDialogFragment executeTaskDialogFragment = new ExecuteTaskDialogFragment();
        executeTaskDialogFragment.setTaskBean(taskBean);
        return executeTaskDialogFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_execute_task_dialog;
    }

    @Override
    protected void initData() {
        step = Step.worker;
        EventBus.getDefault().register(this);
        ttsRef = new WeakReference<TextToSpeech>(new TextToSpeech(this.getContext(), this));
        presenter = new ExecuteTaskDialogPresenterImpl(this, this);
        List<EscortBean> escortBeanList = new ArrayList<>();
        taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
        for (TaskEscortBean taskEscortBean : taskBean.getEscortList()) {
            taskEscortBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
            escortBeanList.add(taskEscortBean.getEscortBean());
        }
        List<WorkerBean> workerBeanList = WorkerBeanModel.loadWorkerBeanList();
        WorkerBean workerBean = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
        Iterator iterator = workerBeanList.iterator();
        while (iterator.hasNext()) {
            WorkerBean worker = (WorkerBean) iterator.next();
            if (worker.getWorkno().equals(workerBean.getWorkno())) {
                iterator.remove();
                break;
            }
        }
        presenter.init(verifyWorkerNum, workerBeanList, verifyEscortNum, escortBeanList);
        if (workerBeanList.size() < verifyWorkerNum - 1 || escortBeanList.size() < verifyEscortNum) {
            Toasty.error(this.getContext(), "任务数据出错，请刷新任务或切换模式", Toast.LENGTH_SHORT, true).show();
            dismiss();
        }
        tvDialogTitle.setText("网点员工交接");
        presenter.startVerify();
    }

    @Override
    protected void initView() {
        RxView.clicks(btnDialogCancel)
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dismiss();
                        presenter.closeThread();
                        Device.closeFinger(new byte[200]);
                    }
                });
        RxView.clicks(btnDialogNext)
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        btnDialogNext.setEnabled(false);
                        if (step == Step.workerExe) {
                            step = Step.escort;
                            tvDialogTitle.setText("押运员交接");
                            presenter.startVerifyEscort();
                        } else if (step == Step.escortExe) {
                            step = Step.over;
                            tvDialogTitle.setText("验证完成");
                            ArrayList<WorkerBean> workerBeanArrayList = presenter.getWorkerCache();
                            ArrayList<EscortBean> escortBeanArrayList = presenter.getEscortCache();
                            if (workerBeanArrayList.size() != verifyWorkerNum || escortBeanArrayList.size() != verifyEscortNum) {
                                Toasty.error(ExecuteTaskDialogFragment.this.getContext(),
                                        "验证的员工和押运员数量不匹配", Toast.LENGTH_SHORT, true).show();
                            } else {
                                startActivity(VerifyBoxActivity.newInstance(ExecuteTaskDialogFragment.this.getContext(),
                                        taskBean, workerBeanArrayList, escortBeanArrayList));
                            }
                            dismiss();
                            Log.e("asd", "验证完成");
                        }
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

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultEvent(ResultEvent e) {
        switch (e.getResult()) {
            case ResultEvent.VERIFY_WORKER_SUCCESS:
                String workerMessage = "网点员工" + e.getWorkerBean().getName() +"比对成功，剩余" + (verifyWorkerNum - e.getTime()) + "人需要比对。";
                Toasty.success(this.getContext(), workerMessage, Toast.LENGTH_SHORT, true).show();
                playVoiceMessage("网点员工验证成功");
                if (verifyWorkerNum == e.getTime()) {
                    step = Step.workerExe;
                    btnDialogNext.setEnabled(true);
                }
                break;
            case ResultEvent.VERIFY_ESCORT_SUCCESS:
                String escortMessage = "押运员" + e.getEscortBean().getName() +"比对成功，剩余" + (verifyEscortNum - e.getTime()) + "人需要比对。";
                Toasty.success(this.getContext(), escortMessage, Toast.LENGTH_SHORT, true).show();
                Glide.with(this).load(e.getEscortBean().getPhotoUrl()).into(ivDialogPicture);
                playVoiceMessage("押运员验证成功");
                if (verifyEscortNum == e.getTime()) {
                    step = Step.escortExe;
                    btnDialogNext.setEnabled(true);
                    presenter.closeThread();
                    Device.closeFinger(new byte[200]);
                }
                break;
            case ResultEvent.VERIFY_FAILED:
                playVoiceMessage("验证失败");
                Toasty.error(this.getContext(), "未找到对应指纹", Toast.LENGTH_SHORT, true).show();
                break;
            case ResultEvent.COMPLETE:
                break;
            default:
                break;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsRef.get().setLanguage(Locale.CHINESE);
        }
    }

    private void playVoiceMessage(final String message) {
        ttsRef.get().speak(message, TextToSpeech.QUEUE_FLUSH, null, "login");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        ttsRef.get().shutdown();
    }

}
