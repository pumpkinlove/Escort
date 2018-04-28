package com.miaxis.escort.presenter;

import android.util.Base64;

import com.device.Device;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.WorkerBean;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IVerifyTaskDialogView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/27.
 */

public class VerifyTaskDialogPresenterImpl extends BaseFragmentPresenter implements IVerifyTaskDialogPresenter{

    private IVerifyTaskDialogView verifyTaskDialogView;
    private boolean pause = false;

    public VerifyTaskDialogPresenterImpl(LifecycleProvider<FragmentEvent> provider, IVerifyTaskDialogView verifyTaskDialogView) {
        super(provider);
        this.verifyTaskDialogView = verifyTaskDialogView;
    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void verifyCar(TaskBean taskBean) {
        final byte[] message = new byte[200];
        Observable.just(taskBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<TaskBean>() {
                    @Override
                    public void accept(TaskBean taskBean) throws Exception {
                        Device.openRfid(message);
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                        }
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.playVoiceMessageOnUIThread("请验证车辆");
                        }
                    }
                })
                .doOnNext(new Consumer<TaskBean>() {
                    @Override
                    public void accept(TaskBean taskBean) throws Exception {
                        boolean b = false;
                        while (true) {
                            if (pause) {
                                pause = false;
                                throw new Exception("取消验证");
                            }
                            if (b) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e) {
                            }
                            byte[] tids = new byte[20000];
                            byte[] epcids = new byte[20000];
                            int result = Device.getRfid(1000, tids, epcids, message);
                            if (result != 0) {
                                continue;
                            }
                            String rfids = new String(epcids).trim();
                            String[] rfidArr = rfids.split(",");
                            for (String aRfidArr : rfidArr) {
                                if ((aRfidArr.equals(taskBean.getCarRfid()))) {
                                    b = true;
                                    break;
                                }
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TaskBean>() {
                    @Override
                    public void accept(TaskBean taskBean) throws Exception {
                        Device.closeRfid(message);
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifySuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifyFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void verifyWorker() {
        final byte[] message = new byte[200];
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, WorkerBean>() {
                    @Override
                    public WorkerBean apply(Integer integer) throws Exception {
                        while (true) {
                            Device.openFinger(message);
                            if (pause) {
                                throw new Exception("取消");
                            }
                            try {
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e) {
                            }
                            if (verifyTaskDialogView != null) {
                                verifyTaskDialogView.playVoiceMessageOnUIThread("请网点员工按指纹");
                            }
                            byte[] finger = new byte[2000+152*200];
                            byte[] tz = new byte[513];
                            int result = Device.getImage(10000, finger, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            result = Device.ImageToFeature(finger, tz, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            List<WorkerBean> workerBeanList = EscortApp.getInstance().getDaoSession().getWorkerBeanDao().loadAll();
                            Iterator<WorkerBean> iterator = workerBeanList.iterator();
                            WorkerBean self = (WorkerBean) EscortApp.getInstance().get(StaticVariable.WORKER);
                            while (iterator.hasNext()) {
                                WorkerBean mWorkerBean = iterator.next();
                                if (mWorkerBean.getWorkno().equals(self.getWorkno())) {
                                    iterator.remove();
                                    break;
                                }
                            }
                            for (WorkerBean worker : workerBeanList) {
                                for (int i = 0; i < 10; i++) {
                                    String mbFinger = worker.getFinger(i);
                                    if (mbFinger == null || mbFinger.equals("")) {
                                        continue;
                                    }
                                    result = Device.verifyBinFinger(Base64.decode(mbFinger, Base64.DEFAULT), tz, 3);
                                    if (result == 0) {
                                        return worker;
                                    }
                                }
                            }
                            if (verifyTaskDialogView != null) {
                                verifyTaskDialogView.showToasty("未找到指纹对应员工");
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WorkerBean>() {
                    @Override
                    public void accept(WorkerBean workerBean) throws Exception {
                        Device.closeFinger(message);
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifySuccess();
                            verifyTaskDialogView.setVerifyWorker(workerBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifyFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void verifyEscort(final List<EscortBean> escortBeanList) {
        final byte[] message = new byte[200];
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Integer>bindToLifecycle())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, EscortBean>() {
                    @Override
                    public EscortBean apply(Integer integer) throws Exception {
                        while (true) {
                            Device.openFinger(message);
                            if (pause) {
                                throw new Exception("取消");
                            }
                            try {
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e) {
                            }
                            String str = "";
                            if (verifyTaskDialogView != null) {
                                if (escortBeanList.size() == 2) {
                                    str = "第一个";
                                } else if(escortBeanList.size() == 1) {
                                    str = "第二个";
                                }
                                verifyTaskDialogView.playVoiceMessageOnUIThread("请" + str + "押运员按指纹");
                            }
                            byte[] finger = new byte[2000+152*200];
                            byte[] tz = new byte[513];
                            int result = Device.getImage(10000, finger, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            result = Device.ImageToFeature(finger, tz, message);
                            if (result != 0) {
                                throw new Exception(new String(message, "GBK"));
                            }
                            for (EscortBean escortBean : escortBeanList) {
                                for (int i = 0; i < 10; i++) {
                                    String mbFinger = escortBean.getFinger(i);
                                    if (mbFinger == null || mbFinger.equals("")) {
                                        continue;
                                    }
                                    result = Device.verifyBinFinger(Base64.decode(mbFinger, Base64.DEFAULT), tz, 3);
                                    if (result == 0) {
                                        return escortBean;
                                    }
                                }
                            }
                            if (verifyTaskDialogView != null) {
                                verifyTaskDialogView.showToasty("未找到指纹对应员工");
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EscortBean>() {
                    @Override
                    public void accept(EscortBean escortBean) throws Exception {
                        Device.closeFinger(message);
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifySuccess();
                            verifyTaskDialogView.removeVerified(escortBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (verifyTaskDialogView != null) {
                            verifyTaskDialogView.verifyFailed(throwable.getMessage());
                        }
                    }
                });
    }

    @Override
    public void doDestroy() {
        verifyTaskDialogView = null;
    }
}
