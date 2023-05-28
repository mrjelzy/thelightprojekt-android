package com.thelightprojekt.model.data.customer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Prestashop")
public class UserResponse {
    @Element(name = "customer")
    private UserInfo user;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserResponse() {
        this.user = new UserInfo();
    }
}
