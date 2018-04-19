package com.miaxis.escort.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.IUpTaskModel;
import com.miaxis.escort.model.UpTaskModelImpl;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskUpBean;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IUpTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 一非 on 2018/4/11.
 */

public class UpTaskPresenterImpl extends BaseFragmentPresenter implements IUpTaskPresenter{

    private IUpTaskView upTaskView;
    private IUpTaskModel upTaskModel;

    public UpTaskPresenterImpl(LifecycleProvider<FragmentEvent> provider, IUpTaskView upTaskView) {
        super(provider);
        this.upTaskView = upTaskView;
        upTaskModel = new UpTaskModelImpl(this);
    }

    @Override
    public void loadBox() {
        Observable.create(new ObservableOnSubscribe<List<BoxBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BoxBean>> e) throws Exception {
                e.onNext(upTaskModel.loadBox());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<BoxBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeans) throws Exception {
                        if (upTaskView != null) {
                            upTaskView.updateBox(boxBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void upTask(final TaskUpBean taskUpBean, final List<TaskBoxBean> boxBeanList) {
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Integer i) throws Exception {
                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        Log.e("asd", retrofit.baseUrl().toString());
                        TaskNet taskNet = retrofit.create(TaskNet.class);
                        return taskNet.uploadTask(new Gson().toJson(taskUpBean), new Gson().toJson(boxBeanList));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (upTaskView != null) {
                            if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                                upTaskView.upTaskSuccess();
                            } else {
                                upTaskView.upTaskFailed(responseEntity.getMessage());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (upTaskView != null) {
                            upTaskView.upTaskFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void doDestroy() {
        upTaskView = null;
        upTaskModel = null;
    }
}
