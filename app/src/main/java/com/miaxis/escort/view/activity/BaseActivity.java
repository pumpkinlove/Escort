package com.miaxis.escort.view.activity;

import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 一非 on 2018/4/8.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        bind = ButterKnife.bind(this);
        initData();
        initView();
    }

    protected abstract int setContentView();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
