package com.o4codes.models;

import javafx.scene.image.Image;

public class User {
    // only a single user is allowed into this app

    private String name, mobileNumber, deviceName,devicePassword;
    private Image userImage;

    public User(String name, String mobileNumber, String deviceName, String devicePassword, Image userImage) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.deviceName = deviceName;
        this.devicePassword = devicePassword;
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDevicePassword() {
        return devicePassword;
    }

    public void setDevicePassword(String devicePassword) {
        this.devicePassword = devicePassword;
    }

    public Image getUserImage() {
        return userImage;
    }

    public void setUserImage(Image userImage) {
        this.userImage = userImage;
    }
}
