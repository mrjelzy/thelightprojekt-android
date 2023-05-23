package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "prestashop", strict = false)
public class OrderList {
    @ElementList(name = "orders")
    private List<OrderItem> orders;

    public List<OrderItem> getOrders() {
        return orders;
    }
}
