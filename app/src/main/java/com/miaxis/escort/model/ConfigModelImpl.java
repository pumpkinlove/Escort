package com.miaxis.escort.model;

import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.presenter.IConfigPresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/8.
 */

public class ConfigModelImpl implements IConfigModel{

    private IConfigPresenter configPresenter;

    public ConfigModelImpl(IConfigPresenter configPresenter) {
        this.configPresenter = configPresenter;
    }

    @Override
    public void saveConfig(Config config) {
        //TODO: 一场惊心动魄的数据交换之后，下载了操作员信息，下载了银行信息，下载了箱包信息并保存到数据库
        if (true) {
            Observable.just(config)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Config>() {
                        @Override
                        public void accept(Config config) throws Exception {
                            EscortApp.getInstance().getDaoSession().getConfigDao().deleteAll();
                            EscortApp.getInstance().getDaoSession().getConfigDao().insert(config);
                        }
                    });
            configPresenter.configSaveSuccess();
        } else {
            configPresenter.configSaveFailed();
        }
    }

    @Override
    public void loadConfig() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                Config config = EscortApp.getInstance().getDaoSession().getConfigDao().load(1L);
                e.onNext(config);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                               @Override
                               public void accept(Config config) throws Exception {
                                   configPresenter.fetchConfig(config);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
    }
}
