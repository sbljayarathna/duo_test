package com.sameera.duotest.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sameera on 6/17/17.
 */

public class BeanTicketDetail {
    @SerializedName("_id")
    private String id;
    private String subject, type, priority, status;

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}
