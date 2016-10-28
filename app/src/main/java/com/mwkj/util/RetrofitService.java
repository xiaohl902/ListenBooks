package com.mwkj.util;

import com.mwkj.entity.CrosstalkEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${WU} on 2016/10/27.
 */
public interface RetrofitService {

    //欢迎页面 , 获取图片
    @GET(Constant.USER_WELCOME)
    Call<ResponseBody> getWelcomeDatas();

    @GET(Constant.CROSSTALK)
    Call<CrosstalkEntity> getCrosstalkEntityByUrl();

    //听书馆-艺术家-艺术家详情-作品集合,获取实体类数据
    //albumId=%d、pageNumber=%d
    @GET(Constant.ARTIST_WORK_INFO)
    Call<ResponseBody> getWorksEntityByUrl(@Query("albumId") int albumId,@Query("pageNumber") int pageNumber);
}
