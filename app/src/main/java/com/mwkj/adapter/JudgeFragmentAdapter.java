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
import com.mwkj.widget.MyListView;
import com.mwkj.widget.NoScrollGridView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class JudgeFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> data;
    private CrosstalkEntity dataEntity;
    public JudgeFragmentAdapter(Context context,List<Integer> data) {
        this.context = context;
        this.data =data;
    }

    public void setData(CrosstalkEntity dataEntity){
        this.dataEntity = dataEntity;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view!=null){
            holder = (ViewHolder) view.getTag();
        }else {
            holder = new ViewHolder();
            switch (getItemViewType(i)){
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.item_storytelling_one,null);
                    holder.banner = (Banner) view.findViewById(R.id.storytelling_banner);
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_hot,null);
                    holder.hotGridView = (NoScrollGridView) view.findViewById(R.id.hotGridView);
                    holder.hotAdapter = new HotAdapter(context);
                    holder.title = (TextView) view.findViewById(R.id.title);
                    break;
                case 2:
                    view = LayoutInflater.from(context).inflate(R.layout.item_specialty,null);
                        holder.myListView = (MyListView) view.findViewById(R.id.specialLv);
                        holder.specialAdapter = new SpecialAdapter(context);
                    break;
                case 3:
                    view = LayoutInflater.from(context).inflate(R.layout.item_artist,null);
                    holder.noScrollGridView = (NoScrollGridView) view.findViewById(R.id.artistGridView);
                    holder.inArtistAdapter = new InArtistAdapter(context);
                    break;
            }
            view.setTag(holder);
        }
        switch (getItemViewType(i)){
            case 0:
                //设置轮播
                holder.banner.setImageLoader(new GlideImageLoader());
                holder.banner.setDelayTime(4000);
                holder.banner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                });

                List<CrosstalkEntity.AdvsBean> advs = dataEntity.getAdvs();
                List<String> images = new ArrayList<>();
                for (CrosstalkEntity.AdvsBean adv : advs) {
                    images.add(adv.getAdvImg());
                }
                //设置图片集合
                holder.banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                holder.banner.start();
                view.setTag(holder);
                break;
            case 1:
                holder.title.setText("热门评书");
                holder.hotGridView.setAdapter(holder.hotAdapter);
                holder.hotAdapter.setDatas(dataEntity.getHot());
                break;
            case 2:
                holder.myListView.setAdapter(holder.specialAdapter);
                holder.specialAdapter.setDatas(dataEntity.getSpecialty());
                break;
            case 3:
                holder.noScrollGridView.setAdapter(holder.inArtistAdapter);
                holder.inArtistAdapter.setDatas(dataEntity.getArtist());
                break;
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }
    class ViewHolder{
        //第一部分
        Banner banner;
        TextView one_jd,onexy,one_ga,one_ls,one_gl,one_dm,one_yy,one_qb;

        //第二部分（热门书评）
        TextView title;
        NoScrollGridView hotGridView;
        HotAdapter hotAdapter;

        //第三部分（特色专辑）
        MyListView myListView;
        SpecialAdapter specialAdapter;

        //第四部分（艺术家）
        NoScrollGridView noScrollGridView;
        InArtistAdapter inArtistAdapter;
    }
    public class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：

             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */
            Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

        }
    }
}
