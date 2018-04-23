package com.miaxis.escort.presenter;

import com.miaxis.escort.model.ISearchTaskModel;
import com.miaxis.escort.model.SearchTaskModelImpl;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.view.viewer.ISearchTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 一非 on 2018/4/23.
 */

public class SearchTaskPresenterImpl extends BaseActivityPresenter implements ISearchTaskPresenter {

    private ISearchTaskView searchTaskView;
    private ISearchTaskModel searchTaskModel;

    public SearchTaskPresenterImpl(LifecycleProvider<ActivityEvent> provider, ISearchTaskView searchTaskView) {
        super(provider);
        this.searchTaskView = searchTaskView;
        this.searchTaskModel = new SearchTaskModelImpl();
    }

    @Override
    public void loadTaskByDate(String date) {
        Observable.just(date)
                .subscribeOn(Schedulers.io())
                .compose(getProvider().<String>bindToLifecycle())
                .observeOn(Schedulers.io())
                .map(new Function<String, List<TaskBean>>() {
                    @Override
                    public List<TaskBean> apply(String s) throws Exception {
                        return searchTaskModel.queryTaskByDate(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskBean>>() {
                    @Override
                    public void accept(List<TaskBean> taskBeanList) throws Exception {
                        if (searchTaskView != null) {
                            searchTaskView.updateDataList(taskBeanList);
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
        searchTaskView = null;
    }
}
