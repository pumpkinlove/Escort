package com.miaxis.escort.presenter;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.ISearchBoxModel;
import com.miaxis.escort.model.SearchBoxModelImpl;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.retrofit.BoxNet;
import com.miaxis.escort.model.retrofit.ResponseEntity;
import com.miaxis.escort.util.StaticVariable;
import com.miaxis.escort.view.viewer.ISearchBoxView;
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
 * Created by 一非 on 2018/4/17.
 */

public class SearchBoxPresenterImpl extends BaseActivityPresenter implements ISearchBoxPresenter{

    private ISearchBoxView searchBoxView;
    private ISearchBoxModel searchBoxModel;

    public SearchBoxPresenterImpl(LifecycleProvider<ActivityEvent> provider, ISearchBoxView searchBoxView) {
        super(provider);
        this.searchBoxView = searchBoxView;
        searchBoxModel = new SearchBoxModelImpl();
    }

    @Override
    public void loadSearchBox() {
        Observable.create(new ObservableOnSubscribe<List<BoxBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BoxBean>> e) throws Exception {
                e.onNext(searchBoxModel.loadSearchBox());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<List<BoxBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeans) throws Exception {
                        if (searchBoxView != null) {
                            searchBoxView.updateData(boxBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void downSearchBox() {
        Observable.create(new ObservableOnSubscribe<Config>() {
            @Override
            public void subscribe(ObservableEmitter<Config> e) throws Exception {
                e.onNext((Config) EscortApp.getInstance().get(StaticVariable.CONFIG));
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<Config>bindToLifecycle())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Config, Observable<ResponseEntity<BoxBean>>>() {
                    @Override
                    public Observable<ResponseEntity<BoxBean>> apply(Config config) throws Exception {
                        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                                .build();
                        BoxNet boxNet = retrofit.create(BoxNet.class);
                        return boxNet.downloadBox(config.getOrgCode(), new Gson().toJson(searchBoxModel.getBoxOpdate()));
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<BoxBean>>() {
                    @Override
                    public void accept(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        if (StaticVariable.SUCCESS.equals(boxBeanResponseEntity.getCode())) {
                            searchBoxModel.saveSearchBox(boxBeanResponseEntity.getListData());
                        }
                    }
                })
                .map(new Function<ResponseEntity<BoxBean>, List<BoxBean>>() {
                    @Override
                    public List<BoxBean> apply(ResponseEntity<BoxBean> boxBeanResponseEntity) throws Exception {
                        return searchBoxModel.loadSearchBox();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BoxBean>>() {
                    @Override
                    public void accept(List<BoxBean> boxBeans) throws Exception {
                        if (searchBoxView != null) {
                            searchBoxView.updateData(boxBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toasty.error(EscortApp.getInstance().getApplicationContext(), "刷新失败",0, true).show();
                    }
                });
    }

    @Override
    public void doDestroy() {
        this.searchBoxView = null;
        searchBoxModel = null;
    }
}
