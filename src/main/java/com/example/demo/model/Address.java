package com.example.demo.model;

public class Address {

    public String street;
    public String houseNumber;
    public Address() {
        this.street = "";
        this.houseNumber = "";
    }
    public Address(String street) {
        this.street = street;
        this.houseNumber = "";
    }

    public Address(String street, String houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}