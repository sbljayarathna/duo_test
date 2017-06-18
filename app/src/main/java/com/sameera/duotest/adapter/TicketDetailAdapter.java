package com.sameera.duotest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sameera.duotest.R;
import com.sameera.duotest.dto.BeanTicketDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sameera on 6/17/17.
 */

public class TicketDetailAdapter extends RecyclerView.Adapter<TicketDetailAdapter.MyViewHolder> {

    private List<BeanTicketDetail> tickets;
    private OnClickListener clickListener;

    public TicketDetailAdapter(OnClickListener clickListener, List<BeanTicketDetail> storeItems) {
        this.clickListener = clickListener;
        this.tickets = storeItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_ticket_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BeanTicketDetail ticket = tickets.get(position);
        holder.subject.setText(ticket.getSubject());
        holder.type.setText(ticket.getType());
        holder.priority.setText(ticket.getPriority());
        holder.status.setText(ticket.getStatus());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_subject) TextView subject;
        @BindView(R.id.tv_type) TextView type;
        @BindView(R.id.tv_priority) TextView priority;
        @BindView(R.id.tv_status) TextView status;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onClicked(tickets.get(position));
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClicked(BeanTicketDetail ticket);
    }
}