package com.sameera.duotest.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameera on 6/18/17.
 */

public class ServiceResponse<T> {
    private T Result;
    private String Exception;
    private String CustomMessage;

    @SerializedName("IsSuccess")
    private boolean success;

    public T getResult() {
        return Result;
    }

    public String getException() {
        return Exception;
    }

    public String getCustomMessage() {
        return CustomMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
