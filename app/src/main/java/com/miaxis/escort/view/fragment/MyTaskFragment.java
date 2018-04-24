package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.miaxis.escort.R;
import com.miaxis.escort.adapter.TaskAdapter;
import com.miaxis.escort.adapter.VerifyBoxAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.presenter.IMyTaskPresenter;
import com.miaxis.escort.presenter.MyTaskPresenterImpl;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.activity.VerifyBoxActivity;
import com.miaxis.escort.view.viewer.IMyTaskView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MyTaskFragment extends BaseFragment implements IMyTaskView ,VerifyTaskDialogFragment.OnVerifyTaskDialogListener{

    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.srl_task)
    SwipeRefreshLayout srlTask;

    private TaskAdapter taskAdapter;
    private OnFragmentInteractionListener mListener;
    private IMyTaskPresenter myTaskPresenter;
    private MaterialDialog materialDialog;
    private VerifyTaskDialogFragment verifyTaskDialogFragment;

    private String selecteTaskID = "";
    private int step = 1;

    public MyTaskFragment() {
        // Required empty public constructor
    }

    public static MyTaskFragment newInstance() {
        MyTaskFragment fragment = new MyTaskFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_task;
    }

    @Override
    protected void initData() {
        myTaskPresenter = new MyTaskPresenterImpl(this, this);
    }

    @Override
    protected void initView() {
        taskAdapter = new TaskAdapter(getActivity(), new ArrayList<TaskBean>());
        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new MaterialDialog.Builder(MyTaskFragment.this.getContext())
                        .title("开始执行任务？")
                        .negativeText("取消")
                        .positiveText("确认")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                TaskBean taskBean = taskAdapter.getDataList().get(position);
                                if (!selecteTaskID.equals(taskBean.getTaskcode())) {
                                    step = 1;
                                    selecteTaskID = taskBean.getTaskcode();
                                }
                                verifyTaskDialogFragment = VerifyTaskDialogFragment.newInstance(
                                        taskBean, step, MyTaskFragment.this);
                                verifyTaskDialogFragment.show(getChildFragmentManager(), "verifyTaskDialogFragment");
                                verifyTaskDialogFragment.setCancelable(false);
                            }
                        })
                        .show();
            }
        });
        srlTask.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlTask.setRefreshing(true);
                myTaskPresenter.downTask();
            }
        });
        materialDialog = new MaterialDialog.Builder(this.getActivity())
                .title("请稍后...")
                .content("正在更新任务...")
                .progress(true, 100)
                .cancelable(false)
                .show();
        myTaskPresenter.downTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        myTaskPresenter.loadTask();
    }

    @Override
    public void setDialogMessage(String message) {
        materialDialog.setContent(message);
    }

    @Override
    public void downTaskSuccess() {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (srlTask.isRefreshing()) {
            srlTask.setRefreshing(false);
        }
        Toasty.success(this.getActivity(), "更新成功",0, true).show();
        myTaskPresenter.loadTask();
    }

    @Override
    public void downTaskFailed(String message) {
        if (materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        if (srlTask.isRefreshing()) {
            srlTask.setRefreshing(false);
        }
        Toasty.error(this.getActivity(), message,0, true).show();
    }

    @Override
    public void updateData(List<TaskBean> taskBeanList) {
        taskAdapter.setDataList(taskBeanList);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTaskPresenter.doDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onMyTaskFragmentInteraction();
        }
    }

    /**
     * 开放给UpTaskFragment的刷新任务接口
     */
    public void refreshTaskAfterUpTask() {
        materialDialog.show();
        myTaskPresenter.downTask();
    }

    @Override
    public void updateStep(int step) {
        this.step = step;
    }

    @Override
    public void verifySuccess(TaskBean taskBean) {
        verifyTaskDialogFragment.dismiss();
        selecteTaskID = "";
        step = 1;
        Intent intent = new Intent(MyTaskFragment.this.getActivity(), VerifyBoxActivity.class);
        intent.putExtra("task", taskBean);
        //TODO：默认指纹验证通过，默认押运员，默认操作员
        List<EscortBean> escortBeanList = EscortApp.getInstance().getDaoSession().getEscortBeanDao().loadAll();
        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
        intent.putExtra("escort1st", escortBeanList.get(0));
        intent.putExtra("escort2nd", escortBeanList.get(1));
        intent.putExtra("verifyWorker", workerBeanList.get(1));
        startActivity(intent);
    }

    @Override
    public void verifyFailed() {
        verifyTaskDialogFragment.dismiss();
        selecteTaskID = "";
        step = 1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        myTaskPresenter.doDestroy();
    }

    public interface OnFragmentInteractionListener {
        void onMyTaskFragmentInteraction();
    }
}
