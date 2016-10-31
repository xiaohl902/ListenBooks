package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.adapter.ComTopicAdapter;
import com.mwkj.entity.CommunityTopicEntity;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.widget.pullRefreshListview.XupListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/10/31.
 */
public class ComSomethingActivity extends BaseActivity implements AdapterView.OnItemClickListener, XupListView.IXListViewListener {

    @Bind(R.id.workinfo_tv_name)
    TextView workinfoTvName;
    @Bind(R.id.com_something_lv)
    XupListView comSomethingLv;

    //根据type值判断是刷新，还是加载更多
    int type = 0;
    //Handler处理下拉和上滑
    private Handler handler;
    int fourmId = -1;
    int pageSize = 20;

    private ComTopicAdapter adapter;

    private CommunityTopicEntity communityTopicEntity;
    private List<CommunityTopicEntity.TopicsBean> data;

    @Override
    protected int getContentId() {
        return R.layout.activity_com_something;
    }

    @Override
    protected void init() {
        Intent in = getIntent();
        fourmId = in.getIntExtra("fourmId",-1);
        String titlename = in.getStringExtra("titlename");
        workinfoTvName.setText(titlename);
        handler = new Handler();
        adapter = new ComTopicAdapter(this);
        comSomethingLv.setAdapter(adapter);
        comSomethingLv.setOnItemClickListener(this);
        comSomethingLv.setPullRefreshEnable(true);
        comSomethingLv.setPullLoadEnable(true);
        comSomethingLv.setAutoLoadEnable(true);
        comSomethingLv.setXListViewListener(this);
        comSomethingLv.setRefreshTime(getTime());
    }

    @Override
    protected void loadDatas() {
        Call<CommunityTopicEntity> call = AppStartContext.utils.getsomething(fourmId,pageSize);
        call.enqueue(new Callback<CommunityTopicEntity>() {
            @Override
            public void onResponse(Call<CommunityTopicEntity> call, Response<CommunityTopicEntity> response) {
               communityTopicEntity = response.body();
                data = communityTopicEntity.getTopics();
                if (type == 0) {
                    adapter.setDatas(data);
                } else {
                    adapter.addDatas(data);
                }
            }

            @Override
            public void onFailure(Call<CommunityTopicEntity> call, Throwable t) {

            }
        });


    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.workinfo_back, R.id.workinfo_music, R.id.search_trip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workinfo_back:
                finish();
                break;
            case R.id.workinfo_music:
                break;
            case R.id.search_trip:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //刷新监听
    @Override
    public void onRefresh() {
        type = 0;
//        pageSize = 20;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDatas();
                onLoad();
            }
        }, 2500);

    }

    @Override
    public void onLoadMore() {
//        type = 1;
        pageSize += 10;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadDatas();
                onLoad();
            }
        }, 2000);
    }


    private void onLoad() {
        comSomethingLv.stopRefresh();
        comSomethingLv.stopLoadMore();
        comSomethingLv.setRefreshTime(getTime());
    }
}
