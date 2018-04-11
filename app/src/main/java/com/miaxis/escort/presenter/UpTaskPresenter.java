package com.miaxis.escort.presenter;

import com.miaxis.escort.view.viewer.IUpTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by 一非 on 2018/4/11.
 */

public class UpTaskPresenter extends BaseFragmentPresenter implements IUpTaskPresenter{

    private IUpTaskView upTaskView;

    public UpTaskPresenter(LifecycleProvider<FragmentEvent> provider, IUpTaskView upTaskView) {
        super(provider);
        this.upTaskView = upTaskView;
    }

    @Override
    public void doDestroy() {
        upTaskView = null;
    }
}
