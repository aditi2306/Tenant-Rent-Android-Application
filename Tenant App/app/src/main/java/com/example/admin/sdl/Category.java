package com.example.admin.sdl;

public class Category {
    private String FullName,Email,phoneNo;
    private String Image;
    private String AppartmentName,AppartmentType,BHK;
    private String Rent,Location,streetName;
    Category() {

    }

    public Category(String fullName, String email, String phoneNo, String image, String appartmentName, String appartmentType, String BHK, String rent, String location, String streetName) {
        FullName = fullName;
        Email = email;
        this.phoneNo = phoneNo;
        Image = image;
        AppartmentName = appartmentName;
        AppartmentType = appartmentType;
        this.BHK = BHK;
        Rent = rent;
        Location = location;
        this.streetName = streetName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAppartmentName() {
        return AppartmentName;
    }

    public void setAppartmentName(String appartmentName) {
        AppartmentName = appartmentName;
    }

    public String getAppartmentType() {
        return AppartmentType;
    }

    public void setAppartmentType(String appartmentType) {
        AppartmentType = appartmentType;
    }

    public String getBHK() {
        return BHK;
    }

    public void setBHK(String BHK) {
        this.BHK = BHK;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String rent) {
        Rent = rent;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
