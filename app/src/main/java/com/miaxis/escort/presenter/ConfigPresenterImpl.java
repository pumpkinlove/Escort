package com.miaxis.escort.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.ConfigModelImpl;
import com.miaxis.escort.model.IConfigModel;
import com.miaxis.escort.model.entity.BankBean;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.retrofit.BankNet;
import com.miaxis.escort.model.retrofit.BoxNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.WorkerNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IConfigView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

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
        final Config config = new Config(1L, ip, port, orgCode);
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + ip + ":" + port)
                .build();
        Observable.just(config)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        EscortApp.getInstance().clearAndRebuildDatabase();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        if (configView != null) {
                            configView.setProgressDialogMessage("清空数据成功，正在保存设置...");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configModel.saveConfig(config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        EscortApp.getInstance().put(StaticVariable.CONFIG, config);
                        if (configView != null) {
                            configView.setProgressDialogMessage("设置保存成功，正在下载银行信息...");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Config, Observable<ResponseEntity<BankBean>>>() {
                    @Override
                    public Observable<ResponseEntity<BankBean>> apply(Config config) throws Exception {
                        BankNet net = retrofit.create(BankNet.class);
                        Observable<ResponseEntity<BankBean>> o = net.downloadBank(config.getOrgCode());
                        return o;
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<BankBean>>() {
                    @Override
                    public void accept(ResponseEntity<BankBean> bankBeanResponseEntity) throws Exception {
                        if (bankBeanResponseEntity.getCode().equals("200")) {
                            configModel.saveBank(bankBeanResponseEntity.getData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<BankBean>>() {
                    @Override
                    public void accept(ResponseEntity<BankBean> bankBeanResponseEntity) throws Exception {
                        if (configView != null) {
                            if (bankBeanResponseEntity.getCode().equals("200")) {
                                configView.setProgressDialogMessage("下载银行信息成功，正在下载操作员信息...");
                            } else {
                                configView.setProgressDialogMessage(bankBeanResponseEntity.getMessage());
                            }
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<BankBean>, Observable<ResponseEntity<WorkerBean>>>() {
                    @Override
                    public Observable<ResponseEntity<WorkerBean>> apply(ResponseEntity<BankBean> bankBeanResponseEntity) throws Exception {
                        WorkerNet workerNet = retrofit.create(WorkerNet.class);
                        return workerNet.downloadWorker(config.getOrgCode(), new Gson().toJson(configModel.loadWorkerOpdate()));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<WorkerBean>>() {
                    @Override
                    public void accept(ResponseEntity<WorkerBean> workerBeanResponseEntity) throws Exception {
                        if (workerBeanResponseEntity.getCode().equals("200")) {
                            configModel.saveWorker(workerBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<WorkerBean>>() {
                    @Override
                    public void accept(ResponseEntity<WorkerBean> workerBeanResponseEntity) throws Exception {
                        if (configView != null && workerBeanResponseEntity.getCode().equals("200")) {
                            configView.setProgressDialogMessage("操作员下载完成，正在下载箱包信息...");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseEntity<WorkerBean>, Observable<ResponseEntity<BoxBean>>>() {
                    @Override
                    public Observable<ResponseEntity<BoxBean>> apply(ResponseEntity<WorkerBean> workerBeanResponseEntity) throws Exception {
                        BoxNet boxNet = retrofit.create(BoxNet.class);
                        return boxNet.downloadBox(config.getOrgCode(), new Gson().toJson(configModel.loadBoxOpdate()));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (boxBeanResponseEntity.getCode().equals("200")) {
                            configModel.saveBox(boxBeanResponseEntity.getListData());
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (configView != null) {
                            if (boxBeanResponseEntity.getCode().equals("200")) {
                                configView.setProgressDialogMessage("箱包信息下载完成，正在准备登录...");
                                configView.configSaveSuccess();
                            } else {
                                configView.setProgressDialogMessage(boxBeanResponseEntity.getMessage());
                                //该机构下没有箱包放行
                                configView.configSaveSuccess();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (configView != null) {
                            configView.setProgressDialogMessage("出现错误");
                            configView.configSaveFailed();
                        }
                    }
                });
    }

    @Override
    public void configSaveSuccess() {
        if (configView != null) {
            configView.configSaveSuccess();
        }
    }

    @Override
    public void configSaveFailed() {
        if (configView != null) {
            configView.configSaveFailed();
        }
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
                        if (configView != null) {
                            configView.fetchConfig(config);
                        }
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
    public void initWorker() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(configModel.getWorkerSize());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer == 0 && configView != null) {
                            Toasty.info(EscortApp.getInstance().getApplicationContext(),"该机构下未找到操作员，您可以添加第一个操作员", 1 ,true).show();
                            configView.initWorker();
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
        configView = null;
        configModel = null;
    }
}
