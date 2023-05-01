package com.thelightprojekt.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thelightprojekt.model.data.ProductInfo;
import com.thelightprojekt.model.data.ProductList;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.model.repositories.ProductRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        productRepository = new ProductRepository();
    }


    public LiveData<ProductResponse> getProductResponseLiveData(int id) {
         LiveData<ProductResponse> data = productRepository.searchProduct(id);
         return data;
    }

    public LiveData<ProductList> getProductList(){
        return productRepository.getProductList();
    }

}
