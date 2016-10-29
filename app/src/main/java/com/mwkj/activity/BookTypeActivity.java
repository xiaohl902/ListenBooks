package com.mwkj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.adapter.BookEntityAdapter;
import com.mwkj.entity.BookEntity;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.widget.pullRefreshListview.XListView;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class BookTypeActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.xlv)
    XListView xListView;
    @Bind(R.id.title)
    TextView title;
    private int pageNumber = 1;
    private BookEntity bookEntity;
    private BookEntityAdapter adapter;
    private String url;

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
    }

    @Override
    protected void loadDatas() {
        Call<BookEntity> call = AppStartContext.utils.downLoad(url,pageNumber);
        call.enqueue(new Callback<BookEntity>() {
            @Override
            public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                bookEntity = response.body();
                adapter.setDatas(bookEntity.getAlbums());
            }

            @Override
            public void onFailure(Call<BookEntity> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.back)
    public void btnClick(View v){
        finish();
    }

}
