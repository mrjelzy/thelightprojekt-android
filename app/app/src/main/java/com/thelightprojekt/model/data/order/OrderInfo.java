package com.thelightprojekt.model.data.order;

import com.thelightprojekt.model.data.Link;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "order")
public class OrderInfo {
    @Element(name = "id")
    private String id;

    @Element(name = "id_address_delivery")
    private Link idAddressDelivery;

    @Element(name = "id_currency")
    private Link idCurrency;

    @Element(name = "id_carrier")
    private Link idCarrier;

    @Element(name = "invoice_date")
    private String invoiceDate;

    @Element(name = "date_add")
    private String dateAdd;

    @Element(name = "current_state")
    private String currentState;

    @Element(name = "invoice_number")
    private String invoiceNumber;

    @Element(name = "reference")
    private String reference;

    @Element(name = "delivery_number")
    private String deliveryNumber;

    @Element(name = "shipping_number", required = false)
    private String shippingNumber;

    @Element(name = "total_paid_real")
    private String totalPaidReal;

    @Element(name = "associations",  required = false)
    private Associations associations;

    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getIdAddressDelivery() {
        return idAddressDelivery;
    }

    public void setIdAddressDelivery(Link idAddressDelivery) {
        this.idAddressDelivery = idAddressDelivery;
    }

    public Link getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Link idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Link getIdCarrier() {
        return idCarrier;
    }

    public void setIdCarrier(Link idCarrier) {
        this.idCarrier = idCarrier;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getShippingNumber() {
        return shippingNumber;
    }

    public void setShippingNumber(String shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    public String getTotalPaidReal() {
        return totalPaidReal;
    }

    public void setTotalPaidReal(String totalPaidReal) {
        this.totalPaidReal = totalPaidReal;
    }

    public Associations getAssociations() {
        return associations;
    }

    public void setAssociations(Associations associations) {
        this.associations = associations;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
