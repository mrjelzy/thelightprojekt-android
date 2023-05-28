package com.thelightprojekt.model.data.prescription;

import com.thelightprojekt.model.data.order.OrderInfo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Prestashop")
public class PrescriptionResponse {

    @Element(name = "prescription")
    PrescriptionInfo prescription;

    public PrescriptionInfo getPrescription() {
        return prescription;
    }

    public void setOrder(PrescriptionInfo prescription) {
        this.prescription = prescription;
    }

    public PrescriptionResponse(){
        prescription = new PrescriptionInfo();
    }
}
