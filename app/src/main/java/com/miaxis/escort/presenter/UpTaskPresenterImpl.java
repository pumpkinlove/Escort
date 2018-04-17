package com.miaxis.escort.presenter;

import com.miaxis.escort.model.IUpTaskModel;
import com.miaxis.escort.model.UpTaskModelImpl;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.view.viewer.IUpTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/11.
 */

public class UpTaskPresenterImpl extends BaseFragmentPresenter implements IUpTaskPresenter{

    private IUpTaskView upTaskView;
    private IUpTaskModel upTaskModel;

    public UpTaskPresenterImpl(LifecycleProvider<FragmentEvent> provider, IUpTaskView upTaskView) {
        super(provider);
        this.upTaskView = upTaskView;
        upTaskModel = new UpTaskModelImpl(this);
    }

    @Override
    public void loadBox() {
        Observable.create(new ObservableOnSubscribe<List<BoxBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BoxBean>> e) throws Exception {
                e.onNext(upTaskModel.loadBox());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<BoxBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeans) throws Exception {
                        upTaskView.updateBox(boxBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void doDestroy() {
        upTaskView = null;
        upTaskModel = null;
    }
}
