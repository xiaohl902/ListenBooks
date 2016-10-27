package com.mwkj.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter{

   private String [] title_home = {"评书","相声","专题","艺术家"};
    private List<Fragment> datas;

    public HomeAdapter(FragmentManager fm) {
        super(fm);
        datas = new ArrayList<>();
    }

    public void setDatas(List<Fragment> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_home[position];
    }
}
