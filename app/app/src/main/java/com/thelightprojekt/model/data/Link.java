package com.thelightprojekt.model.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

public class Link {
    @Attribute(name = "href", required = false)
    private String href;

    @Text
    private String value;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
