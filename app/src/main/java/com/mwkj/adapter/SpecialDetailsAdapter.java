package com.mwkj.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mwkj.activity.R;
import com.mwkj.entity.SpecialDetailsEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;

/**
 * Created by ${WU} on 2016/10/30.
 * 专题详情页适配器
 */
public class SpecialDetailsAdapter extends AbsBaseAdapter<SpecialDetailsEntity.SubjectBean.AlbumsBean> {

    public SpecialDetailsAdapter(Context context) {
        super(context, R.layout.item_specialdetails);
    }

    @Override
    public void bindView(ViewHolder viewHolder, SpecialDetailsEntity.SubjectBean.AlbumsBean data) {
        TextView wan = (TextView) viewHolder.getView(R.id.wan);
        int playNumber = data.getPlayNumber();
        if(playNumber/10000!=0){
            playNumber = playNumber/10000;
            wan.setVisibility(View.VISIBLE);
        }else {
            wan.setVisibility(View.GONE);
        }

        viewHolder.setTextView(R.id.title,data.getAlbumName())
                .setTextView(R.id.name,data.getArtist().getArtistName())
                .setTextView(R.id.count,data.getChapterCount()+"")
                .setTextView(R.id.playNumber,playNumber+"")
                .setImageView(R.id.head,data.getAlbumCover());
    }
}
