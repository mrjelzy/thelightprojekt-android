package com.thelightprojekt.model.interfaces;


import com.thelightprojekt.model.data.product.ProductList;
import com.thelightprojekt.model.data.product.ProductResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IProductService {
    @GET("/api/products/{id}")
    Call<ProductResponse> getProductById(
            @Path("id") String id
    );

    @GET("/api/images/products/{id}/{id_image}")
    Call<ResponseBody> fetchProductImage(
            @Path("id") String id,
            @Path("id_image") String id_image
    );

    @GET("/api/products")
    Call<ProductList> getAllProductsIds();
}
