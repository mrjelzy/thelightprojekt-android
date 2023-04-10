package com.thelightprojekt.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thelightprojekt.model.data.ProductInfo;
import com.thelightprojekt.model.data.ProductResponse;
import com.thelightprojekt.model.repositories.ProductRepository;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);

        productRepository = new ProductRepository();
    }

    public LiveData<ProductResponse> getProductResponseLiveData(int id) {
        return  productRepository.searchProduct(id);
    }
}
