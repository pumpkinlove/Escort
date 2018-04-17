package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.EscortBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface EscortNet {
    @GET("yygl/api/downEscort")
    Observable<ResponseEntity<EscortBean>> downloadEscort(@Query("opdateList")String opdateList);
}
