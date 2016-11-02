package com.mwkj.AppContext;

import android.app.Application;

import com.mwkj.util.Constant;
import com.mwkj.util.RetrofitService;
import com.qf.kenlibrary.util.SharedUtil;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${WU} on 2016/10/28.
 */
public class AppStartContext extends Application {

    public  static RetrofitService utils;
    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送相关
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        SharedUtil.init(this);
       utils =  initRetrofit();
    }

    public RetrofitService initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HOME)//把http_root的url给他
                .addConverterFactory(GsonConverterFactory.create())//告诉他用Gson解析数据
                .build();
        return retrofit.create(RetrofitService.class);
    }
}
