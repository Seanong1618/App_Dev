package com.example.hanapp;

public class customer_details {
    private final String name;
    private final String contact;
    private final String temperature;
    private final String address;
    private final String datetime;

    //constructor
    public customer_details(String name, String contact, String temperature, String address, String datetime) {
        this.name = name;
        this.contact = contact;
        this.temperature = temperature;
        this.address = address;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getAddress() {
        return address;
    }

    public String getDatetime() {
        return datetime;
    }
}
