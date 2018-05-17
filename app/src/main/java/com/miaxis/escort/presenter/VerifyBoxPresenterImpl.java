package com.miaxis.escort.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.device.Device;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.IVerifyBoxModel;
import com.miaxis.escort.model.VerifyBoxModelImpl;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.model.retrofit.TaskNet;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.IVerifyBoxView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 一非 on 2018/4/20.
 */
@SuppressLint("CheckResult")
public class VerifyBoxPresenterImpl extends BaseActivityPresenter implements IVerifyBoxPresenter{

    private IVerifyBoxView verifyBoxView;
    private IVerifyBoxModel verifyBoxModel;
    private boolean pause = false;

    public VerifyBoxPresenterImpl(LifecycleProvider<ActivityEvent> provider, IVerifyBoxView verifyBoxView) {
        super(provider);
        this.verifyBoxView = verifyBoxView;
        verifyBoxModel = new VerifyBoxModelImpl();
    }


    @Override
    public void loadBox(TaskBean taskBean) {
        Observable.just(taskBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .map(new Function<TaskBean, List<BoxBean>>() {
                    @Override
                    public List<BoxBean> apply(TaskBean taskBean) throws Exception {
                        return verifyBoxModel.loadBoxListByTask(taskBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeans) throws Exception {
                        if (verifyBoxView != null) {
                            verifyBoxView.updateBoxList(boxBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toasty.error(EscortApp.getInstance().getApplicationContext(), "未找到相关箱包", 0, true).show();
                    }
                });
    }

    @Override
    public void upTaskExe(final TaskExeBean taskExeBean) {
        Observable.just(taskExeBean)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<TaskExeBean>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<TaskExeBean, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(TaskExeBean taskExeBean) throws Exception {
                        Config config = (Config) EscortApp.getInstance().get(StaticVariable.CONFIG);
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        verifyBoxModel.updateTaskStatus(taskExeBean);
                        TaskNet taskNet = retrofit.create(TaskNet.class);
                        return taskNet.uploadTaskExec(new Gson().toJson(taskExeBean));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        if (verifyBoxView != null) {
                            if (StaticVariable.SUCCESS.equals(responseEntity.getCode())) {
                                verifyBoxView.uploadSuccess();
                            } else {
                                verifyBoxView.uploadFailed(responseEntity.getMessage());
                                verifyBoxModel.saveLocal(taskExeBean);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (verifyBoxView != null) {
                            verifyBoxView.uploadFailed(throwable.getMessage());
                            verifyBoxModel.saveLocal(taskExeBean);
                        }
                    }
                });
    }

    @Override
    public void verifyBox(List<BoxBean> boxBeanList) {
        final byte[] message = new byte[200];
        Observable.just(boxBeanList)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<BoxBean>>bindToLifecycle())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeanList) throws Exception {
                        Device.openRfid(message);
                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                        }
                    }
                })
                .doOnNext(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeanList) throws Exception {
                        while (true) {
                            if (pause) {
                                pause = false;
                                throw new Exception("取消验证");
                            }
                            try {
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e) {
                            }
                            byte[] tids = new byte[20000];
                            byte[] epcids = new byte[20000];
                            int result = Device.getRfid(1000, tids, epcids, message);
                            if (verifyBoxView != null) {
                                verifyBoxView.addCount();
                            }
                            if (result != 0) {
                                continue;
                            }
                            String rfids = new String(epcids).trim();
                            String[] rfidArr = rfids.split(",");
                            Iterator<BoxBean> iterator = boxBeanList.iterator();
                            for (int i=0; i<rfidArr.length; i++) {
                                while (iterator.hasNext()) {
                                    BoxBean boxBean = iterator.next();
                                    if ((boxBean.getRfid().equals(rfidArr[i]))) {
                                        if(("0".equals(boxBean.getCheckStatus()) || null == boxBean.getCheckStatus())){
                                            if (verifyBoxView != null) {
                                                verifyBoxView.updateVerify(boxBean);
                                            }
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            if (boxBeanList.size() == 0) {
                                break;
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeanList) throws Exception {
                        Toasty.info(EscortApp.getInstance(), "扫描完毕", Toast.LENGTH_SHORT);
                        Device.closeRfid(message);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toasty.error(EscortApp.getInstance(), "扫描完毕", Toast.LENGTH_SHORT);
                        Device.closeRfid(message);
                    }
                });
    }

    public void pause() {
        pause = true;
    }

    @Override
    public void doDestroy() {
        verifyBoxView = null;
    }
}
