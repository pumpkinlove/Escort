package com.miaxis.escort.presenter;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.view.viewer.ISystemView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/18.
 */

public class SystemPresenterImpl extends BaseFragmentPresenter implements ISystemPresenter{

    private ISystemView systemView;

    public SystemPresenterImpl(LifecycleProvider<FragmentEvent> provider, ISystemView systemView) {
        super(provider);
        this.systemView = systemView;
    }

    @Override
    public void clearAll() {
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        EscortApp.getInstance().clearAndRebuildDatabase();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        systemView.clearSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        systemView.clearFailed();
                    }
                });
    }

    @Override
    public void doDestroy() {
        systemView = null;
    }
}
