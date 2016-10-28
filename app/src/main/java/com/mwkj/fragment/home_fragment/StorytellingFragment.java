package com.mwkj.fragment.home_fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mwkj.activity.R;
import com.mwkj.adapter.JudgeFragmentAdapter;
import com.mwkj.entity.CrosstalkEntity;
import com.mwkj.util.Constant;
import com.mwkj.util.RetrofitService;
import com.qf.kenlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//评书
public class StorytellingFragment extends BaseFragment{
    @Bind(R.id.lv)
    ListView lv;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    //适配器
    private JudgeFragmentAdapter adapter;
    //数据
    private CrosstalkEntity dataEntity;
    @Override
    protected int getContentId() {
        return R.layout.fragment_home_storytelling;
    }

    @Override
    protected void init(View view) {
       //初始化网络请求
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.HOME)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            datas.add(i);
        }
       adapter = new JudgeFragmentAdapter(getContext(),datas);

    }

    @Override
    protected void loadDatas() {
        Call<CrosstalkEntity> call =retrofitService.getStorytellingEntityByUrl();
        call.enqueue(new Callback<CrosstalkEntity>() {
            @Override
            public void onResponse(Call<CrosstalkEntity> call, Response<CrosstalkEntity> response) {
                dataEntity = response.body();

                adapter.setData(dataEntity);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CrosstalkEntity> call, Throwable t) {
                Log.d("log", "onFailure:------- " +"下载失败");
            }
        });

    }
}
