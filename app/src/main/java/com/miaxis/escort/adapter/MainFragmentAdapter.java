package com.miaxis.escort.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragmentLis) {
        super(fm);
        this.fragmentList = fragmentLis;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList == null) {
            return 0;
        }
        return fragmentList.size();
    }
}
