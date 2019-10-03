package com.example.societyapp;

public class Visitor {

    String name,contactNumber,vehicleNumber,reasonOfVisit,guardId,image;

    public Visitor(String name, String contactNumber, String vehicleNumber, String reasonOfVisit, String guardId, String image) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.vehicleNumber = vehicleNumber;
        this.reasonOfVisit = reasonOfVisit;
        this.guardId = guardId;
        this.image = image;
    }
}
