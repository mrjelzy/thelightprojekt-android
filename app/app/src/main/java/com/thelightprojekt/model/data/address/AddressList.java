package com.thelightprojekt.model.data.address;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "prestashop")
public class AddressList {
    @ElementList(name = "addresses")
    private List<AddressItem> addresses;

    public List<AddressItem> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressItem> addresses) {
        this.addresses = addresses;
    }

}
