package com.thelightprojekt.model.data;

import com.google.gson.annotations.SerializedName;

public class SubSimpleAssociation {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
