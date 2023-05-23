package com.thelightprojekt.model.data.product;

import com.google.gson.annotations.SerializedName;
import com.thelightprojekt.model.data.SubSimpleAssociation;

import java.util.ArrayList;

public class Association {

    @SerializedName("categories")
    private ArrayList<SubSimpleAssociation> categories;

    @SerializedName("images")
    private ArrayList<SubSimpleAssociation> images;

    @SerializedName("combinations")
    private ArrayList<SubSimpleAssociation> combinations;

    public ArrayList<SubSimpleAssociation> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<SubSimpleAssociation> categories) {
        this.categories = categories;
    }

    public ArrayList<SubSimpleAssociation> getImages() {
        return images;
    }

    public void setImages(ArrayList<SubSimpleAssociation> images) {
        this.images = images;
    }

    public ArrayList<SubSimpleAssociation> getCombinations() {
        return combinations;
    }

    public void setCombinations(ArrayList<SubSimpleAssociation> combinations) {
        this.combinations = combinations;
    }
}
