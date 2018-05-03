package com.miaxis.escort.view.activity;

        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.View;

        import com.jakewharton.rxbinding2.view.RxView;
        import com.miaxis.escort.R;
        import com.miaxis.escort.adapter.MainFragmentAdapter;
        import com.miaxis.escort.view.fragment.MyTaskFragment;
        import com.miaxis.escort.view.fragment.SystemFragment;
        import com.miaxis.escort.view.fragment.UpTaskFragment;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;

public class MainActivity extends BaseActivity implements UpTaskFragment.OnFragmentInteractionListener,
        MyTaskFragment.OnFragmentInteractionListener,
        SystemFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tl_main)
    TabLayout tlMain;

    private MainFragmentAdapter adapter;

    private List<Drawable> normalIconList;
    private List<Drawable> pressedIconList;

    private UpTaskFragment upTaskFragment;
    private MyTaskFragment myTaskFragment;
    private SystemFragment systemFragment;

    public static final String[] TITLES = {"申报任务", "我的任务", "系统服务"};

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        normalIconList = new ArrayList<>();
        normalIconList.add(getResources().getDrawable(R.drawable.tab_uptask_normal));
        normalIconList.add(getResources().getDrawable(R.drawable.tab_mytask_normal));
        normalIconList.add(getResources().getDrawable(R.drawable.tab_icon_setting_normal));
        pressedIconList = new ArrayList<>();
        pressedIconList.add(getResources().getDrawable(R.drawable.tab_uptask_pressed));
        pressedIconList.add(getResources().getDrawable(R.drawable.tab_mytask_pressd));
        pressedIconList.add(getResources().getDrawable(R.drawable.tab_icon_setting_pressed));
        List<Fragment> fragmentList = new ArrayList<>();
        upTaskFragment = UpTaskFragment.newInstance();
        fragmentList.add(upTaskFragment);
        myTaskFragment = MyTaskFragment.newInstance();
        fragmentList.add(myTaskFragment);
        systemFragment = SystemFragment.newInstance();
        fragmentList.add(systemFragment);
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void initView() {
        vpMain.setAdapter(adapter);
        vpMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlMain));
        tlMain.setupWithViewPager(vpMain, true);
        vpMain.setOffscreenPageLimit(20);
        initTabLayout();
        vpMain.setCurrentItem(1);
        tlMain.getTabAt(1).select();
    }

    private void initTabLayout() {

        for (int i = 0; i < tlMain.getTabCount(); i++) {
            TabLayout.Tab tab = tlMain.getTabAt(i);
            if (tab != null) {
                tab.setIcon(normalIconList.get(i));
                tab.setText(TITLES[i]);
            }
        }
        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < tlMain.getTabCount(); i++) {
                    TabLayout.Tab mTab = tlMain.getTabAt(i);
                    if (mTab != null) {
                        mTab.setIcon(normalIconList.get(i));
                    }
                }
                int position = tab.getPosition();
                toolbar.setVisibility(View.VISIBLE);
                tab.setIcon(pressedIconList.get(position));
                toolbar.setTitle(TITLES[position]);
                tab.setText(TITLES[position]);
                if (position == 2) {
                    toolbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onUpTaskFragmentInteraction() {

    }

    @Override
    public void refreshTask() {
        myTaskFragment.refreshTaskAfterUpTask();
    }

    @Override
    public void onMyTaskFragmentInteraction() {

    }

    @Override
    public void onSystemFragmentInteraction() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
