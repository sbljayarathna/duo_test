package com.sameera.duotest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sameera.duotest.R;
import com.sameera.duotest.config.AppConst;
import com.sameera.duotest.dto.BeanLoginAndProfile;
import com.sameera.duotest.message.LoginMessage;
import com.sameera.duotest.presenter.LoginPresenter;
import com.sameera.duotest.util.AppUtil;
import com.sameera.duotest.util.SharedPref;
import com.squareup.otto.Subscribe;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sameera on 6/16/17.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> {

    public static final String KEY = LoginActivity.class.getSimpleName().toString();
    private String keyPresenter;

    @BindView(R.id.et_username) EditText username;
    @BindView(R.id.et_password) EditText password;

    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            keyPresenter = savedInstanceState.getString(KEY);
        }

        if (keyPresenter == null) {
            keyPresenter = UUID.randomUUID().toString();
        }

        //TODO - remove this when the app goes live
        username.setText("kasun.g@duosoftware.com");
        password.setText("ADTest123!");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, keyPresenter);
    }

    @NonNull @Override public LoginPresenter getPresenter() {
        return LoginPresenter.getPresenter(keyPresenter);
    }

    @Override public String getID() {
        return keyPresenter;
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin(Button button) {
        if (AppUtil.checkNetworkConnection(this)) {
            getPresenter().onClickLogin(getUsername(), getPassword());
        } else {
            AppUtil.checkInternetAlert(this, (dialog, which) -> {
                //When click settings
                AppUtil.showSystemSettingsDialog(LoginActivity.this);
                dialog.dismiss();
            });
        }
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public void loginSuccess(BeanLoginAndProfile loginAndProfile) {
        SharedPref.setAuthToken(this, loginAndProfile.getLoginRes().getToken());
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(AppConst.EXTRA_PROFILE, loginAndProfile.getProfileRes().getResult());
        startActivity(intent);

        //finishing login activity
        this.finish();
    }

    public void loginFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe public void onMessageReceived(LoginMessage message) {
        switch (message.getId()) {
            case LoginMessage.SHOW_PROGERSS:
                progressdialog = new ProgressDialog(this);
                progressdialog.setMessage("Please Wait....");
                progressdialog.setCancelable(false);
                progressdialog.show();
                break;
            case LoginMessage.HIDE_PROGERSS:
                progressdialog.dismiss();
                break;
            case LoginMessage.LOGIN_SUCCESS:
                loginSuccess(message.getLoginAndProfile());
                break;
            case LoginMessage.LOGIN_FAIL:
                progressdialog.dismiss();
                loginFail(message.getMessage());
                break;
            case LoginMessage.ERROR_USERNAME:
                usernameError(message.getMessage());
                break;
            case LoginMessage.ERROR_PASSWORD:
                passwordError(message.getMessage());
                break;
        }
    }

    public void usernameError(String error) {
        username.setError(error);
    }

    public void passwordError(String error) {
        password.setError(error);
    }

}
