package com.example.societyapp;

public class Visitor {

    String name,contactNumber,vehicleNumber,reasonOfVisit,image;

    public Visitor(String name, String contactNumber, String vehicleNumber, String reasonOfVisit, String image) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.vehicleNumber = vehicleNumber;
        this.reasonOfVisit = reasonOfVisit;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getReasonOfVisit() {
        return reasonOfVisit;
    }

    public String getImage() {
        return image;
    }
}
