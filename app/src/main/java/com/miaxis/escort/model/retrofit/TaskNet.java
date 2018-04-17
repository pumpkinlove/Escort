package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.TaskBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface TaskNet {
    @GET("yygl/api/downTask")
    Observable<ResponseEntity<TaskBean>> downloadTask(@Query("deptno")String deptno, @Query("opdate")String opdate);
}
