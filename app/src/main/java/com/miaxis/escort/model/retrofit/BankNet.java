package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.BankBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface BankNet {
    @GET("yygl/api/downBank")
    Observable<ResponseEntity<BankBean>> downloadBank(@Query("bankcode") String orgCode);
}
