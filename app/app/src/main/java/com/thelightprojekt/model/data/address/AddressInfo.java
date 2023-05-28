package com.thelightprojekt.model.data.address;

import com.thelightprojekt.model.data.Link;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "address")
public class AddressInfo {
    @Element(name = "id", required = false)
    private String id;

    @Element(name = "id_customer")
    private Link idCustomer;

    @Element(name = "id_country")
    private Link idCountry;

    @Element(name = "lastname")
    private String lastname;

    @Element(name = "firstname")
    private String firstname;

    @Element(name = "address1")
    private String address1;

    @Element(name = "postcode", required = false)
    private String postcode;

    @Element(name = "city")
    private String city;

    @Element(name = "other", required = false)
    private String other;

    @Element(name = "phone_mobile", required = false)
    private String phoneMobile;

    @Element(name = "alias")
    private String alias;

    private String countryName;

    public AddressInfo(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public Link getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Link idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Link getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Link idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
