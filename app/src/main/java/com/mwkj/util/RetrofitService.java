package com.mwkj.util;

import com.mwkj.entity.CrosstalkEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ${WU} on 2016/10/27.
 */
public interface RetrofitService {

    //欢迎页面 , 获取图片
    @GET(Constant.USER_WELCOME)
    Call<ResponseBody> getWelcomeDatas();

    @GET(Constant.CROSSTALK)
    Call<CrosstalkEntity> getCrosstalkEntityByUrl();
}
