package com.example.mohit.messagebox;

/**
 * Created by Mohit on 23-11-2017.
 */

public class UserInformation {

    public  String name ;
    public String address ;
    public UserInformation()
    {

    }

    public UserInformation(String name , String address)
    {
        this.name = name;
        this.address= address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
