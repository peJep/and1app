package com.example.locationtracker.model;

import java.io.Serializable;

public class Device implements Serializable {

    private String deviceName;
    private String deviceType;
    private int distanceToDevice;
    private String user;
    private Boolean deviceLost;
    private String lastKnownLocation;

    public Device(){}

    public Device(String deviceName, String deviceType, int distanceToDevice) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.distanceToDevice = distanceToDevice;
        user=null;
        deviceLost=false;
        lastKnownLocation="";
    }

    public Device copy() {
        Device temp = new Device();
        temp.setDeviceName(deviceName);
        temp.setDeviceType(deviceType);
        temp.setDistanceToDevice(distanceToDevice);
        temp.setUser(user);
        temp.setDeviceLost(deviceLost);
        temp.setLastKnownLocation(lastKnownLocation);
        return temp;
    }

    public void setDeviceAsLost(String lastKnownLocation){
        deviceLost=true;
        this.lastKnownLocation=lastKnownLocation;
    }

    public void setDeviceAsFound(String lastKnownLocation){
        deviceLost=false;
        this.lastKnownLocation="";
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getDistanceToDevice() {
        return distanceToDevice;
    }

    public void setDistanceToDevice(int distanceToDevice) {
        this.distanceToDevice = distanceToDevice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getDeviceLost() {
        return deviceLost;
    }

    public void setDeviceLost(Boolean deviceLost) {
        this.deviceLost = deviceLost;
    }

    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
}
