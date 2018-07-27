package com.miaxis.escort.presenter;

import android.util.Log;

import com.device.Device;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.event.ResultEvent;
import com.miaxis.escort.event.VoiceEvent;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.view.fragment.ExecuteTaskDialogFragment;
import com.miaxis.escort.view.viewer.IExecuteDialogView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tang.yf on 2018/7/27.
 */

public class ExecuteTaskDialogPresenterImpl extends BaseFragmentPresenter implements IExecuteTaskDialogPresenter {

    private IExecuteDialogView executeDialogView;
    private int workerNum;
    private int escortNum;
    private List<WorkerBean> workerBeanList;
    private List<EscortBean> escortBeanList;
    private ArrayList<WorkerBean> workerBeanCacheList;
    private ArrayList<EscortBean> escortBeanCacheList;
    private boolean workerRun = true;
    private boolean escortRun = true;

    public ExecuteTaskDialogPresenterImpl(LifecycleProvider<FragmentEvent> provider, IExecuteDialogView executeDialogView) {
        super(provider);
        this.executeDialogView = executeDialogView;
    }

    @Override
    public void init(int workerNum, List<WorkerBean> workerBeanList, int escortNum, List<EscortBean> escortBeanList) {
        this.workerNum = workerNum;
        this.workerBeanList = workerBeanList;
        this.escortNum = escortNum;
        this.escortBeanList = escortBeanList;
        workerBeanCacheList = new ArrayList<>();
        escortBeanCacheList = new ArrayList<>();
    }

    @Override
    public void startVerify() {
        workerRun = true;
        escortRun = false;
        verifyWorker();
    }

    private void verifyWorker() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                byte[] message = new byte[200];
                Device.openFinger(message);
                Thread.sleep(500);
                for (int i = 1; i <= workerNum; i++) {
                    e.onNext(i);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        while (workerRun) {
                            Log.e("asd", "worker");
                            byte[] message = new byte[200];
                            byte[] finger = new byte[2000+152*200];
                            byte[] tz = new byte[513];
                            EventBus.getDefault().post(new VoiceEvent("请网点员工按手指"));
                            int result = Device.getImage(10000, finger, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            result = Device.ImageToFeature(finger, tz, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            Iterator<WorkerBean> iterator = workerBeanList.iterator();
                            while (iterator.hasNext()) {
                                WorkerBean worker = iterator.next();
                                for (int i = 0; i < 10; i++) {
                                    String mbFinger = worker.getFinger(i);
                                    if (mbFinger == null || mbFinger.equals("")) {
                                        continue;
                                    }
                                    result = Device.verifyFinger(mbFinger.trim(), new String(tz).trim(), 3);
                                    if (result == 0) {
                                        workerBeanCacheList.add(worker);
                                        iterator.remove();
                                        EventBus.getDefault().post(new ResultEvent(ResultEvent.VERIFY_WORKER_SUCCESS, worker, integer));
                                        return;
                                    }
                                }
                            }
                            EventBus.getDefault().post(new ResultEvent(ResultEvent.VERIFY_FAILED));
                            Thread.sleep(300);
                        }
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("asd", "员工比对成功" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("asd", "员工验证跳出" + throwable.getMessage());
                        startVerify();
                    }
                });
    }

    @Override
    public void startVerifyEscort() {
        workerRun = false;
        escortRun = true;
        verifyEscort();
    }

    private void verifyEscort() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                byte[] message = new byte[200];
                Device.openFinger(message);
                Thread.sleep(500);
                for (int i = 1; i <= escortNum; i++) {
                    e.onNext(i);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        while (escortRun) {
                            Log.e("asd", "escort");
                            byte[] message = new byte[200];
                            byte[] finger = new byte[2000+152*200];
                            byte[] tz = new byte[513];
                            EventBus.getDefault().post(new VoiceEvent("请押运员按手指"));
                            int result = Device.getImage(10000, finger, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            result = Device.ImageToFeature(finger, tz, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            Iterator<EscortBean> iterator = escortBeanList.iterator();
                            while (iterator.hasNext()) {
                                EscortBean escortBean = iterator.next();
                                for (int i = 0; i < 10; i++) {
                                    String mbFinger = escortBean.getFinger(i);
                                    if (mbFinger == null || mbFinger.equals("")) {
                                        continue;
                                    }
                                    result = Device.verifyFinger(mbFinger.trim(), new String(tz).trim(), 3);
                                    if (result == 0) {
                                        escortBeanCacheList.add(escortBean);
                                        iterator.remove();
                                        EventBus.getDefault().post(new ResultEvent(ResultEvent.VERIFY_ESCORT_SUCCESS, escortBean, integer));
                                        return;
                                    }
                                }
                            }
                            EventBus.getDefault().post(new ResultEvent(ResultEvent.VERIFY_FAILED));
                            Thread.sleep(300);
                        }
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("asd", "押运员比对成功" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("asd", "押运员验证跳出" + throwable.getMessage());
                        startVerifyEscort();
                    }
                });
    }

    @Override
    public void closeThread() {
        workerRun = false;
        escortRun = false;
    }

    @Override
    public ArrayList<WorkerBean> getWorkerCache() {
        return workerBeanCacheList;
    }

    @Override
    public ArrayList<EscortBean> getEscortCache() {
        return escortBeanCacheList;
    }

    @Override
    public void doDestroy() {
        executeDialogView = null;
    }
}
