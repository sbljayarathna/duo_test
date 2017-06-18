package com.sameera.duotest.presenter;

import android.util.Log;

import com.sameera.duotest.dto.BeanTicket;
import com.sameera.duotest.dto.response.ServiceResponse;
import com.sameera.duotest.message.LoginMessage;
import com.sameera.duotest.message.TicketDetailMessage;
import com.sameera.duotest.service.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketDetailPresenter extends BasePresenter {

    private static Map<String, TicketDetailPresenter> presenters = new HashMap<String, TicketDetailPresenter>();

    private BeanTicket ticket;

    public static TicketDetailPresenter getPresenter(String id) {
        TicketDetailPresenter presenter = presenters.get(id);
        if (presenter != null) {
            return presenter;
        } else {
            presenter = new TicketDetailPresenter(id);
            presenters.put(id, presenter);
            return presenter;
        }
    }

    //private constructor to avoid instantiation
    private TicketDetailPresenter(String id) {
        Log.d("View Loaded", "Presenter created for" + id);
    }


    @Override public void destroy(String id) {
        presenters.remove(id);
    }

    public void getTicketDetail(String id, String token) {
        if (ticket == null) {
            sendMessageToUI(new TicketDetailMessage(TicketDetailMessage.SHOW_PROGERSS));
            ServiceLocator.getInstance().getApiLT().getTicketDetail("Bearer " + token, id).subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ServiceResponse<BeanTicket>>() {
                @Override public void call(ServiceResponse<BeanTicket> ticketRes) {
                    if (ticketRes.getResult() != null) {
                        ticket = ticketRes.getResult();
                        TicketDetailPresenter.this.sendMessageToUI(new TicketDetailMessage(ticketRes.getResult()));
                    } else {
                        TicketDetailPresenter.this.sendMessageToUI(new TicketDetailMessage("Something went wrong!", TicketDetailMessage.DATA_LOADED_FAIL));
                    }
                }
            }, throwable -> {
                Log.d("Throwable", throwable.toString());
                sendMessageToUI(new TicketDetailMessage("Something went wrong!", TicketDetailMessage.DATA_LOADED_FAIL));
            }, () -> sendMessageToUI(new TicketDetailMessage(TicketDetailMessage.HIDE_PROGERSS)));
        } else {
            TicketDetailPresenter.this.sendMessageToUI(new TicketDetailMessage(ticket));
        }
    }
}