package com.mwkj.util;

import com.mwkj.entity.CrosstalkEntity;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ${WU} on 2016/10/27.
 */
public interface RetrofitService {
    @GET(Constant.CROSSTALK)
    Call<CrosstalkEntity> getCrosstalkEntityByUrl();
}
