package com.miaxis.escort.presenter;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.IMyTaskModel;
import com.miaxis.escort.model.MyTaskModelImpl;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.TaskBean;
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
                        myTaskView.updateData(taskBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void downTask() {
        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
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
                            myTaskModel.saveTask(taskBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<TaskBean>>() {
                    @Override
                    public void accept(ResponseEntity<TaskBean> taskBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(taskBeanResponseEntity.getCode())) {
                            myTaskView.setDialogMessage("下载任务成功，正在下载押运员信息...");
                        } else {
                            myTaskView.setDialogMessage(taskBeanResponseEntity.getMessage());
                        }
                    }
                })
                .subscribe(new Consumer<ResponseEntity<TaskBean>>() {
                    @Override
                    public void accept(ResponseEntity<TaskBean> taskBeanResponseEntity) throws Exception {
                        myTaskView.downTaskSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myTaskView.downTaskFailed();
                    }
                });
    }

    @Override
    public void doDestroy() {
        myTaskView = null;
        myTaskModel = null;
    }
}
