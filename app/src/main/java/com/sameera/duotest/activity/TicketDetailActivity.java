package com.sameera.duotest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sameera.duotest.R;
import com.sameera.duotest.config.AppConst;
import com.sameera.duotest.dto.BeanTicket;
import com.sameera.duotest.message.LoginMessage;
import com.sameera.duotest.message.TicketDetailMessage;
import com.sameera.duotest.presenter.TicketDetailPresenter;
import com.sameera.duotest.util.SharedPref;
import com.squareup.otto.Subscribe;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketDetailActivity extends BaseActivity<TicketDetailPresenter> {

    public static final String KEY = TicketDetailActivity.class.getSimpleName().toString();
    private String keyPresenter, id;
    private ProgressDialog progressdialog;

    @BindView(R.id.tv_type) TextView type;
    @BindView(R.id.tv_subject) TextView subject;
    @BindView(R.id.tv_description) TextView description;
    @BindView(R.id.tv_requester) TextView tvRequester;
    @BindView(R.id.tv_submitter) TextView tvSubmitter;
    @BindView(R.id.tv_assignee) TextView tvAssignee;

    @BindView(R.id.iv_requester) ImageView ivRequester;
    @BindView(R.id.iv_submitter) ImageView ivSubmitter;
    @BindView(R.id.iv_assignee) ImageView ivAssignee;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail_activity);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(AppConst.EXTRA_TICKET_ID)) {
            id = getIntent().getExtras().getString(AppConst.EXTRA_TICKET_ID);
        }

        if (savedInstanceState != null) {
            keyPresenter = savedInstanceState.getString(KEY);
        }

        if (keyPresenter == null) {
            keyPresenter = UUID.randomUUID().toString();
        }

        init();

        getPresenter().getTicketDetail(id, SharedPref.getAuthToken(this));
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ticket");
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, keyPresenter);
    }

    @NonNull @Override public TicketDetailPresenter getPresenter() {
        return TicketDetailPresenter.getPresenter(keyPresenter);
    }

    @Override public String getID() {
        return keyPresenter;
    }

    @Subscribe public void onMessageReceived(TicketDetailMessage message) {
        switch (message.getId()) {
            case TicketDetailMessage.SHOW_PROGERSS:
                progressdialog = new ProgressDialog(this);
                progressdialog.setMessage("Please Wait....");
                progressdialog.setCancelable(false);
                progressdialog.show();
                break;
            case TicketDetailMessage.HIDE_PROGERSS:
                progressdialog.dismiss();
                break;
            case TicketDetailMessage.DATA_LOADED_SUCCESS:
                setTicketDetails(message.getTicket());
                break;
            case TicketDetailMessage.DATA_LOADED_FAIL:
                dataLoadFail(message.getMessage());
                finish();
                break;
        }
    }

    private void setTicketDetails(BeanTicket ticket) {
        type.setText(ticket.getType());
        subject.setText(ticket.getSubject());
        description.setText(ticket.getDescription());
        tvRequester.setText(ticket.getRequester().getFirstname() + " " + ticket.getRequester().getLastname());
        tvSubmitter.setText(ticket.getSubmitter().getFirstname() + " " + ticket.getSubmitter().getLastname());
        tvAssignee.setText(ticket.getAssignee().getFirstname() + " " + ticket.getAssignee().getLastname());

        Glide.with(this).load(ticket.getRequester().getAvatar()).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder)).into(ivRequester);
        Glide.with(this).load(ticket.getSubmitter().getAvatar()).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder)).into(ivSubmitter);
        Glide.with(this).load(ticket.getAssignee().getAvatar()).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder)).into(ivAssignee);
    }

    public void dataLoadFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
