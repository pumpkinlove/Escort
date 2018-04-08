package com.miaxis.escort.presenter;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by 一非 on 2018/4/8.
 */

public class BasePresenter {

    protected Context mContext;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    protected LifecycleProvider getActivityLifecycleProvider() {
        LifecycleProvider provider = null;
        if (null != mContext && mContext instanceof LifecycleProvider) {
            provider =  (LifecycleProvider)mContext;
        }
        return provider;
    }

    public void doDestroy(){
        this.mContext = null;
    }

}
