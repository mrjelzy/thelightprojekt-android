package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Prestashop")
public class OrderResponse {

    @Element(name = "order")
    OrderInfo order;

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
