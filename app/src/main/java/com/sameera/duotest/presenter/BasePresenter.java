package com.sameera.duotest.presenter;

import com.sameera.duotest.service.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameera on 6/16/17.
 */

public abstract class BasePresenter<T> {

    private boolean isVisible;
    private List<T> cacheMessages = new ArrayList<>();

    public abstract void destroy(String id);

    public void setVisible(boolean visible) {
        isVisible = visible;
        if (isVisible) {
            for (int i = cacheMessages.size() - 1; i >= 0; i--) {
                ServiceLocator.getInstance().getBus().post(cacheMessages.get(i));
                cacheMessages.remove(i);
            }
        }
    }

    protected void sendMessageToUI(T message) {
        if (isVisible) {
            ServiceLocator.getInstance().getBus().post(message);
        } else {
            cacheMessages.add(0, message);
        }
    }

}