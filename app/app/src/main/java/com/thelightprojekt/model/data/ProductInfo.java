package com.thelightprojekt.model.data;

import com.google.gson.annotations.SerializedName;

public class ProductInfo {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
