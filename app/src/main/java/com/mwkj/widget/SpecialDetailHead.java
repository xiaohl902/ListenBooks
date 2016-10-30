package com.mwkj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.activity.R;
import com.mwkj.entity.SpecialEntity;

/**
 * Created by ${WU} on 2016/10/30.
 * 专题-详情页的头部
 */
public class SpecialDetailHead extends LinearLayout {
    private SpecialEntity.SubjectsBean headBean;
    private ImageView bg_pic;
    private TextView describe;
    private TextView viewNum;
    private TextView details;

    public SpecialDetailHead(Context context) {
        this(context,null);
    }

    public SpecialDetailHead(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpecialDetailHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.head_specialdetails,this,true);
        bg_pic = (ImageView) findViewById(R.id.bg);
        describe = (TextView) findViewById(R.id.content);
        viewNum = (TextView) findViewById(R.id.viewNum);
        details = (TextView) findViewById(R.id.details);
    }

    public void setDatas(SpecialEntity.SubjectsBean headBean){
        this.headBean = headBean;
        Glide.with(getContext()).load(headBean.getSubjectImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bg_pic);
        describe.setText(headBean.getSubjectTitle());
        viewNum.setText(headBean.getSubjectViewNumber()+"");
        details.setText(headBean.getSubjectDesc());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
