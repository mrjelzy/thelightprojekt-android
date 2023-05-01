package com.thelightprojekt.model.data;

import com.google.gson.annotations.SerializedName;

public class ProductInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("associations")
    private Association associations;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Association getAssociations() {
        return associations;
    }

    public void setAssociations(Association associations) {
        this.associations = associations;
    }
}
