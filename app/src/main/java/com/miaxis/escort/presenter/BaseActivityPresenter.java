package com.miaxis.escort.presenter;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by 一非 on 2018/4/9.
 */

public class BaseActivityPresenter {
    private LifecycleProvider<ActivityEvent> provider;

    public BaseActivityPresenter(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }

    public LifecycleProvider<ActivityEvent> getProvider() {
        return provider;
    }
}
