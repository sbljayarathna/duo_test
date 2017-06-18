package com.sameera.duotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.sameera.duotest.R;
import com.sameera.duotest.adapter.TicketDetailAdapter;
import com.sameera.duotest.config.AppConst;
import com.sameera.duotest.dto.BeanTicketDetail;
import com.sameera.duotest.message.TicketsMessage;
import com.sameera.duotest.presenter.TicketsPresenter;
import com.sameera.duotest.util.AppUtil;
import com.sameera.duotest.util.SharedPref;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketsActivity extends BaseActivity<TicketsPresenter> implements TicketDetailAdapter.OnClickListener {

    public static final String KEY = TicketsActivity.class.getSimpleName().toString();
    private String keyPresenter;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int pageNo = 1;
    private TicketDetailAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            keyPresenter = savedInstanceState.getString(KEY);
        }

        if (keyPresenter == null) {
            keyPresenter = UUID.randomUUID().toString();
        }

        init();
        getPresenter().getTicketDetailList(SharedPref.getAuthToken(this), false, false, 1);
    }

    private void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Tickets");

        swipeRefreshLayout.setOnRefreshListener(() -> {
            TicketsActivity.this.getPresenter().getTicketDetailList(SharedPref.getAuthToken(TicketsActivity.this), true, false, 1);
            loading = true;
            pageNo = 1;
        });


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

    @NonNull @Override public TicketsPresenter getPresenter() {
        return TicketsPresenter.getPresenter(keyPresenter);
    }

    @Override public String getID() {
        return keyPresenter;
    }

    private void setUpRecyclerView(List<BeanTicketDetail> data) {
        mAdapter = new TicketDetailAdapter(this, data);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //pagination..
                            getPresenter().getTicketDetailList(SharedPref.getAuthToken(TicketsActivity.this), false, true, ++pageNo);
                        }
                    }
                }
            }
        });
    }

    @Subscribe public void onMessageReceived(TicketsMessage message) {
        switch (message.getId()) {
            case TicketsMessage.SHOW_PROGERSS:
                swipeRefreshLayout.setRefreshing(true);
                break;
            case TicketsMessage.HIDE_PROGERSS:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case TicketsMessage.DATA_LOADED:
                setUpRecyclerView(message.getStoreItems());
                break;
            case TicketsMessage.SHOW_MESSAGE:
                Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
                break;
            case TicketsMessage.DATA_LOAD_ERROR:
                Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TicketsMessage.NOTIFY_PAGINATION:
                mAdapter.notifyDataSetChanged();
                loading = true;
                break;
        }
    }

    @Override public void onClicked(BeanTicketDetail ticket) {
        if (AppUtil.checkNetworkConnection(this)) {
            Intent intent = new Intent(this, TicketDetailActivity.class);
            intent.putExtra(AppConst.EXTRA_TICKET_ID, ticket.getId());
            startActivity(intent);
        } else {
            AppUtil.checkInternetAlert(this, (dialog, which) -> {
                //When click settings
                AppUtil.showSystemSettingsDialog(TicketsActivity.this);
                dialog.dismiss();
            });
        }

    }
}
