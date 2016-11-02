package com.mwkj.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mwkj.adapter.CollectionsAdapter;
import com.mwkj.db.ArtistOpenHelper;
import com.mwkj.entity.ArtistInfoEntity;
import com.qf.kenlibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/11/1.
 * 浏览记录（收藏页）
 */
public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.collect_lv)
    ListView collectLv;
//    private ListView listView;
    private CollectionsAdapter collectionsAdapter;
    private ArtistOpenHelper helper;
    private SQLiteDatabase database;
    private List<ArtistInfoEntity.AlbumsBean> colist;

//    private TextView artistname;

    @Override
    protected int getContentId() {
        return R.layout.collections_lv;
    }

    @Override
    protected void init() {
//        artistname = findViewByIds(R.id.art_info_name);
//        artistname.setText();
        collectLv = findViewByIds(R.id.collect_lv);
        collectLv.setOnItemClickListener(this);
        helper = new ArtistOpenHelper(this);
        database = helper.getReadableDatabase();
        collectionsAdapter = new CollectionsAdapter(this);
        collectLv.setAdapter(collectionsAdapter);
    }

    @Override
    protected void loadDatas() {
        colist = new ArrayList<>();
        Cursor cursor = database.query("works", new String[]{"albumId", "albumName", "artistName", "albumChapter", "playNumber", "albumCover"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int albumId = cursor.getInt(cursor.getColumnIndex("albumId"));
            String albumName = cursor.getString(cursor.getColumnIndex("albumName"));
            String artistName = cursor.getString(cursor.getColumnIndex("artistName"));
            int albumChapter = cursor.getInt(cursor.getColumnIndex("albumChapter"));
            int playNumber = cursor.getInt(cursor.getColumnIndex("playNumber"));
            String albumCover = cursor.getString(cursor.getColumnIndex("albumCover"));
            ArtistInfoEntity.AlbumsBean coentity = new ArtistInfoEntity.AlbumsBean();
            coentity.setAlbumId(albumId);
            coentity.setAlbumName(albumName);
            coentity.setArtistName(artistName);
            coentity.setAlbumChapter(albumChapter);
            coentity.setPlayNumber(playNumber);
            coentity.setAlbumCover(albumCover);
            colist.add(coentity);
        }
        collectionsAdapter.setDatas(colist);
        cursor.close();
    }

    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ArtWorksActivity.class);
        intent.putExtra("albumid", colist.get(position).getAlbumId());
        intent.putExtra("chapternum", colist.get(position).getAlbumChapter());
        intent.putExtra("fansnum", colist.get(position).getPlayNumber());
        intent.putExtra("worksname", colist.get(position).getAlbumName());
        intent.putExtra("worksimg", colist.get(position).getAlbumCover());
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.collect_back, R.id.collect_music})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_back:
                finish();
                break;
            case R.id.collect_music:
                break;
        }
    }
}
