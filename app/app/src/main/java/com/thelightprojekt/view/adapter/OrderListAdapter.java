package com.thelightprojekt.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thelightprojekt.R;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.product.ProductResponse;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderResponse> orders;
    Context context;

    public OrderListAdapter(Context context, ArrayList<OrderResponse> orders) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_adapter, parent, false);
        return new OrderListAdapter.OrderListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        OrderResponse order = orders.get(position);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = inputFormat.parse(order.getOrder().getDateAdd());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String outputDateString = outputFormat.format(date);

        double price = Double.parseDouble(order.getOrder().getTotalPaidReal());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedPrice = decimalFormat.format(price);

        holder.orderReference.setText(order.getOrder().getReference());
        holder.orderDate.setText(outputDateString);
        holder.orderStatus.setText(order.getOrder().getState());
        holder.orderPrice.setText(price + " €" );
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

    public void setItems(ArrayList<OrderResponse> orders) {
        this.orders = orders;
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        private TextView orderReference;
        private TextView orderDate;
        private TextView orderStatus;
        private TextView orderPrice;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            orderReference = itemView.findViewById(R.id.adapter_order_reference);
            orderDate = itemView.findViewById(R.id.adapter_order_date);
            orderStatus = itemView.findViewById(R.id.adapter_order_status);
            orderPrice = itemView.findViewById(R.id.adapter_order_price);
        }

    }
}
