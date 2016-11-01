package com.mwkj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.activity.R;
import com.mwkj.entity.ArtEntity;
import com.mwkj.widget.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luckey on 2016/10/26.
 * 听书馆-艺术家fragment的配套adapter
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtHolder> {
    private Context context;
    private List<ArtEntity> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArtistAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setDatas(List<ArtEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ArtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_artist_rv, parent, false);
        return new ArtHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ArtHolder holder, int position) {
        holder.artist_name.setText(list.get(position).getArtistName());
        holder.works_name.setText(list.get(position).getArtistWork());
        holder.worknum.setText(list.get(position).getWorkNumber()+"");
        int playNumber = list.get(position).getPlayNumber();
        if(playNumber/10000!=0) {
            holder.playnum.setText((playNumber / 10000) + "万");
        }else {
            holder.playnum.setText(playNumber+"");
        }
//        holder.playnum.setText(list.get(position).getPlayNumber()+"");

        if (position == 0){
            Glide.with(context)
                    .load("http://www.mow99.com/img/artist/29.jpg")
                    .crossFade(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .transform(new GlideCircleTransform(context))
                    .thumbnail(0.1f)
                    .into(holder.art_iv);
        }else {
            Glide.with(context)
                    .load(list.get(position).getArtistImg())
                    .crossFade(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .transform(new GlideCircleTransform(context))
                    .thumbnail(0.1f)
                    .into(holder.art_iv);
        }


    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ArtHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView art_iv;
        TextView artist_name,works_name;
        TextView worknum,playnum;

        public ArtHolder(View itemView) {
            super(itemView);
            this.art_iv = (ImageView) itemView.findViewById(R.id.art_iv);
            this.artist_name = (TextView) itemView.findViewById(R.id.art_name_title);
            this.works_name = (TextView) itemView.findViewById(R.id.art_works_name);
            this.worknum = (TextView) itemView.findViewById(R.id.worknum);
            this.playnum = (TextView) itemView.findViewById(R.id.playnum);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onClick(v,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}
