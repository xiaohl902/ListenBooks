package com.mwkj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.activity.ArtWorksActivity;
import com.mwkj.activity.ArtistInfoActivity;
import com.mwkj.activity.BookTypeActivity;
import com.mwkj.activity.R;
import com.mwkj.entity.CrosstalkEntity;
import com.mwkj.util.Constant;
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
public class JudgeFragmentAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<Integer> data;
    private CrosstalkEntity dataEntity;

    public JudgeFragmentAdapter(Context context, List<Integer> data) {
        this.context = context;
        this.data = data;
    }
    public void setData(CrosstalkEntity dataEntity) {
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
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            switch (getItemViewType(i)) {
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.item_storytelling_one, null);
                    holder.banner = (Banner) view.findViewById(R.id.storytelling_banner);
                    holder.one_jd = (TextView) view.findViewById(R.id.jd);
                    holder.one_xy = (TextView) view.findViewById(R.id.xy);
                    holder.one_ga = (TextView) view.findViewById(R.id.ga);
                    holder.one_ls = (TextView) view.findViewById(R.id.ls);
                    holder.one_gl = (TextView) view.findViewById(R.id.gl);
                    holder.one_dm = (TextView) view.findViewById(R.id.dm);
                    holder.one_yy = (TextView) view.findViewById(R.id.yy);
                    holder.one_qb = (TextView) view.findViewById(R.id.qb);

                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_hot, null);
                    holder.hotGridView = (NoScrollGridView) view.findViewById(R.id.hotGridView);
                    holder.more = (TextView) view.findViewById(R.id.more);
                    holder.hotAdapter = new HotAdapter(context);
                    holder.title = (TextView) view.findViewById(R.id.title);
                    break;
                case 2:
                    view = LayoutInflater.from(context).inflate(R.layout.item_specialty, null);
                    holder.myListView = (MyListView) view.findViewById(R.id.specialLv);
                    holder.specialAdapter = new SpecialAdapter(context);
                    holder.more = (TextView) view.findViewById(R.id.s_more);
                    break;
                case 3:
                    view = LayoutInflater.from(context).inflate(R.layout.item_artist, null);
                    holder.noScrollGridView = (NoScrollGridView) view.findViewById(R.id.artistGridView);
                    holder.inArtistAdapter = new InArtistAdapter(context);
                   holder.turn = (TextView) view.findViewById(R.id.more);
                    break;
            }
            view.setTag(holder);
        }
        switch (getItemViewType(i)) {
            case 0:
                //设置轮播
                final List<CrosstalkEntity.AdvsBean> advs1 = dataEntity.getAdvs();
                holder.banner.setImageLoader(new GlideImageLoader());
                holder.banner.setDelayTime(4000);
                holder.banner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //                        Log.d("log", "OnBannerClick: " + position);
                        //                        Log.d("log", "OnBannerClick: " +advs1.get(position-1).getAlbum()+"---");
                        if (advs1.get(position - 1).getAlbum() != null) {
                            Intent intent = new Intent(context, ArtWorksActivity.class);
                            intent.putExtra("albumid", advs1.get(position - 1).getAlbum().getAlbumId());
                            intent.putExtra("worksname",advs1.get(position - 1).getAlbum().getAlbumName());
                            intent.putExtra("chapternum",advs1.get(position - 1).getAlbum().getAlbumChapter());
                            intent.putExtra("fansnum",advs1.get(position - 1).getAlbum().getPlayNumber());
//                            intent.putExtra("worksimg",advs1.get(position - 1).getAlbum().getAlbumCover());
                            intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
                            context.startActivity(intent);
                        }
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
                holder.one_jd.setOnClickListener(this);
                holder.one_xy.setOnClickListener(this);
                holder.one_ga.setOnClickListener(this);
                holder.one_ls.setOnClickListener(this);
                holder.one_gl.setOnClickListener(this);
                holder.one_dm.setOnClickListener(this);
                holder.one_yy.setOnClickListener(this);
                holder.one_qb.setOnClickListener(this);


                break;
            case 1:
                holder.title.setText("热门评书");
                holder.hotGridView.setAdapter(holder.hotAdapter);
                holder.hotAdapter.setDatas(dataEntity.getHot());

                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, BookTypeActivity.class);
                        intent.putExtra("title","全部");
                        intent.putExtra("url", Constant.ALL);
                        context.startActivity(intent);
                    }
                });
                holder.hotGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       Intent intent = new Intent(context,ArtWorksActivity.class);
                        intent.putExtra("albumid", dataEntity.getHot().get(i).getAlbumId());
                        intent.putExtra("worksname",dataEntity.getHot().get(i).getAlbumName());
                        intent.putExtra("chapternum",dataEntity.getHot().get(i).getAlbumChapter());
                        intent.putExtra("fansnum",dataEntity.getHot().get(i).getPlayNumber());
                                                    intent.putExtra("worksimg",dataEntity.getHot().get(i).getAlbumCover());
