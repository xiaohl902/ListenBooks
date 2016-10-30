package com.mwkj.fragment.home_fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.activity.R;
import com.mwkj.adapter.ZspecialAdapter;
import com.mwkj.entity.SpecialEntity;
import com.qf.kenlibrary.base.BaseFragment;
import com.qf.kenlibrary.widget.pullRefreshListview.XupListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//专题
public class SpecialFragment extends BaseFragment implements XupListView.IXListViewListener, AdapterView.OnItemClickListener {
    @Bind(R.id.lv)
    XupListView mListView;
    private int pageNumber = 1;
    private ZspecialAdapter adapter;
    //处理下拉刷新上拉加载
    private Handler handler;
    //根据type值判断是刷新，还是加载更多
    int type = 0;
    private SpecialEntity specialEntity;

    @Override
    protected int getContentId() {
        return R.layout.fragment_home_special;
    }

    @Override
    protected void init(View view) {

        adapter = new ZspecialAdapter(getContext());
        mListView.setAdapter(adapter);
        handler = new Handler();
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
    }

    @Override
    protected void loadDatas() {
        Call<SpecialEntity> call = AppStartContext.utils.downSpecialEntity(pageNumber);
        call.enqueue(new Callback<SpecialEntity>() {
            @Override
            public void onResponse(Call<SpecialEntity> call, Response<SpecialEntity> response) {
                specialEntity = response.body();
                if (type == 0) {
                    adapter.setDatas(specialEntity.getSubjects());
                }else {
                    adapter.addDatas(specialEntity.getSubjects());
                }
            }

            @Override
            public void onFailure(Call<SpecialEntity> call, Throwable t) {

            }
        });
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    public void onRefresh() {
        type = 0;
        pageNumber = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDatas();
                onLoad();
            }
        }, 2000);
        
    }

    @Override
    public void onLoadMore() {
        type = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNumber++;
                loadDatas();
                onLoad();
            }
        },2000);
    }
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("log", "onItemClick: " +i+"----"+l);
    }
}
