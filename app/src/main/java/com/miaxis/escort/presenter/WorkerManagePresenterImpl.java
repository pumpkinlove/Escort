package com.miaxis.escort.presenter;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.IWorkerManageModel;
import com.miaxis.escort.model.WorkerManageModelImpl;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.WorkerNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IWorkerManageView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 一非 on 2018/4/17.
 */

public class WorkerManagePresenterImpl extends BaseActivityPresenter implements IWorkerManagePresenter{

    private IWorkerManageView workerManageView;
    private IWorkerManageModel workerManageModel;

    public WorkerManagePresenterImpl(LifecycleProvider<ActivityEvent> provider, IWorkerManageView workerManageView) {
        super(provider);
        this.workerManageView = workerManageView;
        workerManageModel = new WorkerManageModelImpl(this);
    }

    @Override
    public void downWorkerList() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                e.onNext((Config) EscortApp.getInstance().get(StaticVariable.CONFIG));
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Config, Observable<ResponseEntity<WorkerBean>>>() {
                    @Override
                    public Observable<ResponseEntity<WorkerBean>> apply(Config config) throws Exception {
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        WorkerNet workerNet = retrofit.create(WorkerNet.class);
                        return workerNet.downloadWorker(config.getOrgCode(), new Gson().toJson(workerManageModel.getWorkerOpdate()));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<WorkerBean>>() {
                    @Override
                    public void accept(ResponseEntity<WorkerBean> workerBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(workerBeanResponseEntity.getCode())) {
                            workerManageModel.saveWorkerList(workerBeanResponseEntity.getListData());
                        }
                    }
                })
                .map(new Function<ResponseEntity<WorkerBean>, List<WorkerBean>>() {
                    @Override
                    public List<WorkerBean> apply(ResponseEntity<WorkerBean> workerBeanResponseEntity) throws Exception {
                        return workerManageModel.loadWorkerList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WorkerBean>>() {
                    @Override
                    public void accept(List<WorkerBean> workerBeans) throws Exception {
                        if (workerManageView != null) {
                            workerManageView.updateDataList(workerBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toasty.error(EscortApp.getInstance().getApplicationContext(), "刷新失败",0, true).show();
                    }
                });
    }

    @Override
    public void loadWorkerList() {
        Observable.create(new ObservableOnSubscribe<List<WorkerBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WorkerBean>> e) throws Exception {
                e.onNext(workerManageModel.loadWorkerList());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<WorkerBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WorkerBean>>() {
                    @Override
                    public void accept(List<WorkerBean> workerBeanList) throws Exception {
                        if (workerManageView != null) {
                            workerManageView.updateDataList(workerBeanList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void doDestroy() {
        workerManageView = null;
        workerManageModel = null;
    }

}
