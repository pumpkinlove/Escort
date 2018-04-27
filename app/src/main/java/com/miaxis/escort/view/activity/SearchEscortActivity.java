package com.miaxis.escort.view.activity;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchEscortAdapter;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.EscortBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchEscortActivity extends BaseActivity {

    @BindView(R.id.search_escort_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_search_escort)
    RecyclerView rvSearchEscort;

    private SearchEscortAdapter searchEscortAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_escort;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("押运员查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchEscortAdapter = new SearchEscortAdapter(this, new ArrayList<EscortBean>());
        rvSearchEscort.setAdapter(searchEscortAdapter);
        rvSearchEscort.setLayoutManager(new LinearLayoutManager(this));
        searchEscortAdapter.setOnItemClickListener(new SearchEscortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.create(new ObservableOnSubscribe<List<EscortBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<EscortBean>> e) throws Exception {
                e.onNext(EscortApp.getInstance().getDaoSession().getEscortBeanDao().loadAll());
            }
        })
                .subscribeOn(Schedulers.io())
                .compose(SearchEscortActivity.this.<List<EscortBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<EscortBean>>() {
                    @Override
                    public void accept(List<EscortBean> escortBeans) throws Exception {
                        searchEscortAdapter.setDataList(escortBeans);
                        searchEscortAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
