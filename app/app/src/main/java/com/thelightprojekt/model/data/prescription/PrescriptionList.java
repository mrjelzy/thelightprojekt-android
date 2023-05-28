package com.thelightprojekt.model.data.prescription;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
@Root(name = "prestashop", strict = false)
public class PrescriptionList {
    @ElementList(name = "prescriptions")
    private List<PrescriptionItem> items;

    public List<PrescriptionItem> getPrescritpions() {
        return items;
    }
}
