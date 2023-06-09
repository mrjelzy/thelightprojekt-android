package com.thelightprojekt.model.data.product;

import com.google.gson.annotations.SerializedName;
import com.thelightprojekt.model.data.SubSimpleAssociation;

import java.util.ArrayList;

public class ProductList {
    @SerializedName("products")
    private ArrayList<SubSimpleAssociation> products;

    public ArrayList<SubSimpleAssociation> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<SubSimpleAssociation> products) {
        this.products = products;
    }

    public int size(){
        return  products.size();
    }
}
