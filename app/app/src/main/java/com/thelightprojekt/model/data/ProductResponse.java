package com.thelightprojekt.model.data;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("product")
    private ProductInfo productInfo;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}