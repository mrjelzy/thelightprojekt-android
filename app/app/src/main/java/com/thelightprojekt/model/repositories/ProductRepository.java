package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.RetrofitClientInstance;
import com.thelightprojekt.model.data.ProductInfo;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.model.interfaces.IProductService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private IProductService productService;

    public ProductRepository() {
        productService = RetrofitClientInstance.getRetrofit().create(IProductService.class);
    }

    public LiveData<ProductResponse> searchProduct(int id){
        final MutableLiveData<ProductResponse> data = new MutableLiveData<ProductResponse>();
        productService.getProductById(String.valueOf(id))
               .enqueue(new Callback<ProductResponse>(){
                   @Override
                   public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                       if (response.body() != null) {
                           data.setValue(response.body());
                       }
                   }

                   @Override
                   public void onFailure(Call<ProductResponse> call, Throwable t) {
                       data.setValue(null);
                   }
               });
       return data;
    }

}
