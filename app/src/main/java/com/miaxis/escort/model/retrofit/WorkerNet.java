package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.WorkerBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface WorkerNet {
    @GET("yygl/api/downWorker")
    Observable<ResponseEntity<WorkerBean>> downloadWorker(@Query("deptno")String deptno, @Query("opdateList")String opdateList);
}
