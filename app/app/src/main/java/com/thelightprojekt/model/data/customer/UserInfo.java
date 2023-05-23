package com.thelightprojekt.model.data.customer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "customer")
public class UserInfo {
    @Element(name = "id")
    private String id;

    @Element(name = "firstname")
    private String firstname;

    @Element(name = "lastname")
    private String lastname;

    @Element(name = "passwd")
    private String passwd;

    @Element(name = "email")
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
