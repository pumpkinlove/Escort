package com.miaxis.escort.presenter;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.retrofit.AppNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ISystemView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                        if (systemView != null) {
                            systemView.clearSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (systemView != null) {
                            systemView.clearFailed();
                        }
                    }
                });
    }

    @Override
    public void updateApp() {
        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        AppNet appNet = retrofit.create(AppNet.class);
        Observable<ResponseEntity<String>> observableVersion = appNet.getAppVersion();
        Observable<ResponseEntity<String>> observableLength = appNet.getAppLength();
        Observable<ResponseEntity<String>> observableTime = appNet.getTime();
        Observable.zip(observableVersion, observableLength, observableTime, new Function3<ResponseEntity<String>, ResponseEntity<String>, ResponseEntity<String>, String>() {
            @Override
            public String apply(ResponseEntity<String> stringResponseEntity, ResponseEntity<String> stringResponseEntity2, ResponseEntity<String> stringResponseEntity3) throws Exception {
                StringBuilder message = new StringBuilder();
                if (StaticVariable.SUCCESS.equals(stringResponseEntity.getCode()) &&
                        StaticVariable.SUCCESS.equals(stringResponseEntity2.getCode()) &&
                        StaticVariable.SUCCESS.equals(stringResponseEntity3.getCode())) {
                    message.append(stringResponseEntity.getData() + "&");
                    message.append(stringResponseEntity2.getData() + "&");
                    message.append(stringResponseEntity3.getData());
                }
                return message.toString();
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<String>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (systemView != null) {
                            systemView.downAppMessageSuccess(s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (systemView != null) {
                            systemView.downAppMessageFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void doDestroy() {
        systemView = null;
    }
}
