package com.mwkj.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.adapter.BookEntityAdapter;
import com.mwkj.entity.BookEntity;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.widget.pullRefreshListview.XupListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class BookTypeActivity extends BaseActivity implements AdapterView.OnItemClickListener, XupListView.IXListViewListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.xlv)
    XupListView xListView;
    @Bind(R.id.title)
    TextView title;
    //动画
    @Bind(R.id.playAnimation)
    ImageView playAnimation;
    private AnimationDrawable animationDrawable;
    private int pageNumber = 1;
    private BookEntity bookEntity;
    private BookEntityAdapter adapter;
    private String url;
    //Handler处理下拉和上滑
    private Handler handler;
    //根据type值判断是刷新，还是加载更多
    int type = 0;
    //接收广播控制右上角小图标的动画
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected int getContentId() {
        return R.layout.activity_booktype;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String uptitle = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        title.setText(uptitle);
        adapter = new BookEntityAdapter(this);
        xListView.setAdapter(adapter);
        xListView.setOnItemClickListener(this);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setAutoLoadEnable(true);
        xListView.setXListViewListener(this);
        xListView.setRefreshTime(getTime());
        //初始化handler
        handler = new Handler();

        playAnimation.setImageResource(R.drawable.animation1);
        animationDrawable = (AnimationDrawable) playAnimation.getDrawable();

        //接收广播控制动画
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "action.qf.intent.beginPlay":
                        animationDrawable.start();
                        break;
                    case "action.qf.intent.stopPlay":
                        animationDrawable.stop();
                        break;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.qf.intent.beginPlay");//自定义的action
        intentFilter.addAction("action.qf.intent.stopPlay");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void loadDatas() {
        Call<BookEntity> call = AppStartContext.utils.downLoad(url, pageNumber);
        call.enqueue(new Callback<BookEntity>() {
            @Override
            public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                bookEntity = response.body();
                if (type == 0) {
                    adapter.setDatas(bookEntity.getAlbums());
                } else {
                    adapter.addDatas(bookEntity.getAlbums());
                }
            }

            @Override
            public void onFailure(Call<BookEntity> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.back)
    public void btnClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("log", "onItemClick: " + i + "******" + l);
        Intent intent = new Intent(this, ArtWorksActivity.class);
        intent.putExtra("albumid", bookEntity.getAlbums().get(i - 1).getAlbumId());
        intent.putExtra("worksname", bookEntity.getAlbums().get(i - 1).getAlbumName());
        intent.putExtra("chapternum", bookEntity.getAlbums().get(i - 1).getAlbumChapter());
        intent.putExtra("fansnum", bookEntity.getAlbums().get(i - 1).getPlayNumber());
        intent.putExtra("worksimg", bookEntity.getAlbums().get(i - 1).getAlbumCover());
        //                        intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
        startActivity(intent);
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
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
        }, 2500);

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
        }, 2000);
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}
