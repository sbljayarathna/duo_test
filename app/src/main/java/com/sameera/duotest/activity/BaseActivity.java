package com.sameera.duotest.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.sameera.duotest.presenter.BasePresenter;
import com.sameera.duotest.service.ServiceLocator;

/**
 * Created by Sameera on 6/16/17.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    @NonNull public abstract T getPresenter();

    @Override protected void onPause() {
        super.onPause();
        getPresenter().setVisible(false);
        ServiceLocator.getInstance().getBus().unregister(this);
    }

    @Override protected void onResume() {
        super.onResume();
        ServiceLocator.getInstance().getBus().register(this);
        getPresenter().setVisible(true);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            getPresenter().destroy(getID());
        }
    }

    public String getID() {
        return null;
    }

}
