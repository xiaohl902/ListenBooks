package com.mwkj.fragment.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mwkj.activity.ArtWorksActivity;
import com.mwkj.activity.BookTypeActivity;
import com.mwkj.activity.R;
import com.mwkj.adapter.MyAdapter;
import com.mwkj.entity.CrosstalkEntity;
import com.mwkj.util.Constant;
import com.mwkj.util.RetrofitService;
import com.mwkj.widget.MyListView;
import com.qf.kenlibrary.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//相声
public class CrosstalkFragment extends BaseFragment {
    @Bind(R.id.banner)
    Banner banner;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    List<String> images = new ArrayList<>();
    private CrosstalkEntity body;
    @Bind(R.id.mlv)
    MyListView mlv;

    private MyAdapter myAdapter;
    @Override
    protected int getContentId() {

        return R.layout.fragment_home_crosstalk;
    }

    @Override
    protected void init(View view) {
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.HOME)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(4000);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                List<CrosstalkEntity.AdvsBean> advs = body.getAdvs();
                Intent intent = new Intent(getContext(), ArtWorksActivity.class);
                intent.putExtra("albumid", advs.get(position - 1).getAlbum().getAlbumId());
                intent.putExtra("worksname",advs.get(position - 1).getAlbum().getAlbumName());
                intent.putExtra("chapternum",advs.get(position - 1).getAlbum().getAlbumChapter());
                intent.putExtra("fansnum",advs.get(position - 1).getAlbum().getPlayNumber());
                //                            intent.putExtra("worksimg",advs1.get(position - 1).getAlbum().getAlbumCover());
                intent.putExtra("worksimg",advs.get(position-1).getAdvImg());
                getContext().startActivity(intent);
            }
        });


    }

    @OnClick({R.id.jd, R.id.ct, R.id.dk, R.id.qb})
    public void btnClick(View v){
        Intent intent = new Intent(getContext(), BookTypeActivity.class);
        switch (v.getId()){
            case R.id.jd:
                intent.putExtra("title","经典");
                intent.putExtra("url",Constant.XJINGDIAN );
                break;
            case R.id.ct:
                intent.putExtra("title","传统");
                intent.putExtra("url",Constant.XCHANGTONG );
                break;
            case R.id.dk:
                intent.putExtra("title","单口");
                intent.putExtra("url",Constant.XDANKOU );
                break;
            case R.id.qb:
                intent.putExtra("title","全部");
                intent.putExtra("url",Constant.XALL );
                break;
        }
        getContext().startActivity(intent);
    }
    @Override
    protected void loadDatas() {
        Call<CrosstalkEntity> call = retrofitService.getCrosstalkEntityByUrl();
        call.enqueue(new Callback<CrosstalkEntity>() {
            @Override
            public void onResponse(Call<CrosstalkEntity> call, Response<CrosstalkEntity> response) {
                body = response.body();

                List<CrosstalkEntity.AdvsBean> advs = body.getAdvs();

                for (CrosstalkEntity.AdvsBean adv : advs) {
                    images.add(adv.getAdvImg());
                }
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                myAdapter = new MyAdapter(getContext(),body);
                mlv.setAdapter(myAdapter);
                List<Integer> datas = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    datas.add(i);
                }
                myAdapter.setDatas(datas);
            }

            @Override
            public void onFailure(Call<CrosstalkEntity> call, Throwable t) {

            }
        });

    }
    //头部轮播图片加载数据的方法
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
