package com.miaxis.escort.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.IMyTaskModel;
import com.miaxis.escort.model.MyTaskModelImpl;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskEscortBean;
import com.miaxis.escort.model.retrofit.BoxNet;
import com.miaxis.escort.model.retrofit.EscortNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IMyTaskView;
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
 * Created by 一非 on 2018/4/17.
 */

public class MyTaskPresenterImpl extends BaseFragmentPresenter implements IMyTaskPresenter{

    private IMyTaskView myTaskView;
    private IMyTaskModel myTaskModel;

    public MyTaskPresenterImpl(LifecycleProvider<FragmentEvent> provider, IMyTaskView myTaskView) {
        super(provider);
        this.myTaskView = myTaskView;
        myTaskModel = new MyTaskModelImpl(this);
    }

    @Override
    public void loadTask() {
        Observable.create(new ObservableOnSubscribe<List<TaskBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TaskBean>> e) throws Exception {
                e.onNext(myTaskModel.loadTask());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<TaskBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskBean>>() {
                    @Override
                    public void accept(List<TaskBean> taskBeans) throws Exception {
                        if (myTaskView != null) {
                            myTaskView.updateData(taskBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void downTask() {
        final Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        Observable.just(config)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Config, Observable<ResponseEntity<TaskBean>>>() {
                    @Override
                    public Observable<ResponseEntity<TaskBean>> apply(Config config) throws Exception {
                        TaskNet taskNet = retrofit.create(TaskNet.class);
                        return  taskNet.downloadTask(config.getOrgCode(), DateUtil.getToday());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<TaskBean>>() {
                    @Override
                    public void accept(ResponseEntity<TaskBean> taskBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(taskBeanResponseEntity.getCode())) {
                            List<TaskBean> taskBeans =taskBeanResponseEntity.getListData();
                            myTaskModel.saveTask(taskBeans);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<TaskBean>>() {
                    @Override
                    public void accept(ResponseEntity<TaskBean> taskBeanResponseEntity) throws Exception {
                        if (myTaskView != null) {
                            if (StaticVariable.SUCCESS.equals(taskBeanResponseEntity.getCode())) {
                                myTaskView.setDialogMessage("下载任务成功，正在下载任务箱包信息...");
                            } else {
                                myTaskView.setDialogMessage(taskBeanResponseEntity.getMessage());
                            }
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<TaskBean>, Observable<ResponseEntity<BoxBean>>>() {
                    @Override
                    public Observable<ResponseEntity<BoxBean>> apply(ResponseEntity<TaskBean> taskBeanResponseEntity) throws Exception {
                        BoxNet boxNet = retrofit.create(BoxNet.class);
                        return boxNet.downloadBox(config.getOrgCode(), new Gson().toJson(myTaskModel.getBoxOpdateByTaskDate(DateUtil.getToday())));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(boxBeanResponseEntity.getCode())) {
                            myTaskModel.saveBox(boxBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (myTaskView != null) {
                            if (StaticVariable.SUCCESS.equals(boxBeanResponseEntity.getCode())) {
                                myTaskView.setDialogMessage("下载任务箱包完成，正在下载押运员信息...");
                            } else {
                                myTaskView.setDialogMessage(boxBeanResponseEntity.getMessage());
                            }
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<BoxBean>, Observable<ResponseEntity<EscortBean>>>() {
                    @Override
                    public Observable<ResponseEntity<EscortBean>> apply(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        EscortNet escortNet = retrofit.create(EscortNet.class);
                        return escortNet.downloadEscort(new Gson().toJson(myTaskModel.getEscortOpdate(DateUtil.getToday())));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<EscortBean>>() {
                    @Override
                    public void accept(ResponseEntity<EscortBean> escortBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(escortBeanResponseEntity.getCode())) {
                            myTaskModel.saveEscort(escortBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<EscortBean>>() {
                    @Override
                    public void accept(ResponseEntity<EscortBean> escortBeanResponseEntity) throws Exception {
                        if (myTaskView != null) {
                            if (StaticVariable.SUCCESS.equals(escortBeanResponseEntity.getCode())) {
                                myTaskView.setDialogMessage("下载押运员信息成功，正在更新箱包信息...");
                            } else {
                                myTaskView.setDialogMessage(escortBeanResponseEntity.getMessage());
                            }
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<EscortBean>, ObservableSource<ResponseEntity<BoxBean>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<BoxBean>> apply(ResponseEntity<EscortBean> escortBeanResponseEntity) throws Exception {
                        BoxNet boxNet = retrofit.create(BoxNet.class);
                        return boxNet.downloadBox(config.getOrgCode(), new Gson().toJson(myTaskModel.getBoxOpdate()));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(boxBeanResponseEntity.getCode())) {
                            myTaskModel.saveBox(boxBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (myTaskView != null) {
                            if (StaticVariable.SUCCESS.equals(boxBeanResponseEntity.getCode())) {
                                myTaskView.setDialogMessage("更新箱包成功，正在初始化...");
                            } else {
                                myTaskView.setDialogMessage(boxBeanResponseEntity.getMessage());
                            }
                        }
                    }
                })
                .subscribe(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> taskBeanResponseEntity) throws Exception {
                        if (myTaskView != null) {
                            myTaskView.downTaskSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (myTaskView != null) {
                            myTaskView.downTaskFailed();
                        }
                    }
                });
    }

    @Override
    public void doDestroy() {
        myTaskView = null;
        myTaskModel = null;
    }
}
