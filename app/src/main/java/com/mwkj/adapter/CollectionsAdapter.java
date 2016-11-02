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
import com.mwkj.entity.ArtistInfoEntity;
import com.mwkj.widget.GlideCircleTransform;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/1.
 *
 */
public class CollectionsAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {


    //定义RecyclerView item为空的布局类型
    private static final int VIEW_TYPE = -1;

    Context context;
    List<ArtistInfoEntity.AlbumsBean> infolist;

    private ArtInfoOnItemClickListener onItemClickListener;



    public void setOnItemClickListener(ArtInfoOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //构造方法
    public CollectionsAdapter(Context context) {
        this.context = context;
        this.infolist = new ArrayList<>();
    }

    //设置数据方法
    public void setDatas(List<ArtistInfoEntity.AlbumsBean> infolist) {
        this.infolist = infolist;
        this.notifyDataSetChanged();
    }


    //SwipeMenuAdapter需要重写的2个方法
    //移动item的方法
    public void moveItem(int fromPosition,int toPosition){
        ArtistInfoEntity.AlbumsBean remove = this.infolist.remove(fromPosition);
        this.infolist.add(toPosition,remove);
        this.notifyItemMoved(fromPosition,toPosition);
    }

    //移除item的方法
    public void remove(int position){
        this.infolist.remove(position);
        this.notifyItemRemoved(position);
    }

    //SwipeMenuAdapter需要重写的2个方法
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        //RecyclerView item为空的适用类型
        if (VIEW_TYPE == viewType) {
            view = inflater.inflate(R.layout.empty_textview, parent, false);
            return view;

        }
        //正常有数据的item布局,以及ViewHolder
        view = inflater.inflate(R.layout.item_artist_workinfo, parent, false);
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (VIEW_TYPE == viewType) {
            return new MyEmptyHolder(realContentView);
        }
        return new ArtInfoHolder(realContentView);
    }


//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        //RecyclerView item为空的适用类型
//        if (VIEW_TYPE == viewType) {
//            view = inflater.inflate(R.layout.empty_textview, parent, false);
//
//            //返回RecyclerView item为空的ViewHolder
//            return new MyEmptyHolder(view);
//        }
//
//        //正常有数据的item布局,以及ViewHolder
//        view = inflater.inflate(R.layout.item_artist_workinfo, parent, false);
//        return new ArtInfoHolder(view);
//
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //正常有item数据ViewHolder的实例
        if (holder instanceof ArtInfoHolder) {
            ((ArtInfoHolder)holder).art_info_title.setText(infolist.get(position).getAlbumName());
            ((ArtInfoHolder)holder).art_info_name.setText(infolist.get(position).getArtistName());
            ((ArtInfoHolder)holder).artist_capter.setText(infolist.get(position).getAlbumChapter() + "");
            int playNumber = infolist.get(position).getPlayNumber();
            if(playNumber/10000 != 0) {
                ((ArtInfoHolder)holder).single_work_fans.setText((playNumber/10000) + "万");
            }else {
                ((ArtInfoHolder)holder).single_work_fans.setText(playNumber+"");
            }

            Glide.with(context)
                    .load(infolist.get(position).getAlbumCover())
                    .crossFade(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .transform(new GlideCircleTransform(context))
                    .thumbnail(0.1f)
                    .into(((ArtInfoHolder)holder).artist_img);
        }
    }


    @Override
    public int getItemCount() {
//        return infolist.size() > 0 ? infolist.size() : 1;
        return infolist.size() <= 0 ? 0 : infolist.size(); //三目运算符判断数据源集合的长度
    }

    public int getItemViewType(int position) {
        //若数据源集合的长度为0
        if (infolist.size() <= 0) {
            return VIEW_TYPE;
        }
        //正常数据源list集合返回类型
        return super.getItemViewType(position);
    }



    //数据源集合的长度为0的ViewHolder
    public class MyEmptyHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyEmptyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_empty);
        }
    }


    //正常item数据源的ViewHolder
    public class ArtInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView art_info_title,art_info_name,artist_capter,single_work_fans;//

        ImageView artist_img;
        public ArtInfoHolder(View itemView) {
            super(itemView);

            this.art_info_title = (TextView) itemView.findViewById(R.id.art_info_title);
            this.art_info_name = (TextView) itemView.findViewById(R.id.art_info_name);
            this.artist_capter = (TextView) itemView.findViewById(R.id.art_info_total_num);
            this.single_work_fans = (TextView) itemView.findViewById(R.id.artist_funs_num);
            this.artist_img = (ImageView) itemView.findViewById(R.id.art_info_img);

            itemView.setOnClickListener(this); //设置item的点击事件

        }

        //item的点击事件的监听
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }


    //item的点击事件的接口回调
    public interface ArtInfoOnItemClickListener{
        void onItemClick(View view, int position);
    }

}
