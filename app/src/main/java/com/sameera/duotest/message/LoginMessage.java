package com.sameera.duotest.message;

import com.sameera.duotest.dto.BeanLoginAndProfile;

/**
 * Created by Sameera on 6/16/17.
 */

public class LoginMessage {
    public static final int SHOW_PROGERSS = 1;
    public static final int HIDE_PROGERSS = 2;
    public static final int LOGIN_SUCCESS = 3;
    public static final int LOGIN_FAIL = 4;
    public static final int ERROR_USERNAME = 5;
    public static final int ERROR_PASSWORD = 6;

    private int id;
    private String message;
    private BeanLoginAndProfile loginAndProfile;

    public LoginMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LoginMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public LoginMessage(BeanLoginAndProfile loginAndProfile) {
        this.loginAndProfile = loginAndProfile;
        this.id = LOGIN_SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public BeanLoginAndProfile getLoginAndProfile() {
        return loginAndProfile;
    }
}
