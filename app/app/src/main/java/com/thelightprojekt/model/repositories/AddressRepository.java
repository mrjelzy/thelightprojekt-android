package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.address.AddressList;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.address.CountryResponse;
import com.thelightprojekt.model.data.customer.UserResponse;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.model.data.order.OrderState;
import com.thelightprojekt.model.interfaces.IAddressService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {
    private IAddressService addressService;

    public AddressRepository() {
        addressService = HttpClientInstance.getRetrofitXml().create(IAddressService.class);
    }

    public LiveData<AddressList> getAddressListByIdCustomer(String id){
        final MutableLiveData<AddressList> data = new MutableLiveData<AddressList>();
        addressService.getAddressListByIdCustomer(id).enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, Response<AddressList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<AddressList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<AddressResponse> getAddressById(String id){
        final MutableLiveData<AddressResponse> data = new MutableLiveData<AddressResponse>();
        String display = "[id,id_customer,id_country,alias,lastname,firstname,address1,postcode,city,other,phone_mobile]";
        addressService.getAddressById(id, display).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<CountryResponse> getCountryById(String id){
        final MutableLiveData<CountryResponse> data = new MutableLiveData<CountryResponse>();
        String display = "[name]";
        addressService.getCountryById(id, display).enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Boolean> createAddress(AddressResponse address){
        final MutableLiveData<Boolean> data = new MutableLiveData<Boolean>();
        addressService.createAddress(address).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    data.setValue(true);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
                data.setValue(false);
            }
        });
        return data;
    }
}
