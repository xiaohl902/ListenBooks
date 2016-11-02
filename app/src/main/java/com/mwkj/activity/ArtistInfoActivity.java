package com.mwkj.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.mwkj.db.ArtistOpenHelper;
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


    private int pagesize = 8; //默认加载第一页数据
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

    private ArtistOpenHelper helper;
    private SQLiteDatabase database;
    private String artistName;

    @Override
    protected int getContentId() {
        return R.layout.activity_artistinfo;
    }

    @Override
    protected void init() {

        helper = new ArtistOpenHelper(this);
        database = helper.getReadableDatabase();
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

            String downurl = String.format(Constant.ARTIST_INFO,artistid,pagesize);
//        Log.d("print", "loadDatas: downurl= "+downurl);
            new DownUtil().setOnDownListener(this).downJSON(downurl);


        artinfo_swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_artinfo);
        artinfo_swipeRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        artinfo_swipeRefresh.setColorSchemeColors(Color.parseColor("#50a913"));
        artinfo_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("print", "------>开始刷新，加载数据");

                //页数++，重新加载

                pagesize += 8; //第一页展示的长度+8
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


    /**
     * long 保存至浏览记录数据库
     *
     */

    private void saveDatatoDb(ArtistInfoEntity.AlbumsBean artentity) {
        Cursor cursor = database.query("works",new String[]{"_id","albumId"},null,null,null,null,null);
        boolean flag = false;
        if (cursor.getCount() == 0) {
            flag = true;
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex("albumId")) == artentity.getAlbumId()) {
//                    Toast.makeText(ArtistInfoActivity.this, "您已经添加过该浏览记录",
//                            Toast.LENGTH_LONG).show();
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
        }
        if (flag) {
            ContentValues values = new ContentValues();
            values.put("albumId", artentity.getAlbumId());
            values.put("albumName", artentity.getAlbumName());
            values.put("artistName", artistName);
            values.put("albumChapter", artentity.getAlbumChapter());
            values.put("playNumber", artentity.getPlayNumber());
            values.put("albumCover", artentity.getAlbumCover());
            database.insert("works", null, values);
//            Toast.makeText(ArtistInfoActivity.this, "成功添加至浏览记录",
//                    Toast.LENGTH_SHORT).show();
        }
    }


   /* //保存至list集合大数据库
    private void saveDatatoDb2(ArtistInfoEntity.AlbumsBean artentity2) {
        Cursor cursor = database.query("artlists",new String[]{"_id","albumId"},null,null,null,null,null);
        boolean flag = false;
        if (cursor.getCount() == 0) {
            flag = true;
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex("albumId")) == artentity2.getAlbumId()) {
//                    Toast.makeText(ArtistInfoActivity.this, "您已经添加过该浏览记录",
//                            Toast.LENGTH_LONG).show();
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
        }
        if (flag) {
            ContentValues values = new ContentValues();
            values.put("albumId", artentity2.getAlbumId());
            values.put("albumName", artentity2.getAlbumName());
            values.put("artistName", artistName);
            values.put("albumChapter", artentity2.getAlbumChapter());
            values.put("playNumber", artentity2.getPlayNumber());
            values.put("albumCover", artentity2.getAlbumCover());
            database.insert("artlists", null, values);
//            Toast.makeText(ArtistInfoActivity.this, "成功添加至浏览记录",
//                    Toast.LENGTH_SHORT).show();
        }
    }
*/



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
            artistName = entity.getArtist().getArtistName();
            artinfoTvName.setText(artistName);
            if (entity.getAlbums() != null && !entity.getAlbums().equals("") && !entity.getAlbums().equals("[]")) {
                albums = entity.getAlbums();
               /* for (int i = 0; i < entity.getAlbums().size(); i++) {
                    saveDatatoDb2(entity.getAlbums().get(i));
                }*/
                infoAdapter.setDatas(albums);
                infoAdapter.setOnItemClickListener(this);
            }


            expandTextView.setText(entity.getArtist().getArtistResume(),mCollapsedStatus,0);
            artListNum.setText(entity.getArtist().getWorkNumber()+"");
            int playNumber = entity.getArtist().getPlayNumber();
            if(playNumber/10000 != 0) {
                artFunsNum.setText((playNumber / 10000) + "万");
            }else {
                artFunsNum.setText(playNumber+"");
            }

        }
    }

    /**
     * @param view
     * @param position
     */

    //item点击事件的接口回调
    @Override
    public void onItemClick(View view, int position) {
        saveDatatoDb(albums.get(position));
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
