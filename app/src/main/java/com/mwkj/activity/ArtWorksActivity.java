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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckey on 2016/10/28.
 * 听书馆-艺术家-艺术家详情-作品集合的activity
 */
public class ArtWorksActivity extends BaseActivity implements DownUtil.OnDownListener {
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
    @Bind(R.id.tv_work_download)
    TextView tvWorkDownload;
    @Bind(R.id.tv_work_collection)
    TextView tvWorkCollection;
    @Bind(R.id.ll_works1)
    LinearLayout llWorks1;
    @Bind(R.id.check_all)
    TextView checkAll;
    @Bind(R.id.tv_ll2_download2)
    TextView tvLl2Download2;
    @Bind(R.id.ll_works2)
    LinearLayout llWorks2;
    @Bind(R.id.uncheck_all)
    TextView uncheckAll;
    @Bind(R.id.tv_ll2_download3)
    TextView tvLl2Download3;
    @Bind(R.id.ll_works3)
    LinearLayout llWorks3;
//    @Bind(R.id.srl_wrokinfo)
//    SwipeRefreshLayout srlWrokinfo;

    private int albumid = -1;//作品id,默认-1
    private int chapternum, fansnum;//作品章节数、播放次数
    private String worksname, worksimg;//作者名称、图片url

    private TextView artListNum;

    //适配器
    private WorksInfoAdapter worksInfoAdapter;

    CheckBox work_checkbox;

    private int pageSize = 20; //默认加载第一页数据

    //配合可折叠textview使用
    SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();

    //下拉刷新
    private SwipeRefreshLayout srlWrokinfo;
    private ArtWorksEntity workentity; //实体类
    private List<ArtWorksEntity.ChaptersBean> chapters;
    private List<ArtWorksEntity.ChaptersBean> chapters1;

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
//        Log.d("print", "init: albumid " + albumid + "chapternum " + chapternum + "fansnum " + fansnum + "worksname" + worksname + "worksimg " + worksimg);

        work_checkbox = findViewByIds(R.id.work_checkbox);

        srlWrokinfo = findViewByIds(R.id.srl_wrokinfo);
        artListNum = findViewByIds(R.id.work_list_num);

        workinfoTvName.setText(worksname);//设置作品名
        artListNum.setText(chapternum + "");//设置章节数

        //设置播放数
        if (fansnum != -1) {
            if (fansnum / 10000 != 0) {
                artFunsNum.setText((fansnum / 10000) + "万");
            } else {
                artFunsNum.setText(fansnum + "");
            }
        } else {
            artFunsNum.setText(fansnum + "0");
        }

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
                4, getResources().getColor(R.color.color_additem_decoration)));

        srlWrokinfo.setProgressBackgroundColorSchemeColor(Color.GREEN);
        srlWrokinfo.setColorSchemeColors(Color.BLUE);
        srlWrokinfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("print", "------>开始刷新，加载数据");

                //页数++，重新加载
                pageSize += 20;
                loadDatas();

                new Thread() {
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
        String downurl = String.format(Constant.ARTIST_WORK_INFO, albumid, pageSize);
//        Log.d("print", "loadDatas: downurl= " + downurl);
        new DownUtil().setOnDownListener(this).downJSON(downurl);

    }

    @Override
    public Object paresJson(String json) {
        if (json != null) {
            return new Gson().fromJson(json, ArtWorksEntity.class);
        }
        return null;
    }

    @Override
    public void downSucc(Object object) {
        if (object != null) {
            workentity = (ArtWorksEntity) object;
            final String artistName = workentity.getArtist().getArtistName();
            chapters1 = workentity.getChapters();

            this.chapters = workentity.getChapters();
            worksInfoAdapter.setDatas(this.chapters);
            worksInfoAdapter.setRecyclerViewOnItemClickListener(new WorksInfoAdapter.RecyclerViewOnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    //点击事件
                    Intent intent = new Intent(ArtWorksActivity.this, PlayActivity.class);
                    intent.putExtra("playtitle", ArtWorksActivity.this.chapters.get(position).getChapterName());
                    intent.putExtra("position",chapters.get(position).getChapterIdx()-1);
                    Bundle  bundle = new Bundle();
                    bundle.putSerializable("chapters", (Serializable) chapters1);
                    intent.putExtra("bundle",bundle);
                    intent.putExtra("playartist",artistName);
                    intent.putExtra("playurl",chapters.get(position).getChapterLocation());
//                    String chapterurl = chapters.get(position).getChapterLocation();

                    String chapterurl = chapters.get(position).getChapterLocation();
//
//                    String preurl = chapterurl.substring(0,chapterurl.lastIndexOf("/")+1);
//                    String laststring = chapterurl.substring(chapterurl.lastIndexOf("/")+1);
////                    String urlnum = laststring.split(".")[0];
//                    String pointmp3 = laststring.substring(laststring.indexOf("."));
//                    String urlnum = laststring.substring(0,laststring.indexOf("."));
//
////                    Log.d("print", "onItemClickListener: preurl "+preurl + " pointmp3 "+pointmp3 + " laststring "+laststring +" urlnum "+urlnum);
//                    intent.putExtra("urlpart1",preurl);
//                    intent.putExtra("urlpart2",urlnum);
//                    intent.putExtra("urlpart3",pointmp3);
                    intent.putExtra("chapterurl",chapterurl);
                    startActivity(intent);

                }

                @Override
                public boolean onItemLongClickListener(View view, int position) {
                    //设置选中的项
                    worksInfoAdapter.setShowBox();
                    worksInfoAdapter.setSelectItem(position);
                    worksInfoAdapter.notifyDataSetChanged();
                    return true;
                }

            });


            expandableText.setText(workentity.getAlbum().getAlbumDesc(), mCollapsedStatus, 0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.workinfo_back, R.id.workinfo_music, R.id.tv_work_download, R.id.tv_work_collection, R.id.check_all, R.id.tv_ll2_download2,R.id.uncheck_all, R.id.tv_ll2_download3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.workinfo_back:
                finish();
                break;
            case R.id.workinfo_music:
                finish();
                break;
            case R.id.tv_work_download:
                llWorks1.setVisibility(View.GONE);
                llWorks2.setVisibility(View.VISIBLE);
                worksInfoAdapter.setShowBox();
                worksInfoAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_work_collection:
                break;
            case R.id.check_all:
                //全选功能
                Map<Integer, Boolean> mapall = worksInfoAdapter.getMap();
                for (int i = 0; i < chapters.size(); i++) {
                    mapall.put(i, true);
                    worksInfoAdapter.notifyDataSetChanged();
                }
                llWorks2.setVisibility(View.GONE);
                llWorks3.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_ll2_download2:
                //获取选中内容
                Map<Integer, Boolean> map = worksInfoAdapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        Log.d("TAG", "你选了第：" + i + "项");
                    }
                }

                break;
            //取消全选
            case R.id.uncheck_all:
                Map<Integer, Boolean> m = worksInfoAdapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    worksInfoAdapter.notifyDataSetChanged();
                }
                llWorks2.setVisibility(View.VISIBLE);
                llWorks3.setVisibility(View.GONE);
                break;
            case R.id.tv_ll2_download3:
                break;
        }
    }


}
