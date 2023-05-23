package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "order")
public class OrderItem {
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "href")
    private String href;

    public int getId() {
        return id;
    }

    public String getHref() {
        return href;
    }
}
