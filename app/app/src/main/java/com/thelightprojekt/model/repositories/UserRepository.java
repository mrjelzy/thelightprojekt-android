package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.customer.CustomerList;
import com.thelightprojekt.model.data.customer.UserResponse;
import com.thelightprojekt.model.interfaces.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private IUserService userService;

    public UserRepository() {
        userService = HttpClientInstance.getRetrofitXml().create(IUserService.class);
    }

    public LiveData<CustomerList> getAllCustomers(){
        final MutableLiveData<CustomerList> data = new MutableLiveData<CustomerList>();
        userService.getAllCustomers().enqueue(new Callback<CustomerList>() {
            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<CustomerList> getCustomerByEmailFilter(String email){
        final MutableLiveData<CustomerList> data = new MutableLiveData<CustomerList>();
        userService.getCustomerByEmailFilter(email).enqueue(new Callback<CustomerList>() {
            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<UserResponse> getUserById(String id){
        final MutableLiveData<UserResponse> data = new MutableLiveData<UserResponse>();
        String display = "[id,passwd,lastname,firstname,email]";
        userService.getUserById(id, display).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }
}
