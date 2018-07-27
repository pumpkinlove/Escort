package com.miaxis.escort.presenter;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.model.local.greenDao.gen.WorkerBeanDao;
import com.miaxis.escort.model.retrofit.AppNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ISystemView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

/**
 * Created by 一非 on 2018/4/18.
 */

public class SystemPresenterImpl extends BaseFragmentPresenter implements ISystemPresenter{

    private ISystemView systemView;

    public SystemPresenterImpl(LifecycleProvider<FragmentEvent> provider, ISystemView systemView) {
        super(provider);
        this.systemView = systemView;
    }

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
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
        Observable<ResponseEntity<String>> observableUrl = appNet.getAppUrl();
        Observable.zip(observableVersion, observableLength, observableTime, observableUrl, new Function4<ResponseEntity<String>, ResponseEntity<String>, ResponseEntity<String>, ResponseEntity<String>, String>() {
            @Override
            public String apply(ResponseEntity<String> stringResponseEntity, ResponseEntity<String> stringResponseEntity2, ResponseEntity<String> stringResponseEntity3, ResponseEntity<String> stringResponseEntity4) throws Exception {
                StringBuilder message = new StringBuilder();
                if (StaticVariable.SUCCESS.equals(stringResponseEntity.getCode()) &&
                        StaticVariable.SUCCESS.equals(stringResponseEntity2.getCode()) &&
                        StaticVariable.SUCCESS.equals(stringResponseEntity3.getCode()) &&
                        StaticVariable.SUCCESS.equals(stringResponseEntity4.getCode())) {
                    message.append(stringResponseEntity.getData() + "&");
                    message.append(stringResponseEntity2.getData() + "&");
                    message.append(stringResponseEntity3.getData() + "&");
                    message.append(stringResponseEntity4.getData());
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
                            systemView.downAppMessageFailed("网络错误");
                        }
                    }
                });
    }

    @Override
    public void download(URL url, String path) {
        FileDownloader.getImpl().create(url.toString())
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }
                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }
                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int percent=(int) ((double) soFarBytes / (double) totalBytes * 100);
                        if (systemView != null) {
                            systemView.updateProgress(percent);
                        }
                    }
                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }
                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        systemView.downloadSuccess(task.getPath());
                    }
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        if (systemView != null) {
                            systemView.downloadPause();
                        }
                    }
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        systemView.downloadFailed(e.getMessage());
                    }
                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    @Override
    public void showCurWorker() {
        WorkerBean workerBean = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
        systemView.showCurWorker(workerBean);
    }

    @Override
    public void doDestroy() {
        systemView = null;
    }
}
