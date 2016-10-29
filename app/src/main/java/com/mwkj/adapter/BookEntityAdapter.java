package com.mwkj.adapter;

import android.content.Context;
import android.view.View;

import com.mwkj.activity.R;
import com.mwkj.entity.BookEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;

/**
 * Created by ${WU} on 2016/10/29.
 */
public class BookEntityAdapter extends AbsBaseAdapter<BookEntity.AlbumsBean> {


    public BookEntityAdapter(Context context) {
        super(context, R.layout.item_booktype);
    }


    @Override
    public void bindView(ViewHolder viewHolder, BookEntity.AlbumsBean data) {
        View view = viewHolder.getView(R.id.wan);
        int playNumber = data.getPlayNumber();
        if(playNumber/10000!=0){
            playNumber = playNumber/10000;
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }

        viewHolder.setImageView(R.id.head,data.getAlbumCover())
                   .setTextView(R.id.title,data.getAlbumName())
                    .setTextView(R.id.name,data.getArtist().getArtistName())
                    .setTextView(R.id.count,data.getAlbumChapter()+"")
                    .setTextView(R.id.playNumber,playNumber+"")
        ;
    }
}
