package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MyTaskFragment extends BaseFragment {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.srl_task)
    SwipeRefreshLayout srlTask;

    private TaskAdapter taskAdapter;
    private OnFragmentInteractionListener mListener;

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

    }

    @Override
    protected void initView() {
        List<String> dataList = new ArrayList<>();
        dataList.add("任务1");
        dataList.add("任务2");
        dataList.add("任务3");
        dataList.add("任务4");
        dataList.add("任务5");
        dataList.add("任务6");
        taskAdapter = new TaskAdapter(getActivity(), dataList);
        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        srlTask.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.just(0)
                        .delay(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                srlTask.setRefreshing(false);
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onMyTaskFragmentInteraction();
        }
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
    }

    public interface OnFragmentInteractionListener {
        void onMyTaskFragmentInteraction();
    }
}
