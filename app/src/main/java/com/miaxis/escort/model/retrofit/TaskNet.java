package com.miaxis.escort.model.retrofit;

import com.miaxis.escort.model.entity.TaskBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface TaskNet {
    @GET("yygl/api/downTask")
    Observable<ResponseEntity<TaskBean>> downloadTask(@Query("deptno")String deptno, @Query("opdate")String opdate);

    @GET("yygl/api/downTask")
    Call<ResponseBody> downTask(@Query("deptno")String deptno, @Query("opdate")String opdate);

    @FormUrlEncoded
    @POST("yygl/api/uploadTask")
    Observable<ResponseEntity> uploadTask(@Field("jsonTaskBean") String jsonTaskBean, @Field("boxList") String boxList);

    @FormUrlEncoded
    @POST("yygl/api/execTask")
    Observable<ResponseEntity> uploadTaskExec(@Field("execTask") String execTask);

    @GET("yygl/api/delTask")
    Observable<ResponseEntity> deleteTask(@Query("taskcode")String taskcode);
}
