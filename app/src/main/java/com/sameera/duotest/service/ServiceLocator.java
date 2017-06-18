package com.sameera.duotest.service;

import com.sameera.duotest.config.ApiEndpointInterfaceLT;
import com.sameera.duotest.config.ApiEndpointInterfaceUS;
import com.squareup.otto.Bus;

/**
 * Created by Sameera on 6/16/17.
 */

public final class ServiceLocator {

    private final Bus bus;
    private final ApiEndpointInterfaceUS apiEndpointInterfaceUS;
    private final ApiEndpointInterfaceLT apiEndpointInterfaceLT;
    private static ServiceLocator INSTANCE;

    private ServiceLocator() {
        bus = new Bus();
        apiEndpointInterfaceUS = RetrofitBuilder.getRetrofitInstanceUS().create(ApiEndpointInterfaceUS.class);
        apiEndpointInterfaceLT = RetrofitBuilder.getRetrofitInstanceLT().create(ApiEndpointInterfaceLT.class);
    }

    public Bus getBus() {
        return bus;
    }

    public ApiEndpointInterfaceUS getApiUS() {
        return apiEndpointInterfaceUS;
    }

    public ApiEndpointInterfaceLT getApiLT() {
        return apiEndpointInterfaceLT;
    }

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceLocator();
        }
        return INSTANCE;
    }
}