//                        intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                holder.myListView.setAdapter(holder.specialAdapter);
                holder.specialAdapter.setDatas(dataEntity.getSpecialty());
                holder.myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(context,ArtWorksActivity.class);
                        intent.putExtra("albumid", dataEntity.getSpecialty().get(i).getAlbumId());
                        intent.putExtra("worksname",dataEntity.getSpecialty().get(i).getAlbumName());
                        intent.putExtra("chapternum",dataEntity.getSpecialty().get(i).getAlbumChapter());
                        intent.putExtra("fansnum",dataEntity.getSpecialty().get(i).getPlayNumber());
                        intent.putExtra("worksimg",dataEntity.getSpecialty().get(i).getAlbumCover());
                        // intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
                        context.startActivity(intent);
                    }
                });

                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, BookTypeActivity.class);
                        intent.putExtra("title","全部");
                        intent.putExtra("url", Constant.ALL);
                        context.startActivity(intent);
                    }
                });
                break;
            case 3:
                holder.noScrollGridView.setAdapter(holder.inArtistAdapter);
                holder.inArtistAdapter.setDatas(dataEntity.getArtist());
                holder.noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(context,ArtistInfoActivity.class);
                        //用intent将艺术家id及头像传送至跳转后页面
                        intent.putExtra("artistid",dataEntity.getArtist().get(i).getArtistId());
                        intent.putExtra("artistimg",dataEntity.getArtist().get(i).getArtistImg());
                        context.startActivity(intent);
                    }
                });
                holder.turn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction("action.qf.intent.turn");
                        context.sendBroadcast(intent);
                    }
                });
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, BookTypeActivity.class);
        switch (view.getId()) {
            case R.id.jd:
                intent.putExtra("title","经典");
                intent.putExtra("url", Constant.JINGDIAN);
                break;
            case R.id.xy:
                intent.putExtra("title","侠义");
                intent.putExtra("url", Constant.XIAYI);
                break;
            case R.id.ga:
                intent.putExtra("title","公案");
                intent.putExtra("url", Constant.GONGAN);
                break;
            case R.id.ls:
                intent.putExtra("title","历史");
                intent.putExtra("url", Constant.LISHI);
                break;
            case R.id.gl:
                intent.putExtra("title","古龙");
                intent.putExtra("url", Constant.GULONG);
                break;
            case R.id.dm:
                intent.putExtra("title","动漫");
                intent.putExtra("url", Constant.DONGMAN);
                break;
            case R.id.yy:
                intent.putExtra("title","粤语");
                intent.putExtra("url", Constant.YUEYU);
                break;
            case R.id.qb:
                intent.putExtra("title","全部");
                intent.putExtra("url", Constant.ALL);
                break;
        }
        context.startActivity(intent);
    }

    class ViewHolder {
        //第一部分
        Banner banner;
        TextView one_jd, one_xy, one_ga, one_ls, one_gl, one_dm, one_yy, one_qb;

        //第二部分（热门书评）
        TextView title;
        TextView more;
        NoScrollGridView hotGridView;
        HotAdapter hotAdapter;

        //第三部分（特色专辑）
        MyListView myListView;
        SpecialAdapter specialAdapter;

        //第四部分（艺术家）
        NoScrollGridView noScrollGridView;
        InArtistAdapter inArtistAdapter;
        TextView  turn;
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
