package com.sameera.duotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sameera.duotest.R;
import com.sameera.duotest.config.AppConst;
import com.sameera.duotest.dto.response.BeanProfileRes;
import com.sameera.duotest.presenter.ProfilePresenter;
import com.sameera.duotest.util.AppUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sameera on 6/18/17.
 */

public class ProfileActivity extends BaseActivity<ProfilePresenter> {
    public static final String KEY = ProfileActivity.class.getSimpleName().toString();

    private boolean mBackPressedToExitOnce = false;
    private String keyPresenter;
    private BeanProfileRes profileRes;

    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_dob) TextView tvDob;
    @BindView(R.id.tv_gender) TextView tvGender;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.tv_email) TextView tvEmail;

    @BindView(R.id.iv_profile) ImageView ivProfile;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(AppConst.EXTRA_PROFILE)) {
            profileRes = getIntent().getExtras().getParcelable(AppConst.EXTRA_PROFILE);
        }

        if (savedInstanceState != null) {
            keyPresenter = savedInstanceState.getString(KEY);
        }

        if (keyPresenter == null) {
            keyPresenter = UUID.randomUUID().toString();
        }

        init();
        loadProfileData();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    private void loadProfileData() {
        tvName.setText(profileRes.getFirstname() + " " + profileRes.getLastname());
        tvDob.setText(profileRes.getBirthday().split("T")[0]);
        tvGender.setText(profileRes.getGender());
        tvPhone.setText(profileRes.getPhoneNumber().getContact());
        tvEmail.setText(profileRes.getEmail().getContact());

        Glide.with(this).load(profileRes.getAvatar()).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder)).into(ivProfile);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, keyPresenter);
    }

    @NonNull @Override public ProfilePresenter getPresenter() {
        return ProfilePresenter.getPresenter(keyPresenter);
    }

    @OnClick(R.id.btn_check_tickets)
    public void onClickCheckTickets(Button button) {
        if (AppUtil.checkNetworkConnection(this)) {
            startActivity(new Intent(this, TicketsActivity.class));
        } else {
            AppUtil.checkInternetAlert(this, (dialog, which) -> {
                //When click settings
                AppUtil.showSystemSettingsDialog(ProfileActivity.this);
                dialog.dismiss();
            });
        }
    }


    @Override
    public void onBackPressed() {
        if (mBackPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.mBackPressedToExitOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> mBackPressedToExitOnce = false, AppConst.BACK_PRESSED_DELAY);
        }
    }
}
