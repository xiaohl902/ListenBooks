package com.mwkj.fragment.home_fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mwkj.activity.R;
import com.mwkj.activity.SearchActivity;
import com.mwkj.adapter.HomeAdapter;
import com.qf.kenlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//听书馆
public class HomeFragment extends BaseFragment {
    @Bind(R.id.home_tab)
    TabLayout tabLayout;
    @Bind(R.id.home_viewPager)
    ViewPager homeViewPager;
    @Bind(R.id.search)
    LinearLayout search;
    private HomeAdapter adapter ;

    //接收跳转广播
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected int getContentId() {
        return R.layout.fragment_home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void init(View view) {
        homeViewPager.setOffscreenPageLimit(3);
        List<Fragment> datas = new ArrayList<>();
        datas.add(new StorytellingFragment());//评书
        datas.add(new CrosstalkFragment());//相声
        datas.add(new SpecialFragment());//专题
        datas.add(new ArtistFragment());//艺术家

        adapter = new HomeAdapter(getChildFragmentManager());
        homeViewPager.setAdapter(adapter);
        adapter.setDatas(datas);

        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //关联TabLayout和ViewPager
        tabLayout.setupWithViewPager(homeViewPager);

        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(homeViewPager);

        //接收广播
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("log", "onReceive: " +"收到广播");
                tabLayout.getTabAt(2).select();
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.qf.intent.turn");//自定义的action

        getContext().registerReceiver(broadcastReceiver, intentFilter);

        //跳转到搜索activity
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(broadcastReceiver != null){
            getContext().unregisterReceiver(broadcastReceiver);
        }
    }
}
