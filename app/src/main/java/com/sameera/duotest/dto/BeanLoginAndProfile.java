package com.sameera.duotest.dto;

import com.sameera.duotest.dto.response.BeanLoginRes;
import com.sameera.duotest.dto.response.BeanProfileRes;
import com.sameera.duotest.dto.response.ServiceResponse;

/**
 * Created by Sameera on 6/18/17.
 */

public final class BeanLoginAndProfile {
    private final BeanLoginRes loginRes;
    private final ServiceResponse<BeanProfileRes> profileRes;

    public BeanLoginAndProfile(BeanLoginRes loginRes, ServiceResponse<BeanProfileRes> profileRes) {
        this.loginRes = loginRes;
        this.profileRes = profileRes;
    }

    public BeanLoginRes getLoginRes() {
        return loginRes;
    }

    public ServiceResponse<BeanProfileRes> getProfileRes() {
        return profileRes;
    }
}
