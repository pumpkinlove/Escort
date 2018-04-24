package com.miaxis.escort.presenter;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.ITaskDetailModel;
import com.miaxis.escort.model.TaskDetailModelImpl;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ITaskDetailView;
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
 * Created by 一非 on 2018/4/24.
 */

public class TaskDetailPresenterImpl extends BaseActivityPresenter implements ITaskDetailPresenter{

    private ITaskDetailView taskDetailView;
    private ITaskDetailModel taskDetailModel;

    public TaskDetailPresenterImpl(LifecycleProvider<ActivityEvent> provider, ITaskDetailView taskDetailView) {
        super(provider);
        this.taskDetailView = taskDetailView;
        taskDetailModel = new TaskDetailModelImpl();
    }

    @Override
    public void deleteTask(final TaskBean taskBean) {
        Observable.just(taskBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<TaskBean, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(TaskBean taskBean) throws Exception {
                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        TaskNet taskNet = retrofit.create(TaskNet.class);
                        return taskNet.deleteTask(taskBean.getTaskcode());
                    }
                })
                .doOnNext(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                            taskDetailModel.deleteTaskLocal(taskBean);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (taskDetailView != null) {
                            if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                                taskDetailView.deleteSuccess();
                            } else {
                                taskDetailView.deleteFailed(responseEntity.getMessage());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        taskDetailView.deleteFailed(throwable.getMessage());
                    }
                });
    }

    @Override
    public void doDestroy() {
        taskDetailView = null;
    }
}
