package com.miaxis.escort.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.device.Device;
import com.miaxis.escort.view.viewer.IFingerView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.UnsupportedEncodingException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/25.
 */

public class FingerPresenterImpl extends BaseActivityPresenter implements IFingerPresenter {

    private IFingerView fingerView;

    public FingerPresenterImpl(LifecycleProvider<ActivityEvent> provider, IFingerView fingerView) {
        super(provider);
        this.fingerView = fingerView;
    }

    public void regFinger() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] image = new byte[2000+152*200];
                byte[] message = new byte[200];
                byte[][] tz = new byte[4][513];
                byte[] mb = new byte[513];
                Device.openFinger(message);
                Device.openRfid(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                for (int i = 0; i < 4; i++) {
                    if (fingerView != null) {
                        fingerView.updateTip("请按第" + i + "次手指");
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    int r = com.device.Device.getImage(10000, image, message);
                    if (r != 0) {
                        if (fingerView != null) {
                            String error = newGBKString(message);
                            fingerView.updateTip(error);
                        }
                        return;
                    }
                    if (fingerView != null) {
                        fingerView.updateImage(BitmapFactory.decodeByteArray(image, 0, image.length));
                    }
                    r = com.device.Device.ImageToFeature(image, tz[i], message);
                    if (r != 0) {
                        if (fingerView != null) {
                            String error = newGBKString(message);
                            fingerView.updateTip(error);
                        }
                        return;
                    }
                }
                int r = Device.FeatureToTemp(tz[0], tz[1], tz[2], mb,
                        message);
                if (r != 0) {
                    if (fingerView != null) {
                        String error = newGBKString(message);
                        fingerView.updateTip(error);
                    }
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                r = com.device.Device.verifyBinFinger(mb, tz[3], 3);
                if (r != 0) {
                    if (fingerView != null) {
                        String error = newGBKString(message);
                        fingerView.updateTip(error);
                    }
                    return;
                }
                String str = new String(Base64.encode(mb, Base64.DEFAULT));
                if (fingerView != null) {
                    fingerView.register(str);
                }
                Device.closeFinger(message);
                Device.closeRfid(message);
            }
        }).start();
    }

    private String newGBKString(byte[] bytes) {
        try {
            return new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    @Override
    public void doDestroy() {
        fingerView = null;
    }
}
