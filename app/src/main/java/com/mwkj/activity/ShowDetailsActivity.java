package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.entity.ShowEntity;
import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${WU} on 2016/11/1.
 */
public class ShowDetailsActivity extends BaseActivity {
    @Bind(R.id.showName)
    TextView showName;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.playAnimation)
    ImageView playAnimation;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.buyTicket)
    TextView buyTicket;
    @Bind(R.id.showAddress)
    TextView showAddress;
    @Bind(R.id.showDetails)
    TextView showDetails;
    private ShowEntity.ShowsBean bean;


    @Override
    protected int getContentId() {
        return R.layout.activity_showdetails;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        bean = (ShowEntity.ShowsBean) intent.getSerializableExtra("show");
        showName.setText(bean.getShowTitle());
        time.setText(bean.getShowTimeStr());
        address.setText(bean.getShowCity());
        price.setText(bean.getShowTicketPrice());
        showAddress.setText(bean.getShowVenue());
        showDetails.setText(bean.getShowDesc());
        Glide.with(this).load(bean.getShowCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buyTicket)
    public void onClick() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", bean.getShowBuyUrl());
        // Log.d("log", "onClick: " +"买票去"+bean.getShowBuyUrl());
        startActivity(intent);
    }
}
