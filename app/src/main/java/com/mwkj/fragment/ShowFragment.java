package com.mwkj.fragment;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.activity.R;
import com.mwkj.activity.ShowDetailsActivity;
import com.mwkj.adapter.ShowAdapter;
import com.mwkj.entity.ShowEntity;
import com.qf.kenlibrary.base.BaseFragment;
import com.qf.kenlibrary.widget.pullRefreshListview.XupListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//演出
public class ShowFragment extends BaseFragment implements AdapterView.OnItemClickListener, XupListView.IXListViewListener {
    @Bind(R.id.mListView)
    XupListView mListView;
    private ShowAdapter showAdapter;
    int pageNumber = 1;
    private Handler handler;
    int type = 0;
    @Override
    protected int getContentId() {
        return R.layout.fragment_show;
    }

    @Override
    protected void init(View view) {
        showAdapter = new ShowAdapter(getContext());
        mListView.setAdapter(showAdapter);
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
        Call<ShowEntity> call = AppStartContext.utils.getShowEnityByUrl(pageNumber,null,null);
        call.enqueue(new Callback<ShowEntity>() {
            @Override
            public void onResponse(Call<ShowEntity> call, Response<ShowEntity> response) {
                ShowEntity showEntity = response.body();
                if (type == 0) {
                    showAdapter.setDatas(showEntity.getShows());
                }else {
                    showAdapter.addDatas(showEntity.getShows());
                }
            }

            @Override
            public void onFailure(Call<ShowEntity> call, Throwable t) {

            }
        });
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("log", "onItemClick: " +i);
        ShowEntity.ShowsBean showsBean= (ShowEntity.ShowsBean) showAdapter.getItem(i-1);
        Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
        intent.putExtra("show",showsBean);
        getContext().startActivity(intent);
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
}
