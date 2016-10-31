package com.mwkj.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.mwkj.activity.R;
import com.mwkj.entity.CommunityTopicEntity;
import com.mwkj.util.DateFormatUtil;
import com.mwkj.widget.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/10/31.
 */
public class ComTopicAdapter extends BaseAdapter {
    private Context context;
    private List<CommunityTopicEntity.TopicsBean> list;
    public SparseBooleanArray mCollapsedStatus;

    public ComTopicAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.mCollapsedStatus = new SparseBooleanArray();
    }

    public void setDatas(List<CommunityTopicEntity.TopicsBean> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<CommunityTopicEntity.TopicsBean> list){
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TopicViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_com_something, null);
            holder = new TopicViewHolder();
            holder.user_iv = (ImageView) convertView.findViewById(R.id.community_info_img);
            holder.user_name = (TextView) convertView.findViewById(R.id.community_info_title);
            holder.user_time = (TextView) convertView.findViewById(R.id.community_info_name);
            holder.countnum1 = (TextView) convertView.findViewById(R.id.likescount);
            holder.countnum2 = (TextView) convertView.findViewById(R.id.commentcount);
            holder.countnum3 = (TextView) convertView.findViewById(R.id.favCount);

            holder.expandableTextView = (ExpandableTextView) convertView.findViewById(R.id.expand_community_tv);
            convertView.setTag(holder);
        }else {
            holder = (TopicViewHolder) convertView.getTag();
        }

        holder.user_name.setText(list.get(position).getUser().getMowuserNikeName());
        holder.countnum1.setText(list.get(position).getTotalCommentCount()+"");
        holder.countnum2.setText(list.get(position).getTotalViewCount()+"");
        holder.countnum3.setText(list.get(position).getTotalDigCount()+"");
        holder.expandableTextView.setText(list.get(position).getTopicContent(), mCollapsedStatus, position);
        long topicCreateTime = list.get(position).getTopicCreateTime();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(topicCreateTime));
        holder.user_time.setText(DateFormatUtil.getTimeRange(format));
        Glide.with(context)
                .load(list.get(position).getUser().getMowuserPersonImg())
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher)
                .transform(new GlideCircleTransform(context))
                .thumbnail(0.1f)
                .into(holder.user_iv);
        return convertView;
    }


    class TopicViewHolder{
        ExpandableTextView expandableTextView;//可扩展收缩的textview
        TextView user_name,user_time;
        TextView countnum1,countnum2,countnum3;
        ImageView user_iv;
    }
}
