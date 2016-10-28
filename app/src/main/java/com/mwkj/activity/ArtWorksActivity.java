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
import com.mwkj.adapter.WorksInfoAdapter;
import com.mwkj.entity.ArtWorksEntity;
import com.mwkj.util.Constant;
import com.mwkj.widget.GlideCircleTransform;
import com.mwkj.widget.RecycleViewDivider;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.util.DownUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckey on 2016/10/28.
 * 听书馆-艺术家-艺术家详情-作品集合的activity
 */
public class ArtWorksActivity extends BaseActivity implements WorksInfoAdapter.OnWorksItemClickListener, DownUtil.OnDownListener {
    @Bind(R.id.workinfo_tv_name)
    TextView workinfoTvName;
    @Bind(R.id.info_artist_header)
    ImageView infoArtistHeader;
    @Bind(R.id.expand_tv_works)
    ExpandableTextView expandableText;
//    @Bind(R.id.work_list_num)
//    TextView artListNum;
    @Bind(R.id.work_funs_num)
    TextView artFunsNum;
    @Bind(R.id.wrok_info_rv)
    RecyclerView wrokInfoRv;
//    @Bind(R.id.srl_wrokinfo)
//    SwipeRefreshLayout srlWrokinfo;

    private int albumid = -1;//作品id,默认-1
    private int chapternum, fansnum;//作品章节数、播放次数
    private String worksname, worksimg;//作品名称、图片url

    private TextView artListNum;

    private WorksInfoAdapter worksInfoAdapter;

    private int page = 1; //默认加载第一页数据

    //配合可折叠textview使用
    SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();

    //下拉刷新
    private SwipeRefreshLayout srlWrokinfo;
    private ArtWorksEntity workentity; //实体类

    @Override
    protected int getContentId() {
        return R.layout.activity_worksinfo;
    }

    @Override
    protected void init() {
        Intent in = getIntent();
        albumid = in.getIntExtra("albumid", -1);
        chapternum = in.getIntExtra("chapternum", -1);
        fansnum = in.getIntExtra("fansnum", -1);
        worksname = in.getStringExtra("worksname");
        worksimg = in.getStringExtra("worksimg");
        Log.d("print", "init: albumid "+albumid + "chapternum "+chapternum+"fansnum "+fansnum+ "worksname"+worksname+"worksimg "+worksimg);


        srlWrokinfo = findViewByIds(R.id.srl_wrokinfo);
        artListNum = findViewByIds(R.id.work_list_num);

        workinfoTvName.setText(worksname);//设置作品名
        artListNum.setText(chapternum+"");//设置章节数
        artFunsNum.setText(fansnum+"");//设置播放数

        //下载作品相应图片
        Glide.with(this)
                .load(worksimg)
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .transform(new GlideCircleTransform(this))
                .thumbnail(0.1f)
                .into(infoArtistHeader);

        wrokInfoRv.setLayoutManager(new LinearLayoutManager(this));
        worksInfoAdapter = new WorksInfoAdapter(this);
        wrokInfoRv.setAdapter(worksInfoAdapter);
//        wrokInfoRv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));

//        wrokInfoRv.addItemDecoration(new RecycleViewDivider(this,
//                LinearLayoutManager.HORIZONTAL,
//                R.drawable.works_item_divider));

        wrokInfoRv.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL,
                4, getResources().getColor(R.color.colorPrimary)));

        srlWrokinfo.setProgressBackgroundColorSchemeColor(Color.GREEN);
        srlWrokinfo.setColorSchemeColors(Color.BLUE);
        srlWrokinfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                                srlWrokinfo.setRefreshing(false);
                            }
                        });
                    }
                }.start();
            }
        });


    }

    @Override
    protected void loadDatas() {
        String downurl = String.format(Constant.ARTIST_WORK_INFO,albumid,page);
        Log.d("print", "loadDatas: downurl= "+downurl);
        new DownUtil().setOnDownListener(this).downJSON(downurl);

    }

    @Override
    public Object paresJson(String json) {
        if (json != null){
            return new Gson().fromJson(json,ArtWorksEntity.class);
        }
        return null;
    }

    @Override
    public void downSucc(Object object) {
        if (object != null){
            workentity = (ArtWorksEntity) object;
            List<ArtWorksEntity.ChaptersBean> chapters = workentity.getChapters();
            worksInfoAdapter.setDatas(chapters);
            worksInfoAdapter.setOnWorksItemClickListener(this);

            expandableText.setText(workentity.getAlbum().getAlbumDesc(),mCollapsedStatus,0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.workinfo_back, R.id.workinfo_music})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workinfo_back:
                finish();
                break;
            case R.id.workinfo_music:
                finish();
                break;
        }
    }

    @Override
    public void worksitemclick(View view, int position) {
        startActivity(new Intent(this,PlayActivity.class));
    }


}
