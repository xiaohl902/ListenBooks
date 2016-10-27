package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwkj.entity.WelcomeEntity;
import com.mwkj.util.Constant;
import com.mwkj.util.RetrofitService;
import com.qf.kenlibrary.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {
    @Bind(R.id.ac_welcome_img)
    ImageView acWelcomeImg;

    @Override
    protected int getContentId() {
        return R.layout.activity_welcome;
    }

    /**
     * 初始化方法
     */
    private boolean tmp = true;//标志位，当用户点击按钮跳过欢迎页面，不在执行自动跳转效果

    @Override
    protected void init() {
        Glide.with(WelcomeActivity.this).load("http://www.mow99.com/img/loading/5.jpg").into(acWelcomeImg);
        delayTime();//延迟跳转Activity
    }

    /**
     * 加载数据
     */
    @Override
    protected void loadDatas() {
       // DownJSON();//下载JSON
    }

    /**
     * 下载Json
     */
    private void DownJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HOME)
                .client(new OkHttpClient())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        /**
         * 请求城市列表
         */
        Call<ResponseBody> call = retrofitService.getWelcomeDatas();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();//URL数据源
                    analysisJSON(str);//解析json
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    /**
     * 解析json
     *
     * @param str
     */
    private void analysisJSON(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);//用来判断该Str是否是JSON格式，若不是，被系统捕捉，抛出异常
            TypeToken<WelcomeEntity> tt = new TypeToken<WelcomeEntity>(){};
            WelcomeEntity welcomeEntity = new Gson().fromJson(jsonObject.toString(), tt.getType());
            Log.d("print", "analysisJSON: JSON下载成功"+welcomeEntity.getLoadingAdv().getAdvImgUrl());
//            Log.d("print", "analysisJSON: JSON下载成功"+welcomeEntity.getLoadingAdv().getAdvForwardUrl());
//            Glide.with(WelcomeActivity.this).load(welcomeEntity.getLoadingAdv().getAdvImgUrl()).into(acWelcomeImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //延迟跳转Activity
    private void delayTime() {
        acWelcomeImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tmp) {
                    //进入登录页面
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        },3000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ac_welcome_skip)
    public void onClick() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
        tmp = false;
    }

    @OnClick(R.id.ac_welcome_img)
    public void doClick() {
        Log.d("print", "doClick: 点击了图片");
    }
}
