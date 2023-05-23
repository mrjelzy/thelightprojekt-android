package com.thelightprojekt.model.interfaces;

import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IOrderService {
    @GET("/api/orders")
    Call<OrderList> getOrderListByIdCustomer(
            @Query("filter[id_customer]") String id
    );

    @GET("/api/orders/{id}")
    Call<OrderResponse> getOrderById(
            @Path("id") String id,
            @Query("display") String display
    );

    @GET("/api/order_states/{id}")
    Call<OrderState> getOrderStateById(
            @Path("id") String id,
            @Query("display") String display
    );
}
