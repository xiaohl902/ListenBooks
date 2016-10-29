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
 * 特色专辑的适配器
 */
public class SpecialAdapter extends BaseAdapter {
    private Context context;
    private List<CrosstalkEntity.SpecialtyBean> datas;

    public SpecialAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }
    public void setDatas(List<CrosstalkEntity.SpecialtyBean> datas){
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
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertview!=null){
            holder = (ViewHolder) convertview.getTag();
        }else{
            holder = new ViewHolder();
            convertview = LayoutInflater.from(context).inflate(R.layout.item_inspecialty,null);
            holder.iv1 = (ImageView) convertview.findViewById(R.id.head);
            holder.albumName = (TextView) convertview.findViewById(R.id.title);
            holder.name = (TextView) convertview.findViewById(R.id.name);
            holder.chapterCount = (TextView) convertview.findViewById(R.id.count);
            holder.playNumber = (TextView) convertview.findViewById(R.id.playNumber);
            holder.wan = (TextView) convertview.findViewById(R.id.wan);
            convertview.setTag(holder);
        }
        Glide.with(context).load(datas.get(i).getAlbumCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv1);
        holder.albumName.setText(datas.get(i).getAlbumName());
        holder.name.setText(datas.get(i).getArtist().getArtistName());
        holder.chapterCount.setText(datas.get(i).getChapterCount()+"");
        int playNumber = datas.get(i).getPlayNumber();
        if(playNumber/10000!=0){
            playNumber = playNumber/10000;
            holder.wan.setVisibility(View.VISIBLE);
        }else {
            holder.wan.setVisibility(View.GONE);
        }
        holder.playNumber.setText(playNumber+"");

        return convertview;
    }

    class ViewHolder{
        ImageView iv1;
        TextView albumName,name,chapterCount,playNumber,wan;
    }
}
