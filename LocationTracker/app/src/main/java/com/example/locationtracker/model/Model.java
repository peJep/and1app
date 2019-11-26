package com.example.locationtracker.model;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.locationtracker.MainActivity;
import com.example.locationtracker.webApi.PokemonApi;
import com.example.locationtracker.webApi.PokemonResponse;
import com.example.locationtracker.webApi.ServiceGenerator;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    //stores response from bluetooth

    private ArrayList<Device> availableDevices;

    //stores response from webservice to put in RecyclerView.

    private ArrayList<Device> userDevices;

    private ArrayList<Device> lostDevices;

    //constructor
    private Model(){


        //should be retrieved from bluetooth and separate from users devises (separate ArrayList should be made eventually) retrieved by webservice on login time
        availableDevices =generateAvailableDeviceArrayList();

        //a reference to objects could be fetched from webservice instead comparing to object references in local storage SQLite then only retrieving object not already in local storage for reducing network traffic on webservice load
        //should be retrieved by webservice on login time

        //current users devices not list. When adding available devices incoming from bluetooth then devices already on users list is filtered before put into availableDevices
        userDevices = generateUserDeviceArrayLIst();

        //only for currentUser to deselect availableDevices from personal lost list.
        lostDevices= generateLostDeviceArrayList(); //App the app encounters available availableDevices from the phones bluetooth it should automatically check with webservice if another users reported these availableDevices as lost and if the send new last known gps location to webservice. Then other user is prompted about new last know location automatically uploaded from other users app of current users lost availableDevices on login time or when accessing lost availableDevices sections of current users app.



    }

    public ArrayList<Device> generateLostDeviceArrayList() {

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Device("Weedle", "Bluetooh-small", 0));
        devices.add(new Device("Beedrill", "Large-gps", 0));
        devices.add(new Device("Kakuna", "Brick", 0));
        devices.add(new Device("Pidgey", "Brick", 0));

        //set availableDevices as lost and belonging to currentUser
        for (Device i: devices
        ) {
            i.setDeviceLost(true);
            //Set last know coordinate from original owners phone at the time when lost
            i.setLastKnownLocation("N"+(int)(Math.random()*90)+"V"+(int)(Math.random()*90));
            //set belonging to current user
            i.setUser("currentUser");
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

        //add more copy items
        int extraItemsToAdd = 0;
        int arraySizeBefore = devices.size();
        for (int i = 0; i < extraItemsToAdd; i++) {
            devices.add(devices.get((int) (arraySizeBefore * Math.random() - 0.0001)).copy());
        }

        //return device ArrayList
        return devices;
    }
    public int randomStartLocation() {
        int maxWithinRange = 60;
        double maxPossibleDistanceModifier = 2.0;
        return (int) (Math.random() * maxPossibleDistanceModifier * maxWithinRange);
    }

    public ArrayList<Device> generateUserDeviceArrayLIst(){

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Device("Charizard", "Bluetooh-small", randomStartLocation()));
        devices.add(new Device("Squirtle", "Bluetooh-small", randomStartLocation()));
        devices.add(new Device("Wartortle", "cake", randomStartLocation()));
        devices.add(new Device("Blastoise", "pie", randomStartLocation()));
        devices.add(new Device("Caterpie", "Large-gps", randomStartLocation()));
        devices.add(new Device("Metapod", "Brick", randomStartLocation()));
        devices.add(new Device("Butterfree", "Large-gps", randomStartLocation()));

        //set as belonging to current user
        for (Device i: devices
             ) {
            i.setUser("currentUser");
        }

        return devices;
    }

    //webservice - used to check of devices added from bluetooth match devices on lost devices lst from webservice
    public void checkIfLostDeviceOnWebService(final Device device) {
        PokemonApi pokemonApi = ServiceGenerator.getPokemonApi();
        Call<PokemonResponse> call = pokemonApi.getPokemon(device.getDeviceName());
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.code() == 200) {
                        //device found on webservice
                        //how do I get the response.body() data out of this inner class?
                }
            }
            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    //setters and getters

    public ArrayList<Device> getAvailableDevices() {
        return availableDevices;
    }

    public void setAvailableDevices(ArrayList<Device> devices) {
        this.availableDevices = devices;
    }

    public ArrayList<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(ArrayList<Device> userDevices) {
        this.userDevices = userDevices;
    }

    public ArrayList<Device> getLostDevices() {
        return lostDevices;
    }

    public void setLostDevices(ArrayList<Device> lostDevices) {
        this.lostDevices = lostDevices;
    }
}
