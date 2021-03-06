package com.mwkj.util;

import com.mwkj.entity.BookEntity;
import com.mwkj.entity.CommunityTopicEntity;
import com.mwkj.entity.CrosstalkEntity;
import com.mwkj.entity.ShowEntity;
import com.mwkj.entity.SpecialDetailsEntity;
import com.mwkj.entity.SpecialEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
    Call<ResponseBody> getWorksEntityByUrl(@Query("albumId") int albumId, @Query("pageNumber") int pageNumber);

    //评书
    @GET(Constant.STORYTELLING)
    Call<CrosstalkEntity> getStorytellingEntityByUrl();

    //评书-经典
    @GET
    Call<BookEntity> downLoad(@Url String str, @Query("pageNumber") Integer pageNumber);

        //听书馆-专题
    @GET(Constant.ZHUANTI)
    Call<SpecialEntity> downSpecialEntity(@Query("pageNumber") Integer pageNumber);

    //听书馆-专题-详情
    @GET(Constant.ZHUANTI_DETAILS)
    Call<SpecialDetailsEntity> getSpecialDetailsEntity(@Query("subjectId")Integer id ,@Query("pageNumber") Integer pageNumber);

    //演出&pageNumber=1
    @GET(Constant.SHOW)
    Call<ShowEntity> getShowEnityByUrl(@Query("pageNumber") Integer pageNumber, @Query("showDate") String year, @Query("cityName") String cityName);
    //搜索
    @GET(Constant.SEARCH)
    Call<BookEntity> getBookEntityBySearch(@Query("pageNumber") Integer pageNumber,@Query("query") String query);

    //社区-那些事儿
    @GET(Constant.COMMUNITY_SOMETHING)
    Call<CommunityTopicEntity> getsomething(@Query("fourmId")Integer fourmId,@Query("pageSize") Integer pageSize);
}
