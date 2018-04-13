package com.miaxis.escort.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.miaxis.escort.R;
import com.miaxis.escort.adapter.SearchBoxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SearchBoxActivity extends BaseActivity {

    @BindView(R.id.search_box_toolbar)
    Toolbar toolbar;
    @BindView(R.id.srl_search_box)
    SwipeRefreshLayout srl_searchBox;
    @BindView(R.id.rv_search_box)
    RecyclerView rvSearchBox;

    private SearchBoxAdapter searchBoxAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_box;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("箱包查询");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<String> dataList = new ArrayList<>();
        dataList.add("箱包1");
        dataList.add("箱包2");
        dataList.add("箱包3");
        dataList.add("箱包4");
        dataList.add("箱包5");
        dataList.add("箱包6");
        searchBoxAdapter = new SearchBoxAdapter(this, dataList);
        rvSearchBox.setAdapter(searchBoxAdapter);
        rvSearchBox.setLayoutManager(new LinearLayoutManager(this));
        searchBoxAdapter.setOnItemClickListener(new SearchBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        srl_searchBox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.just(0)
                        .delay(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .compose(SearchBoxActivity.this.<Integer>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                srl_searchBox.setRefreshing(false);
                            }
                        });
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
