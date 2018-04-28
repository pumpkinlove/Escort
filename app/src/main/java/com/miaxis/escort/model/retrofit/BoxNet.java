package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.BoxBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface BoxNet {
    @GET("yygl/api/downBox")
    Observable<ResponseEntity<BoxBean>> downloadBox(@Query("deptno")String deptno, @Query("opdateList")String opdateList);

    @GET("yygl/api/updateBox")
    Observable<ResponseEntity<BoxBean>> updateBox(@Query("boxcodes")String boxcodes);
}
