package com.cinfy.jmvendor.dashboard.bean;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinfy.jmvendor.R;

import java.util.List;

public class PendingOrderAdapter  extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {
    List<PendingOrderData> dataList;
    Context context;

    public PendingOrderAdapter(List<PendingOrderData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;

    }

    @NonNull
    @Override
    public PendingOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.pending_order_list, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PendingOrderData data = dataList.get(position);

        holder.name.setText(data.getName());
        holder.orderid.setText(data.getOrderid());
        holder.address.setText(data.getAddress());
        holder.itemdetail.setText(data.getItemdetail());
        holder.amount.setText(data.getAmount());
        holder.status.setText(data.getStatus());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,orderid,address,itemdetail,amount,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            orderid = itemView.findViewById(R.id.orderid);
           address = itemView.findViewById(R.id.address);
            itemdetail = itemView.findViewById(R.id.itemdetail);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
        }
    }

}
