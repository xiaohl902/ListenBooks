package com.mwkj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwkj.activity.R;
import com.mwkj.entity.ArtistInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/10/27.
 */
public class ArtInfoAdapter extends RecyclerView.Adapter<ArtInfoAdapter.ArtInfoHolder> {

    private Context context;
    private List<ArtistInfoEntity.AlbumsBean> infolist;

    public ArtInfoAdapter(Context context) {
        this.context = context;
        this.infolist = new ArrayList<>();
    }

    public void setDatas(List<ArtistInfoEntity.AlbumsBean> infolist) {
        this.infolist = infolist;
        this.notifyDataSetChanged();
    }

    @Override
    public ArtInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_artist_workinfo, parent, false);
        return new ArtInfoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ArtInfoHolder holder, int position) {
        holder.art_info_title.setText(infolist.get(position).getAlbumName());
        holder.art_info_name.setText(infolist.get(position).getArtist().getArtistName());
        holder.artist_capter.setText(infolist.get(position).getAlbumChapter()+"");
        holder.single_work_fans.setText(infolist.get(position).getPlayNumber()+"");
    }

    @Override
    public int getItemCount() {
        return infolist.size();
    }

    public class ArtInfoHolder extends RecyclerView.ViewHolder{
//        TextView artist_name, tv_work_num,artist_fansnum;
        TextView art_info_title,art_info_name,artist_capter,single_work_fans;
//        ExpandableTextView expandableTextView;
//        ImageView artist_headimg;
        ImageView artist_img;
        public ArtInfoHolder(View itemView) {
            super(itemView);
//            this.artist_name = (TextView) itemView.findViewById(R.id.artinfo_tv_name);
//            this.tv_work_num = (TextView) itemView.findViewById(R.id.art_list_num);
//            this.artist_fansnum = (TextView) itemView.findViewById(R.id.art_funs_num);
//            this.expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);

            this.art_info_title = (TextView) itemView.findViewById(R.id.art_info_title);
            this.art_info_name = (TextView) itemView.findViewById(R.id.art_info_name);
            this.artist_capter = (TextView) itemView.findViewById(R.id.art_info_total_num);
            this.single_work_fans = (TextView) itemView.findViewById(R.id.artist_funs);

//            this.artist_headimg = (ImageView) itemView.findViewById(R.id.info_artist_header);
            this.artist_img = (ImageView) itemView.findViewById(R.id.art_info_img);

        }
    }

}
