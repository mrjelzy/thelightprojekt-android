package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.interfaces.IOrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private IOrderService orderService;

    public OrderRepository() {
        orderService = HttpClientInstance.getRetrofitXml().create(IOrderService.class);
    }

    public LiveData<OrderList> getOrderListByIdCustomer(String id){
        final MutableLiveData<OrderList> data = new MutableLiveData<OrderList>();
        orderService.getOrderListByIdCustomer(id).enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<OrderResponse> getOrderById(String id){
        final MutableLiveData<OrderResponse> data = new MutableLiveData<OrderResponse>();
        String display = "[id, total_paid_real, id_address_delivery, id_currency,id_carrier,delivery_number,shipping_number,current_state,reference,date_add,invoice_date,invoice_number,order_rows[id,product_id,product_quantity,product_price]]";
        orderService.getOrderById(id, display).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }


    public LiveData<OrderState> getOrderStateById(String id){
        final MutableLiveData<OrderState> data = new MutableLiveData<OrderState>();
        String display = "[name]";
        orderService.getOrderStateById(id, display).enqueue(new Callback<OrderState>() {
            @Override
            public void onResponse(Call<OrderState> call, Response<OrderState> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderState> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }
}
