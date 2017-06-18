package com.sameera.duotest.message;

import com.sameera.duotest.dto.BeanTicket;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketDetailMessage {
    public static final int SHOW_PROGERSS = 1;
    public static final int HIDE_PROGERSS = 2;
    public static final int DATA_LOADED_SUCCESS = 3;
    public static final int DATA_LOADED_FAIL = 4;

    private int id;
    private String message;
    private BeanTicket ticket;

    public TicketDetailMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TicketDetailMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public TicketDetailMessage(BeanTicket ticket) {
        this.ticket = ticket;
        this.id = DATA_LOADED_SUCCESS;
    }

    public BeanTicket getTicket() {
        return ticket;
    }

    public String getMessage() {
        return message;
    }
}
