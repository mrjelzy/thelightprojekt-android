package com.thelightprojekt.model.data.order;

import com.thelightprojekt.model.data.Link;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "order_row")
class OrderRow {
    @Element(name = "id")
    private String id;

    @Element(name = "product_id")
    private Link productId;

    @Element(name = "product_quantity")
    private String productQuantity;

    @Element(name = "product_price")
    private String productPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getProductId() {
        return productId;
    }

    public void setProductId(Link productId) {
        this.productId = productId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
