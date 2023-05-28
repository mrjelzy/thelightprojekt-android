package com.thelightprojekt.model.data.prescription;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "prescription")
public class PrescriptionInfo {
    @Element(name = "id")
    private String id;

    @Element(name = "id_prescription")
    private String idPrescription;

    @Element(name = "id_customer")
    private String idCustomer;

    @Element(name = "id_attachment")
    private String idAttachment;

    @Element(name = "sphere_left")
    private String sphereLeft;

    @Element(name = "sphere_right")
    private String sphereRight;

    @Element(name = "cylinder_left")
    private String cylinderLeft;

    @Element(name = "cylinder_right")
    private String cylinderRight;

    @Element(name = "axis_left")
    private String axisLeft;

    @Element(name = "axis_right")
    private String axisRight;

    @Element(name = "pd_left")
    private String pdLeft;

    @Element(name = "pd_right")
    private String pdRight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(String idPrescription) {
        this.idPrescription = idPrescription;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(String idAttachment) {
        this.idAttachment = idAttachment;
    }

    public String getSphereLeft() {
        return sphereLeft;
    }

    public void setSphereLeft(String sphereLeft) {
        this.sphereLeft = sphereLeft;
    }

    public String getSphereRight() {
        return sphereRight;
    }

    public void setSphereRight(String sphereRight) {
        this.sphereRight = sphereRight;
    }

    public String getCylinderLeft() {
        return cylinderLeft;
    }

    public void setCylinderLeft(String cylinderLeft) {
        this.cylinderLeft = cylinderLeft;
    }

    public String getCylinderRight() {
        return cylinderRight;
    }

    public void setCylinderRight(String cylinderRight) {
        this.cylinderRight = cylinderRight;
    }

    public String getAxisLeft() {
        return axisLeft;
    }

    public void setAxisLeft(String axisLeft) {
        this.axisLeft = axisLeft;
    }

    public String getAxisRight() {
        return axisRight;
    }

    public void setAxisRight(String axisRight) {
        this.axisRight = axisRight;
    }

    public String getPdLeft() {
        return pdLeft;
    }

    public void setPdLeft(String pdLeft) {
        this.pdLeft = pdLeft;
    }

    public String getPdRight() {
        return pdRight;
    }

    public void setPdRight(String pdRight) {
        this.pdRight = pdRight;
    }
}
