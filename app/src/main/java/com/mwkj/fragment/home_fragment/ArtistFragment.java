package com.mwkj.fragment.home_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwkj.activity.ArtistInfoActivity;
import com.mwkj.activity.R;
import com.mwkj.adapter.ArtistAdapter;
import com.mwkj.entity.ArtEntity;
import com.mwkj.util.Constant;
import com.qf.kenlibrary.base.BaseFragment;
import com.qf.kenlibrary.util.DownUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

//Fragment--艺术家
public class ArtistFragment extends BaseFragment implements DownUtil.OnDownListener, ArtistAdapter.OnItemClickListener {
    private List<ArtEntity> da;

    //默认加载第一页数据
    int pageSize = 10;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;

    //RecyclerView自带的下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getContentId() {
        return R.layout.fragment_home_artist;
    }

    @Override
    protected void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.artist_cv);
        //流式布局,2列排布
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        artistAdapter = new ArtistAdapter(getContext());
        recyclerView.setAdapter(artistAdapter);


    }



    @Override
    protected void loadDatas() {
        //url的字符串拼接
        String downurl = String.format(Constant.ARTIST,pageSize);
        new DownUtil().setOnDownListener(this).downJSON(downurl);

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srl);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);//下拉刷新圆圈背景色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#50a913"));//下拉刷新 转动圆圈颜色(主题色)
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GREEN);
//        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("print", "------>开始刷新，加载数据");

                //页数++，重新加载
                pageSize += 10;
                loadDatas();

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //数据加载完成
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //关闭刷新
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }.start();
            }
        });
    }



    @Override
    public Object paresJson(String json) {
        if (json != null){
            try {
                JSONArray artists = new JSONObject(json).optJSONArray("artists");
                TypeToken<List<ArtEntity>> tt = new TypeToken<List<ArtEntity>>(){};
                return new Gson().fromJson(artists.toString(),tt.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void downSucc(Object object) {
        if (object != null) {
            da = (List<ArtEntity>) object;
            artistAdapter.setDatas(da);

            //item点击监听
            artistAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(getActivity(),ArtistInfoActivity.class);
        //用intent将艺术家id及头像传送至跳转后页面
        intent.putExtra("artistid",da.get(position).getArtistId());
        intent.putExtra("artistimg",da.get(position).getArtistImg());
        startActivity(intent);
    }
}
