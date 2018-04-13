package com.miaxis.escort.presenter;

import com.miaxis.escort.model.ConfigModelImpl;
import com.miaxis.escort.model.IConfigModel;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.view.viewer.IConfigView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/8.
 */

public class ConfigPresenterImpl extends BaseFragmentPresenter implements IConfigPresenter{

    private IConfigView configView;
    private IConfigModel configModel;

    public ConfigPresenterImpl(LifecycleProvider<FragmentEvent> provider, IConfigView configView) {
        super(provider);
        this.configView = configView;
        this.configModel = new ConfigModelImpl(this);
    }

    @Override
    public void configSave(String ip, String port, String orgCode) {
        configModel.saveConfig(new Config(1L, ip, port, orgCode));
    }

    @Override
    public void configSaveSuccess() {
        configView.configSaveSuccess();
    }

    @Override
    public void configSaveFailed() {
        configView.configSaveFailed();
    }

    @Override
    public void loadConfig() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                Config config = configModel.loadConfig();
                e.onNext(config);
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configView.fetchConfig(config);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void fetchConfig(Config config) {
        configView.fetchConfig(config);
    }

    @Override
    public void doDestroy() {
        configView = null;
    }
}
