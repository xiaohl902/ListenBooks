package com.mwkj.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BigImgActivity extends BaseActivity {
    @Bind(R.id.ac_bigimg)
    ImageView acBigimg;

    @Override
    protected int getContentId() {
        return R.layout.activity_bigimg;
    }

    @Override
    protected void init() {
        Bitmap bitmap  =  getIntent().getParcelableExtra("img");
        acBigimg.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
