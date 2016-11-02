package com.mwkj.adapter;

import android.content.Context;

import com.mwkj.activity.R;
import com.mwkj.entity.ArtistInfoEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;

/**
 * Created by lenovo on 2016/11/1.
 *
 */
public class CollectionsAdapter extends AbsBaseAdapter<ArtistInfoEntity.AlbumsBean> {

    public CollectionsAdapter(Context context) {
        super(context, R.layout.item_artist_workinfo);
    }


    @Override
    public void bindView(ViewHolder viewHolder, ArtistInfoEntity.AlbumsBean data) {
        viewHolder
                .setTextView(R.id.art_info_title,data.getAlbumName())
                .setTextView(R.id.art_info_name,data.getArtistName())
                .setTextView(R.id.art_info_total_num,data.getAlbumChapter()+"")
                .setTextView(R.id.artist_funs_num,data.getPlayNumber()+"")
                .setImageView(R.id.art_info_img,data.getAlbumCover());
    }
}
