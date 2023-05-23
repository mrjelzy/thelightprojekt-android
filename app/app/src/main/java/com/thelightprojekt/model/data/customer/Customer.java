package com.thelightprojekt.model.data.customer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "customer")
public class Customer {

    @Attribute(name = "id")
    private String id;

    @Attribute(name = "href")
    private String href;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
