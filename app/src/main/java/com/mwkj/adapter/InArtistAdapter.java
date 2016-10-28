package com.mwkj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.activity.R;
import com.mwkj.entity.CrosstalkEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class InArtistAdapter extends BaseAdapter {
    private Context context;
    private List<CrosstalkEntity.ArtistBean> datas;

    public InArtistAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }
    public void setDatas(List<CrosstalkEntity.ArtistBean> datas){
        this.datas =datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view !=null){
            holder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_inartist,null);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.big_pic);
            holder.tv = (TextView) view.findViewById(R.id.artist_name);

         view.setTag(holder);
        }

        Glide.with(context).load(datas.get(i).getArtistImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv);
        holder.tv.setText(datas.get(i).getArtistName());

        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
