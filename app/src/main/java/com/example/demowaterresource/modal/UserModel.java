package com.example.demowaterresource.modal;

public class UserModel
{
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String dates;
    private String location;
    private String description;
    private byte[] image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getDate() {
        return dates;
    }

    public void setDate(String location) { this.dates = dates; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) { this.location = location; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() { return image; }

    public void setImage(byte[] image){this.image = image;}



}
