package com.thelightprojekt.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.HttpClientInstance;
import com.thelightprojekt.model.data.ProductList;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.model.interfaces.IProductService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private IProductService productService;

    public ProductRepository() {
        productService = HttpClientInstance.getRetrofit().create(IProductService.class);
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


    public LiveData<ProductList> getProductList(){
        final MutableLiveData<ProductList> data = new MutableLiveData<ProductList>();
        productService.getAllProductsIds().enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                System.out.println("request failed: " + t.getMessage());
            }
        });
        return data;
    }


}
