package com.mwkj.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.mwkj.adapter.CollectionsAdapter;
import com.mwkj.db.ArtistOpenHelper;
import com.mwkj.entity.ArtistInfoEntity;
import com.qf.kenlibrary.base.BaseActivity;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/11/1.
 * 浏览记录（收藏页）
 */
public class CollectionActivity extends BaseActivity implements OnItemMoveListener {

    @Bind(R.id.collect_rv)
    SwipeMenuRecyclerView collectRv;
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

        helper = new ArtistOpenHelper(this);
        database = helper.getReadableDatabase();
//        artistname = findViewByIds(R.id.art_info_name);
//        artistname.setText();
//        collectRv = findViewByIds(R.id.collect_lv);
        collectRv.setLayoutManager(new LinearLayoutManager(this));
        collectionsAdapter = new CollectionsAdapter(this);
        collectionsAdapter.setOnItemClickListener(new CollectionsAdapter.ArtInfoOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CollectionActivity.this, ArtWorksActivity.class);
                intent.putExtra("albumid", colist.get(position).getAlbumId());
                intent.putExtra("chapternum", colist.get(position).getAlbumChapter());
                intent.putExtra("fansnum", colist.get(position).getPlayNumber());
                intent.putExtra("worksname", colist.get(position).getAlbumName());
                intent.putExtra("worksimg", colist.get(position).getAlbumCover());
                startActivity(intent);
            }
        });

        collectRv.setAdapter(collectionsAdapter);
        collectRv.setLongPressDragEnabled(true);//开启长按拖拽
        collectRv.setItemViewSwipeEnabled(false);//开启滑动删除 ,与swipeMenuCreator只能用一种，否则会冲突
        collectRv.setOnItemMoveListener(this);

        collectRv.setSwipeMenuCreator(swipeMenuCreator);
        collectRv.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();//关闭被点击菜单
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION){
                    //右滑
                    if (menuPosition == 0) {
                        collectionsAdapter.moveItem(adapterPosition,0);
                        collectRv.scrollToPosition(0);
                    }else if (menuPosition == 1){
                        collectionsAdapter.remove(adapterPosition);
                    }
                }
            }
        });

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

    //setOnItemMoveListener的两个监听方法
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        collectionsAdapter.moveItem(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        collectionsAdapter.remove(position);
    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

//            SwipeMenuItem addItem = new SwipeMenuItem(MainActivity.this)
////                    .setBackgroundDrawable(R.mipmap.ic_launcher)// 点击的背景。
//                    .setBackgroundDrawable(R.color.colorPrimary)// 点击的背景。
//                    .setImage(android.R.drawable.ic_delete) // 图标。
//                    .setWidth(200) // 宽度。
//                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT); // 高度。
//            swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

            SwipeMenuItem deleteItem = new SwipeMenuItem(CollectionActivity.this)
//                    .setBackgroundDrawable(new ColorDrawable(Color.YELLOW))
                    .setImage(android.R.drawable.ic_dialog_map) // 图标。
                    .setText("置顶") // 文字。
                    .setTextColor(Color.RED) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(100)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            SwipeMenuItem addItem2 = new SwipeMenuItem(CollectionActivity.this)
//                    .setBackgroundDrawable(R.mipmap.ic_launcher)// 点击的背景。
//                    .setBackgroundDrawable(R.color.colorPrimary)// 点击的背景。
                    .setImage(android.R.drawable.ic_dialog_info) // 图标。
                    .setText("删除") // 文字。
                    .setTextColor(Color.BLUE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(200) // 宽度。
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT); // 高度。
            swipeRightMenu.addMenuItem(addItem2); // 添加一个按钮到左侧菜单。


            // 上面的菜单哪边不要菜单就不要添加。
        }
    };

}
