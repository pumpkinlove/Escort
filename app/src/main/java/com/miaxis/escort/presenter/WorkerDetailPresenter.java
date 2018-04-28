package com.miaxis.escort.presenter;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.WorkerDetailModel;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.WorkerNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IWorkerDetailView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 一非 on 2018/4/19.
 */

public class WorkerDetailPresenter extends BaseActivityPresenter implements IWorkerDetailPresenter{

    private IWorkerDetailView workerDetailView;
    private WorkerDetailModel workerDetailModel;

    public WorkerDetailPresenter(LifecycleProvider<ActivityEvent> provider, IWorkerDetailView workerDetailView) {
        super(provider);
        this.workerDetailView = workerDetailView;
        workerDetailModel = new WorkerDetailModel();
    }

    @Override
    public void addWorker(WorkerBean workerBean) {
        Observable.just(workerBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<WorkerBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<WorkerBean, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(WorkerBean workerBean) throws Exception {
                        workerDetailModel.saveLocal(workerBean);
                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        WorkerNet workerNet = retrofit.create(WorkerNet.class);
                        return workerNet.addWorker(new Gson().toJson(workerBean));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (workerDetailView != null) {
                            if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                                workerDetailView.addWorkerSuccess();
                            } else {
                                workerDetailView.addWorkerFailed(responseEntity.getMessage());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        workerDetailView.addWorkerFailed(throwable.getMessage());
                    }
                });
    }

    @Override
    public void deleteWorker(final String workercode) {
        Observable.just(workercode)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<String>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(String s) throws Exception {
                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        WorkerNet workerNet = retrofit.create(WorkerNet.class);
                        return workerNet.delWorker(s);
                    }
                })
                .doOnNext(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                            workerDetailModel.deleteLocal(workercode);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (workerDetailView != null) {
                            if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                                workerDetailView.delWorkerSuccess();
                            } else {
                                workerDetailView.delWorkerFailed(responseEntity.getMessage());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (workerDetailView != null) {
                            workerDetailView.delWorkerFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public boolean isSelf() {
        return workerDetailModel.isExist((WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER));
    }

    @Override
    public boolean isDuplicate(String workno) {
        return workerDetailModel.isDuplicate(workno);
    }

    @Override
    public void doDestroy() {
        workerDetailView = null;
    }
}
