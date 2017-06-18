package com.sameera.duotest.config;

import com.sameera.duotest.dto.request.BeanLoginReq;
import com.sameera.duotest.dto.response.BeanLoginRes;
import com.sameera.duotest.dto.response.BeanProfileRes;
import com.sameera.duotest.dto.response.ServiceResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Sameera on 6/16/17.
 */

public interface ApiEndpointInterfaceUS {
    @POST("auth/login")
    Observable<BeanLoginRes> login(@Body BeanLoginReq loginReq);

    @GET("DVP/API/1.0.0.0/Myprofile")
    Observable<ServiceResponse<BeanProfileRes>> getProfile(@Header("Authorization") String token);
}
