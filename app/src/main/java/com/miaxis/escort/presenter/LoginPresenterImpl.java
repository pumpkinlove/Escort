package com.miaxis.escort.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Base64;

import com.device.Device;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.ILoginModel;
import com.miaxis.escort.model.LoginModelImpl;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.retrofit.EscortNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.DateUtil;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ILoginView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import es.dmoral.toasty.Toasty;
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
 * Created by 一非 on 2018/4/8.
 */
@SuppressLint("CheckResult")
public class LoginPresenterImpl extends BaseActivityPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private ILoginModel loginModel;

    public LoginPresenterImpl(LifecycleProvider<ActivityEvent> provider, ILoginView loginView) {
        super(provider);
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void getPermissions(Activity activity) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().<Boolean>bindToLifecycle())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (loginView != null) {
                            if (aBoolean) {
                                loginView.getPermissionsSuccess();
                            } else {
                                loginView.getPermissionsFailed();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (loginView != null) {
                            loginView.getPermissionsFailed();
                        }
                    }
                });
    }

    @Override
    public void initAppData() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                e.onNext(loginModel.loadConfig());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
                        //默认登陆员工
                        EscortApp.getInstance().put(StaticVariable.WORKER, workerBeanList.get(0));
                        EscortApp.getInstance().put(StaticVariable.CONFIG, config);
                        loginView.loginSuccess(workerBeanList.get(0));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.loginFailed(throwable.getMessage());
                    }
                });
    }

    @Override
    public void resumeTaskExe() {
        Observable.create(new ObservableOnSubscribe<TaskExeBean>() {
            @Override
            public void subscribe(ObservableEmitter<TaskExeBean> e) throws Exception {
                List<TaskExeBean> taskExeBeanList = EscortApp.getInstance().getDaoSession().getTaskExeBeanDao().loadAll();
                for (TaskExeBean taskExeBean : taskExeBeanList) {
                    e.onNext(taskExeBean);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskExeBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<TaskExeBean>() {
                    @Override
                    public void accept(TaskExeBean taskExeBean) throws Exception {
                        resumeTaskExeBean(taskExeBean);
                    }
                })
                .subscribe(new Consumer<TaskExeBean>() {
                    @Override
                    public void accept(TaskExeBean taskExeBean) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    private void resumeTaskExeBean(final TaskExeBean taskExeBean) {
        Observable.just(taskExeBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskExeBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<TaskExeBean, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(TaskExeBean taskExeBean) throws Exception {
                        Config config = loginModel.loadConfig();
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        TaskNet taskNet = retrofit.create(TaskNet.class);
                        return taskNet.uploadTaskExec(new Gson().toJson(taskExeBean));
                    }
                })
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                            loginModel.deleteTaskExe(taskExeBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void login() {
        final byte[] message = new byte[200];
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Toasty.info(EscortApp.getInstance().getApplicationContext(), "请按手指", 0, true).show();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<Integer, byte[]>() {
                    @Override
                    public byte[] apply(Integer integer) throws Exception {
                        Device.openFinger(message);
                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                        }
                        byte[] finger = new byte[2000+152*200];
                        byte[] tz = new byte[513];
                        int result = Device.getImage(10000, finger, message);
                        if (result != 0) {
                            throw new Exception(new String(message, "GBK").trim());
                        }
                        result = Device.ImageToFeature(finger, tz, message);
                        if (result != 0) {
                            throw new Exception(new String(message, "GBK").trim());
                        }
                        return tz;
                    }
                })
                .map(new Function<byte[], WorkerBean>() {
                    @Override
                    public WorkerBean apply(byte[] bytes) throws Exception {
                        List<WorkerBean> workerBeanList = loginModel.loadWorker();
                        for (WorkerBean worker: workerBeanList) {
                            for (int i=0; i<10; i++) {
                                String mbFinger = worker.getFinger(i);
                                if (mbFinger == null || mbFinger.equals("")) {
                                    continue;
                                }
                                int result = Device.verifyFinger(mbFinger.trim(), new String(bytes).trim(), 3);

                                if (result == 0) {
                                    return worker;
                                }
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WorkerBean>() {
                    @Override
                    public void accept(WorkerBean workerBean) throws Exception {
                        Device.closeFinger(message);
                        if (loginView != null) {
                            loginView.loginSuccess(workerBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Device.closeFinger(message);
                        if (loginView != null) {
                            loginView.loginFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void loadConfig() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                Config config = loginModel.loadConfig();
                e.onNext(config);
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (loginView != null) {
                            loginView.loadConfigFailed();
                        }
                    }
                });
    }

    @Override
    public int loadWorkerSize() {
        return loginModel.loadWorkerSize();
    }

    @Override
    public void doDestroy() {
        loginView = null;
        loginModel = null;
    }
}
