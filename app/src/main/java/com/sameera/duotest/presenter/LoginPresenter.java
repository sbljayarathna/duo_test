package com.sameera.duotest.presenter;

import android.util.Log;

import com.sameera.duotest.dto.BeanLoginAndProfile;
import com.sameera.duotest.dto.request.BeanLoginReq;
import com.sameera.duotest.dto.response.BeanLoginRes;
import com.sameera.duotest.dto.response.BeanProfileRes;
import com.sameera.duotest.dto.response.ServiceResponse;
import com.sameera.duotest.message.LoginMessage;
import com.sameera.duotest.service.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sameera on 6/16/17.
 */

public class LoginPresenter extends BasePresenter {

    private static Map<String, LoginPresenter> presenters = new HashMap<String, LoginPresenter>();

    public static LoginPresenter getPresenter(String id) {
        LoginPresenter presenter = presenters.get(id);
        if (presenter != null) {
            return presenter;
        } else {
            presenter = new LoginPresenter(id);
            presenters.put(id, presenter);
            return presenter;
        }
    }

    //private constructor to avoid instantiation
    private LoginPresenter(String id) {
        Log.d("View Loaded", "Presenter created for" + id);
    }


    @Override public void destroy(String id) {
        presenters.remove(id);
    }

    public void onClickLogin(String username, String password) {

        if (username.isEmpty()) {
            sendMessageToUI(new LoginMessage("Insert Username", LoginMessage.ERROR_USERNAME));
            return;
        } else if (password.isEmpty()) {
            sendMessageToUI(new LoginMessage("Insert Password", LoginMessage.ERROR_PASSWORD));
            return;
        }

        final BeanLoginReq loginReq = new BeanLoginReq();
        loginReq.setUserName(username);
        loginReq.setPassword(password);
        loginReq.setScope("all_all");
        loginReq.setConsole("AGENT_CONSOLE");
        loginReq.setClientID("e8ea7bb0-5026-11e7-a69b-b153a7c332b9");

        sendMessageToUI(new LoginMessage(LoginMessage.SHOW_PROGERSS));

        ServiceLocator.getInstance().getApiUS().login(loginReq)
                .flatMap(loginRes -> {
                    if (loginRes != null && !loginRes.getToken().isEmpty()) {
                        Observable<ServiceResponse<BeanProfileRes>> deviceObservable = ServiceLocator.getInstance().getApiUS().getProfile("Bearer " + loginRes.getToken());
                        Observable<BeanLoginRes> userObservable = Observable.just(loginRes);
                        return Observable.zip(userObservable, deviceObservable, BeanLoginAndProfile::new);
                    } else {
                        final BeanLoginAndProfile result = new BeanLoginAndProfile(loginRes, null);
                        return Observable.just(result);
                    }
                })
                .map(beanLoginAndProfile -> beanLoginAndProfile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(loginAndProfile -> {
                    if (loginAndProfile.getLoginRes() != null && loginAndProfile.getProfileRes() != null && !loginAndProfile.getLoginRes().getToken().isEmpty()) {
                        LoginPresenter.this.sendMessageToUI(new LoginMessage(loginAndProfile));
                    } else {
                        LoginPresenter.this.sendMessageToUI(new LoginMessage("Something went wrong!", LoginMessage.LOGIN_FAIL));
                    }

                    LoginPresenter.this.sendMessageToUI(new LoginMessage(LoginMessage.HIDE_PROGERSS));

                }, throwable -> {
            Log.d("Throwable", throwable.toString());
            sendMessageToUI(new LoginMessage("Something went wrong!", LoginMessage.LOGIN_FAIL));
        }, () -> sendMessageToUI(new LoginMessage(LoginMessage.HIDE_PROGERSS)));

    }
}
