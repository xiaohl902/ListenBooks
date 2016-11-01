package com.mwkj.fragment.home_fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.mwkj.db.ArtistOpenHelper;
import com.mwkj.entity.ArtEntity;
import com.mwkj.util.Constant;
import com.qf.kenlibrary.base.BaseFragment;
import com.qf.kenlibrary.util.DownUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Fragment--艺术家
public class ArtistFragment extends BaseFragment implements DownUtil.OnDownListener, ArtistAdapter.OnItemClickListener {
    private List<ArtEntity> das;
    private List<ArtEntity> das2;
//    private ArtEntity artEntity;

    //默认加载第一页数据
    int pageSize = 12;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;

    //数据库相关
    private ArtistOpenHelper artistOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    //RecyclerView自带的下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    private int artistId;
    private List<ArtEntity> artlist;
    private String artistImg;
    private List<ArtEntity> da = new ArrayList<>();
    private List<ArtEntity> da2 = new ArrayList<>();

    @Override
    protected int getContentId() {
        artistOpenHelper = new ArtistOpenHelper(getContext());
        sqLiteDatabase = artistOpenHelper.getReadableDatabase();
        return R.layout.fragment_home_artist;
    }

    @Override
    protected void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.artist_cv);
        //流式布局,2列排布
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        artistAdapter = new ArtistAdapter(getContext());
        recyclerView.setAdapter(artistAdapter);
        //item点击监听
        artistAdapter.setOnItemClickListener(this);

    }



    @Override
    protected void loadDatas() {

        Cursor cursor = sqLiteDatabase.query("artists",
                new String[]{"artistId","artistName","artistWork","workNumber","playNumber","artistImg"},null,null,null,null,null);
        artlist = new ArrayList<>();
        while (cursor.moveToNext()){
            ArtEntity artEntity = new ArtEntity();
            artistId = cursor.getInt(cursor.getColumnIndex("artistId"));
            String artistName = cursor.getString(cursor.getColumnIndex("artistName"));
            String artistWork = cursor.getString(cursor.getColumnIndex("artistWork"));
            int workNumber = cursor.getInt(cursor.getColumnIndex("workNumber"));
            int playNumber = cursor.getInt(cursor.getColumnIndex("playNumber" ));
            artistImg = cursor.getString(cursor.getColumnIndex("artistImg"));
            artEntity.setArtistId(artistId);
            artEntity.setArtistName(artistName);
            artEntity.setArtistWork(artistWork);
            artEntity.setWorkNumber(workNumber);
            artEntity.setPlayNumber(playNumber);
            artEntity.setArtistImg(artistImg);
            artlist.add(artEntity);

        }

        if (artlist.size() > 0){
            da.clear();
//            da.removeAll(da);
            da.addAll(artlist);
            artistAdapter.setDatas(artlist);
        }else {
            //url的字符串拼接
            String downurl = String.format(Constant.ARTIST,pageSize);
            new DownUtil().setOnDownListener(this).downJSON(downurl);

        }


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
                pageSize += 1;
                //url的字符串拼接
                String downurl = String.format(Constant.ARTIST,pageSize);
                new DownUtil().setOnDownListener(new DownUtil.OnDownListener() {
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
//
                            das2 = (List<ArtEntity>) object;
                            for (int i = 0; i < das2.size(); i++) {
                                if (das2.get(i).getWorkNumber() != 0) {
                                    da2.add(das2.get(i));
                                }
                            }
                            da.clear();
                            da.addAll(da2);
                            artistAdapter.setDatas(da);
                        }
                    }
                }).downJSON(downurl);

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

    private long saveDatatoDb(ArtEntity artEntity) {
        ContentValues values = new ContentValues();
        values.put("artistId", artEntity.getArtistId());
        values.put("artistName", artEntity.getArtistName());
        values.put("artistWork", artEntity.getArtistWork());
        values.put("workNumber", artEntity.getWorkNumber());
        values.put("playNumber", artEntity.getPlayNumber());
        values.put("artistImg", artEntity.getArtistImg());
        return sqLiteDatabase.insert("artists", null, values);
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
            das = (List<ArtEntity>) object;
            for (int i = 0; i < das.size(); i++) {
                if (das.get(i).getWorkNumber() != 0) {
                    da.add(das.get(i));
                }
            }
            
            for (int i = 0; i < da.size(); i++) {
                saveDatatoDb(da.get(i));
            }
            
            artistAdapter.setDatas(da);
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(getActivity(),ArtistInfoActivity.class);
        //用intent将艺术家id及头像传送至跳转后页面
        intent.putExtra("artistid",da.get(position).getArtistId());

        if (position == 0) {
            intent.putExtra("artistimg","http://www.mow99.com/img/artist/29.jpg");
        }else {
            intent.putExtra("artistimg",da.get(position).getArtistImg());

        }

        startActivity(intent);
    }
}
