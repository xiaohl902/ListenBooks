package com.mwkj.adapter;

import android.content.Context;

import com.mwkj.activity.R;
import com.mwkj.entity.SpecialEntity;
import com.qf.kenlibrary.base.AbsBaseAdapter;


/**
 * Created by ${WU} on 2016/10/29.
 */
public class ZspecialAdapter extends AbsBaseAdapter<SpecialEntity.SubjectsBean> {


    public ZspecialAdapter(Context context) {
        super(context, R.layout.item_fragment_special);
    }

    @Override
    public void bindView(ViewHolder viewHolder, SpecialEntity.SubjectsBean data) {
        viewHolder.setTextView(R.id.num,data.getSubjectId()+"")
                .setTextView(R.id.content,data.getSubjectTitle())
                .setTextView(R.id.viewNum,data.getSubjectViewNumber()+"")
                .setImageView(R.id.bg,data.getSubjectImg());
    }
}
