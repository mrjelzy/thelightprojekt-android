package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.repositories.OrderRepository;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        orderRepository = new OrderRepository();
    }

    public LiveData<OrderList> getOrderListByIdCustomer(String id){
        return orderRepository.getOrderListByIdCustomer(id);
    }

    public LiveData<OrderResponse> getOrderById(String id, LifecycleOwner lifecycleOwner){
        MutableLiveData<OrderResponse> order = new MutableLiveData<OrderResponse>();
        orderRepository.getOrderById(id).observe(lifecycleOwner, new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
                orderRepository.getOrderStateById(orderResponse.getOrder().getCurrentState()).observe(lifecycleOwner, new Observer<OrderState>() {
                    @Override
                    public void onChanged(OrderState orderState) {
                            orderResponse.getOrder().setState(orderState.getOrderState().getName().getLanguage().getValue());
                            order.setValue(orderResponse);
                    }
                });
            }
        });
        return order;
    }
}
