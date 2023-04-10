package com.thelightprojekt.model.interfaces;


import com.thelightprojekt.model.data.ProductInfo;
import com.thelightprojekt.model.data.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IProductService {
    @GET("/api/products/{id}")
    Call<ProductResponse> getProductById(
            @Path("id") String id
    );
}
