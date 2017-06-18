package com.sameera.duotest.presenter;

import android.util.Log;

import com.sameera.duotest.dto.BeanTicketDetail;
import com.sameera.duotest.message.TicketsMessage;
import com.sameera.duotest.service.ServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketsPresenter extends BasePresenter {
    private static Map<String, TicketsPresenter> presenters = new HashMap<String, TicketsPresenter>();
    public static final String PAGINATION_LIMIT = "10";

    private boolean isDataLoading;
    private List<BeanTicketDetail> ticketDetailList;

    public static TicketsPresenter getPresenter(String id) {
        TicketsPresenter presenter = presenters.get(id);
        if (presenter != null) {
            return presenter;
        } else {
            presenter = new TicketsPresenter(id);
            presenters.put(id, presenter);
            return presenter;
        }
    }

    //private constructor to avoid instantiation
    private TicketsPresenter(String id) {
        Log.d("View Loaded", "Presenter created for" + id);
    }


    @Override public void destroy(String id) {
        presenters.remove(id);
    }

    public void getTicketDetailList(String token, boolean isRefreshing, boolean isPaging, int pageNo) {
        if (isDataLoading) {
            sendMessageToUI(new TicketsMessage(TicketsMessage.SHOW_PROGERSS));
        } else if (ticketDetailList != null && !isRefreshing && !isPaging) {
            sendMessageToUI(new TicketsMessage(ticketDetailList));
        } else {
            sendMessageToUI(new TicketsMessage(TicketsMessage.SHOW_PROGERSS));

            isDataLoading = true;

            ServiceLocator.getInstance().getApiLT().getTicketDetailList("Bearer " + token, PAGINATION_LIMIT, String.valueOf(pageNo), "new").subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(beanStore -> {
                        if (ticketDetailList == null)
                            ticketDetailList = new ArrayList<BeanTicketDetail>();

                        if (isRefreshing || !isPaging) {
                            ticketDetailList = beanStore.getResult();
                            TicketsPresenter.this.sendMessageToUI(new TicketsMessage(ticketDetailList));
                        } else if (beanStore.getResult() != null && beanStore.getResult().size() > 0) {
                            ticketDetailList.addAll(beanStore.getResult());
                            TicketsPresenter.this.sendMessageToUI(new TicketsMessage(TicketsMessage.NOTIFY_PAGINATION));
                        } else {
                            TicketsPresenter.this.sendMessageToUI(new TicketsMessage("No more tickets."));
                        }
                        isDataLoading = false;

                        TicketsPresenter.this.sendMessageToUI(new TicketsMessage(TicketsMessage.HIDE_PROGERSS));
                    }, throwable -> {
                Log.d("Throwable", throwable.toString());
                isDataLoading = false;
                sendMessageToUI(new TicketsMessage("Unable to load data", TicketsMessage.DATA_LOAD_ERROR));
                sendMessageToUI(new TicketsMessage(TicketsMessage.HIDE_PROGERSS));
            });
        }

    }
}
