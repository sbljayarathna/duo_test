package com.sameera.duotest.message;

import com.sameera.duotest.dto.BeanTicketDetail;

import java.util.List;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketsMessage {
    public static final int SHOW_PROGERSS = 1;
    public static final int HIDE_PROGERSS = 2;
    public static final int DATA_LOADED = 3;
    public static final int SHOW_MESSAGE = 4;
    public static final int NOTIFY_PAGINATION = 5;
    public static final int DATA_LOAD_ERROR = 6;


    private int id;
    private String message;
    private List<BeanTicketDetail> storeItems;

    public TicketsMessage(int id) {
        this.id = id;
    }

    public TicketsMessage(List<BeanTicketDetail> storeItems) {
        this.storeItems = storeItems;
        this.id = DATA_LOADED;
    }

    public TicketsMessage(String message) {
        this.message = message;
        this.id = SHOW_MESSAGE;
    }

    public TicketsMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public List<BeanTicketDetail> getStoreItems() {
        return storeItems;
    }

    public int getId() {
        return id;
    }
}
