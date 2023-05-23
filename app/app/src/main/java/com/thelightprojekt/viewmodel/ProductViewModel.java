package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thelightprojekt.model.data.product.ProductList;
import com.thelightprojekt.model.data.product.ProductResponse;
import com.thelightprojekt.model.repositories.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        productRepository = new ProductRepository();
    }


    public LiveData<ProductResponse> getProductResponseLiveData(int id) {
        return productRepository.searchProduct(id);
    }

    public LiveData<ProductList> getProductList(){
        return productRepository.getProductList();
    }
    
}
