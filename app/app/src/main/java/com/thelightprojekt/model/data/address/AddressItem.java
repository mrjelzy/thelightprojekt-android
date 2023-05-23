package com.thelightprojekt.model.data.address;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "address")
public class AddressItem {
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "href", required = false)
    private String href;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
