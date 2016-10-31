package com.mwkj.adapter;

import android.content.Context;

import com.mwkj.activity.R;
import com.mwkj.entity.ShowEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;

/**
 * Created by ${WU} on 2016/10/31.
 */
public class ShowAdapter extends AbsBaseAdapter<ShowEntity> {


    public ShowAdapter(Context context) {
        super(context, R.layout.item_showfragment);
    }

    @Override
    public void bindView(ViewHolder viewHolder, ShowEntity data) {

    }
}
