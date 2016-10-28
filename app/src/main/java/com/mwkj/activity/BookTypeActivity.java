package com.mwkj.activity;

import android.content.Intent;

import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.widget.pullRefreshListview.XListView;

import butterknife.Bind;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class BookTypeActivity extends BaseActivity {
    @Bind(R.id.xlv)
    XListView xListView;
    @Override
    protected int getContentId() {
        return R.layout.activity_booktype;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");

    }
}
