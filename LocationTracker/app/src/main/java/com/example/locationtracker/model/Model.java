package com.example.locationtracker.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    //Singleton field
    private static Model model;
    //getInstance
    public static Model getInstance()
    {
        if(model==null)
            model = new Model();
        return model;
    }

    //fields

    private ArrayList<Device> devices;

    //stores response from webservice to put in RecyclerView.
    private ArrayList<Device> lostDevices;

    //constructor
    private Model(){
        devices=generateAvailableDeviceArrayList();
        lostDevices= generateLostDeviceArrayList();
    }

    public ArrayList<Device> generateLostDeviceArrayList() {

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Device("Weedle", "Bluetooh-small", 0));
        devices.add(new Device("Beedrill", "Large-gps", 0));
        devices.add(new Device("Kakuna", "Brick", 0));
        devices.add(new Device("Pidgey", "Brick", 0));

        //set devices as lost
        for (Device i: devices
        ) {
            i.setDeviceLost(true);
            //Set last know coordinate from original owners phone at the time when lost
            i.setLastKnownLocation("N"+(int)(Math.random()*90)+"V"+(int)(Math.random()*90));
        }

        return devices;
    }

    //constructor helper methods

    public ArrayList<Device> generateAvailableDeviceArrayList() {
        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Device("Bulbasaur", "Bluetooh-small", randomStartLocation()));
        devices.add(new Device("Ivysaur", "Brick", randomStartLocation()));
        devices.add(new Device("Venusaur", "Large-gps", randomStartLocation()));
        devices.add(new Device("Charmander", "cake", randomStartLocation()));
        devices.add(new Device("Charmeleon", "pie", randomStartLocation()));
        devices.add(new Device("Charizard", "Bluetooh-small", randomStartLocation()));
        devices.add(new Device("Squirtle", "Bluetooh-small", randomStartLocation()));
        devices.add(new Device("Wartortle", "cake", randomStartLocation()));
        devices.add(new Device("Blastoise", "pie", randomStartLocation()));
        devices.add(new Device("Caterpie", "Large-gps", randomStartLocation()));
        devices.add(new Device("Metapod", "Brick", randomStartLocation()));
        devices.add(new Device("Butterfree", "Large-gps", randomStartLocation()));
        //add more copy items
        int extraItemsToAdd = 0;
        int arraySizeBefore = devices.size();
        for (int i = 0; i < extraItemsToAdd; i++) {
            devices.add(devices.get((int) (arraySizeBefore * Math.random() - 0.0001)).copy());
        }
        //set some devices as belonging to current user
        int numberOfDevicesToAdd = 7;
        String userName ="currentUser";
        for (int i = 0; i < numberOfDevicesToAdd;i++){
            int markDeviceNumber = (int)(Math.random()*devices.size()-0.0001);
            //already marked as belonging to current user
            if(devices.get(markDeviceNumber).getUser() != null
                    && userName.equals(devices.get(markDeviceNumber).getUser()))
                i--;
            else
                devices.get(markDeviceNumber).setUser(userName);
        }
        //return device ArrayList
        return devices;
    }
    public int randomStartLocation() {
        int maxWithinRange = 60;
        double maxPossibleDistanceModifier = 2.0;
        return (int) (Math.random() * maxPossibleDistanceModifier * maxWithinRange);
    }

    //setters and getters

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public ArrayList<Device> getLostDevices() {
        return lostDevices;
    }

    public void setLostDevices(ArrayList<Device> lostDevices) {
        this.lostDevices = lostDevices;
    }
}
