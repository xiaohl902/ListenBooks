package com.mwkj.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.mwkj.adapter.ArtInfoAdapter;
import com.mwkj.entity.ArtistInfoEntity;
import com.mwkj.util.Constant;
import com.mwkj.widget.GlideCircleTransform;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.util.DownUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckey on 2016/10/26.
 * 听书馆-艺术家详情的 activity
 */
public class ArtistInfoActivity extends BaseActivity implements DownUtil.OnDownListener, ArtInfoAdapter.ArtInfoOnItemClickListener {

    @Bind(R.id.artinfo_tv_name)
    TextView artinfoTvName;
    @Bind(R.id.info_artist_header)
    ImageView infoArtistHeader;
    @Bind(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @Bind(R.id.art_list_num)
    TextView artListNum;
    @Bind(R.id.art_funs_num)
    TextView artFunsNum;
    @Bind(R.id.artist_info_rv)
    RecyclerView info_rv;


    private int page = 1; //默认加载第一页数据
    private int artistid = -1; //艺术家id，默认为-1

    //适配器
    private ArtInfoAdapter infoAdapter;

    //数据源
    private List<ArtistInfoEntity.AlbumsBean> albums;

    //配合可折叠textview使用
    SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();

    //下拉刷新
    private SwipeRefreshLayout artinfo_swipeRefresh;
    private ArtistInfoEntity entity;

    @Override
    protected int getContentId() {
        return R.layout.activity_artistinfo;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        artistid = intent.getIntExtra("artistid", -1);
        String artistimg = intent.getStringExtra("artistimg");

        Glide.with(this)
                .load(artistimg)
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .transform(new GlideCircleTransform(this))
                .thumbnail(0.1f)
                .into(infoArtistHeader);

        info_rv.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new ArtInfoAdapter(this);
        info_rv.setAdapter(infoAdapter);


    }

    @Override
    protected void loadDatas() {
        String downurl = String.format(Constant.ARTIST_INFO,artistid,page);
//        Log.d("print", "loadDatas: downurl= "+downurl);
        new DownUtil().setOnDownListener(this).downJSON(downurl);

        artinfo_swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_artinfo);
        artinfo_swipeRefresh.setProgressBackgroundColorSchemeColor(Color.GREEN);
        artinfo_swipeRefresh.setColorSchemeColors(Color.BLUE);
        artinfo_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("print", "------>开始刷新，加载数据");

                //页数++，重新加载
                page++;
                loadDatas();

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //数据加载完成
                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //关闭刷新
                                artinfo_swipeRefresh.setRefreshing(false);
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
            return new Gson().fromJson(json,ArtistInfoEntity.class);
        }

        return null;

    }

    @Override
    public void downSucc(Object object) {
        if (object != null){
            entity = (ArtistInfoEntity) object;
            if (entity.getAlbum() != null && !entity.getAlbum().equals("") && !entity.getAlbum().equals("[]")) {
                albums = entity.getAlbums();
                infoAdapter.setDatas(albums);
                infoAdapter.setOnItemClickListener(this);
            }


            artinfoTvName.setText(entity.getArtist().getArtistName());
            expandTextView.setText(entity.getArtist().getArtistResume(),mCollapsedStatus,0);
            artListNum.setText(entity.getArtist().getWorkNumber()+"");
            artFunsNum.setText(entity.getArtist().getPlayNumber()+"");


        }
        
        
    }

    /**
     * @param view
     * @param position
     */

    //item点击事件的接口回调
    @Override
    public void onItemClick(View view, int position) {
        Intent in = new Intent(this,ArtWorksActivity.class);
        in.putExtra("albumid",entity.getAlbums().get(position).getAlbumId());
        in.putExtra("worksname",entity.getAlbums().get(position).getAlbumName());
        in.putExtra("chapternum",entity.getAlbums().get(position).getAlbumChapter());
        in.putExtra("fansnum",entity.getAlbums().get(position).getPlayNumber());
        in.putExtra("worksimg",entity.getAlbums().get(position).getAlbumCover());
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick({R.id.artinfo_back, R.id.artinfo_music})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.artinfo_back:
                finish();
                break;
            case R.id.artinfo_music:
                finish();
                break;
        }
    }



}
