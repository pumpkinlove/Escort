package com.miaxis.escort.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;
import com.miaxis.escort.view.activity.ConfigActivity;
import com.miaxis.escort.view.activity.WorkerManageActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SystemFragment extends BaseFragment {

    @BindView(R.id.ll_query)
    LinearLayout llQuery;
    @BindView(R.id.ll_worker_manage)
    LinearLayout llWorkerManage;
    @BindView(R.id.ll_config)
    LinearLayout llConfig;
    @BindView(R.id.ll_clear_all)
    LinearLayout llClearAll;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    private OnFragmentInteractionListener mListener;

    public SystemFragment() {
        // Required empty public constructor
    }

    public static SystemFragment newInstance() {
        SystemFragment fragment = new SystemFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        RxView.clicks(llConfig)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(SystemFragment.this.getActivity(), ConfigActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(llWorkerManage)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Intent intent = new Intent(SystemFragment.this.getActivity(), WorkerManageActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onSystemFragmentInteraction();
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
        void onSystemFragmentInteraction();
    }
}
