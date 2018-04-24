package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.BankBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface AppNet {
    @GET("yygl/api/getAppLength")
    Observable<ResponseEntity<String>> getAppLength();
    @GET("yygl/api/getAppData")
    Observable<ResponseEntity<String>> getAppData(@Query("start") int start, @Query("length") int length);
    @GET("yygl/api/getAppVersion")
    Observable<ResponseEntity<String>> getAppVersion();
    @GET("yygl/api/getTime")
    Observable<ResponseEntity<String>> getTime();
}
