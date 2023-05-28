package com.thelightprojekt.model.data.prescription;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "prescription")
public class PrescriptionItem {
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
