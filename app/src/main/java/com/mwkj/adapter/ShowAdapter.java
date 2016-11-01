package com.mwkj.adapter;

import android.content.Context;

import com.mwkj.activity.R;
import com.mwkj.entity.ShowEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;

/**
 * Created by ${WU} on 2016/10/31.
 */
public class ShowAdapter extends AbsBaseAdapter<ShowEntity.ShowsBean> {


    public ShowAdapter(Context context) {
        super(context, R.layout.item_showfragment);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ShowEntity.ShowsBean data) {
        viewHolder.setImageView(R.id.iv,data.getShowCover())
                .setTextView(R.id.title,data.getShowTitle())
                .setTextView(R.id.time,data.getShowTime())
                .setTextView(R.id.address,data.getShowVenue())
                .setTextView(R.id.price,data.getShowTicketPrice());
    }
}
