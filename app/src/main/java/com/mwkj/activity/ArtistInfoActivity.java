package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.mwkj.adapter.ArtInfoAdapter;
import com.mwkj.entity.ArtistInfoEntity;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.util.DownUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/10/26.
 */
public class ArtistInfoActivity extends BaseActivity implements DownUtil.OnDownListener {

    @Bind(R.id.artinfo_back)
    ImageView artinfoBack;
    @Bind(R.id.artinfo_tv_name)
    TextView artinfoTvName;
    @Bind(R.id.artinfo_music)
    ImageView artinfoMusic;
    @Bind(R.id.info_artist_header)
    ImageView infoArtistHeader;
    @Bind(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @Bind(R.id.art_list_num)
    TextView artListNum;
    @Bind(R.id.art_funs_num)
    TextView artFunsNum;
    @Bind(R.id.artist_info_rv)
    RecyclerView artistInfoRv;
    @Bind(R.id.art_funs_unit)
    TextView artFunsUnit;

    private List<ArtistInfoEntity> da;
    int page = 1;
    int artistid;
    private RecyclerView info_rv;
    private ArtInfoAdapter infoAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_artistinfo;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        artistid = intent.getIntExtra("artistid", -1);
        info_rv = findViewByIds(R.id.artist_info_rv);
        info_rv.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new ArtInfoAdapter(this);
        info_rv.setAdapter(infoAdapter);


//        expandTextView.setText();
    }

    @Override
    protected void loadDatas() {
        String url = "http://www.mow99.com/GetAlbumsByArtist?albumType=0&artistId=%d&pageSize=8&pageNumber=%d&session=1416551477485726424&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=352284040546495";
        String downurl = String.format(url,artistid,page);
        new DownUtil().setOnDownListener(this).downJSON(downurl);
    }




    @Override
    public Object paresJson(String json) {
        return new Gson().fromJson(json,ArtistInfoEntity.class);
    }

    @Override
    public void downSucc(Object object) {
        if (object != null){
            ArtistInfoEntity entity = (ArtistInfoEntity) object;
            List<ArtistInfoEntity.AlbumsBean> albums = entity.getAlbums();
            infoAdapter.setDatas(albums);
        }
        
        
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
