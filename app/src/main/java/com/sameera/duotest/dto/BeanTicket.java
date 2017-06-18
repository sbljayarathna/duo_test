package com.sameera.duotest.dto;

/**
 * Created by Sameera on 6/17/17.
 */

public class BeanTicket {
    private String type, subject, description;
    private BeanPerson requester, submitter, assignee;

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public BeanPerson getRequester() {
        return requester;
    }

    public BeanPerson getSubmitter() {
        return submitter;
    }

    public BeanPerson getAssignee() {
        return assignee;
    }
}
