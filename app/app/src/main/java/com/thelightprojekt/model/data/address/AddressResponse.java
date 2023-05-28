package com.thelightprojekt.model.data.address;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Prestashop")
public class AddressResponse {

    @Element(name = "address")
    private AddressInfo address;

    public  AddressResponse(){
        this.address = new AddressInfo();
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }
}
