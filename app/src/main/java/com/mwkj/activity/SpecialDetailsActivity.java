package com.mwkj.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.adapter.SpecialDetailsAdapter;
import com.mwkj.entity.SpecialDetailsEntity;
import com.mwkj.entity.SpecialEntity;
import com.mwkj.widget.SpecialDetailHead;
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
 * Created by ${WU} on 2016/10/30.
 */
public class SpecialDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener, XupListView.IXListViewListener {
    @Bind(R.id.mListView)
     XupListView mListView;
    @Bind(R.id.iv_back)
    ImageView back;
    private SpecialDetailsAdapter adapter;
    private int subjectId;
    private SpecialDetailsEntity body;
    private SpecialEntity.SubjectsBean subjectsBean;
    int pageNumber = 1;
    private Handler handler;
    @Override
    protected int getContentId() {
        return R.layout.activity_special_details;
    }

    @Override
    protected void init() {
        handler = new Handler();
        Intent intent = getIntent();
        subjectId = intent.getIntExtra("subjectId",18);
        subjectsBean = (SpecialEntity.SubjectsBean) intent.getSerializableExtra("head");
        adapter = new SpecialDetailsAdapter(this);

        SpecialDetailHead head = new SpecialDetailHead(this);
        head.setDatas(subjectsBean);
        mListView.addHeaderView(head);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
    }

    @Override
    protected void loadDatas() {
        Call<SpecialDetailsEntity> call = AppStartContext.utils.getSpecialDetailsEntity(subjectId,pageNumber);
        call.enqueue(new Callback<SpecialDetailsEntity>() {
            @Override
            public void onResponse(Call<SpecialDetailsEntity> call, Response<SpecialDetailsEntity> response) {
                body = response.body();

                    adapter.setDatas(body.getSubject().getAlbums());


            }

            @Override
            public void onFailure(Call<SpecialDetailsEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("log", "onItemClick: " +i+"*****"+l);
        Intent intent = new Intent(this,ArtWorksActivity.class);
        intent.putExtra("albumid", body.getSubject().getAlbums().get((int) l).getAlbumId());
        intent.putExtra("worksname",body.getSubject().getAlbums().get((int) l).getAlbumName());
        intent.putExtra("chapternum",body.getSubject().getAlbums().get((int) l).getAlbumChapter());
        intent.putExtra("fansnum",body.getSubject().getAlbums().get((int) l).getPlayNumber());
        intent.putExtra("worksimg",body.getSubject().getAlbums().get((int) l).getAlbumCover());
        // intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
        startActivity(intent);

    }
    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }

    @Override
    public void onRefresh() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDatas();
                onLoad();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDatas();
                onLoad();
            }
        },2000);
    }
    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }
}
