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
public class HotAdapter extends BaseAdapter {
    private Context context;
    private List<CrosstalkEntity.HotBean> datas;

    public HotAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<>();
    }
    public void setDatas(List<CrosstalkEntity.HotBean> datas){
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
        }else {
            holder = new ViewHolder();
            convertview = LayoutInflater.from(context).inflate(R.layout.item_inhot,null);
            holder.iv = (ImageView) convertview.findViewById(R.id.big_pic);
            holder.tv1 = (TextView) convertview.findViewById(R.id.playNumber);
            holder.tv2 = (TextView) convertview.findViewById(R.id.wan);
            holder.tv3 = (TextView) convertview.findViewById(R.id.albumName);
            holder.tv4 = (TextView) convertview.findViewById(R.id.artistName);

            convertview.setTag(holder);
        }
        Glide.with(context).load(datas.get(i).getAlbumCover())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv);
        int playNumber = datas.get(i).getPlayNumber();
        if(playNumber/10000!=0){
            holder.tv1.setText((playNumber/10000)+"");
            holder.tv2.setVisibility(View.VISIBLE);
        }else {
            holder.tv1.setText(playNumber+"");
            holder.tv2.setVisibility(View.GONE);
        }
        holder.tv3.setText(datas.get(i).getAlbumName());
        holder.tv4.setText(datas.get(i).getArtist().getArtistName());


        return convertview;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv1,tv2,tv3,tv4;
    }
}
