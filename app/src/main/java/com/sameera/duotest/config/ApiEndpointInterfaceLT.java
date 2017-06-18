package com.sameera.duotest.config;

import com.sameera.duotest.dto.BeanTicket;
import com.sameera.duotest.dto.BeanTicketDetail;
import com.sameera.duotest.dto.response.ServiceResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Sameera on 6/17/17.
 */

public interface ApiEndpointInterfaceLT {
    @GET("DVP/API/1.0.0.0/MyTickets/{limit}/{page}")
    Observable<ServiceResponse<List<BeanTicketDetail>>> getTicketDetailList(@Header("Authorization") String token, @Path("limit") String limit, @Path("page") String page, @Query("status") String status);

    @GET("DVP/API/1.0.0.0/Ticket/{id}/Details")
    Observable<ServiceResponse<BeanTicket>> getTicketDetail(@Header("Authorization") String token, @Path("id") String id);
}
