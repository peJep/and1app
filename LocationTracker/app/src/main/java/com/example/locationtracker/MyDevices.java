package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.model.Model;
import com.example.locationtracker.recyclerView.DeviceAdapter;

import java.util.ArrayList;

public class MyDevices extends AppCompatActivity {

    //model retrieved from intent.
    //Bundle bundle;
    //Model retrieved from singleton
    Model model;

    Button addDevice;
    Button removeDevice;
    Button reportAsLost;
    Button findDevice;

    RecyclerView myDevicesList;
    DeviceAdapter myDevicesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);


        //retrieve model
        model = Model.getInstance();
        //bundle = getIntent().getExtras();

        //reference views and set functionality

        //reference recyclerView
        myDevicesList = findViewById(R.id.recyclerView);
        myDevicesList.hasFixedSize();
        myDevicesList.setLayoutManager(new LinearLayoutManager(this));

        //fill with elements from model if available.
        //if (bundle != null && bundle.containsKey("availableDevices")) {
        //    //only show devices that belongs to the current user.
        //    ArrayList<Device> availableDevices = (ArrayList<Device>) bundle.get("availableDevices");
        //}
        updateRecyclerView();

        //reference buttons

        //add button
        addDevice = findViewById(R.id.button2);
        //set clicklistener
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDevices.this, AddDevice.class);
                startActivity(intent);
            }
        });

        //remove button
        removeDevice = findViewById(R.id.button3);
        //set clicklistener
        removeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //un-mark user as owner off selected items and
                ArrayList<Device> toRemoveFromUser = myDevicesAdaptor.getSelectedItems();
                for (Device i : toRemoveFromUser
                ) {
                    i.setUser(null);
                    //add to available devices
                    model.getAvailableDevices().add(i);
                    //remove from users devices
                    model.getUserDevices().remove(i);
                }
                //redraw RecyclerView as needed to reflect changes.
                updateRecyclerView();
            }
        });

        //report as lost button
        reportAsLost = findViewById(R.id.button4);
        //set clicklistener
        reportAsLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //un-mark user as owner off selected items and
                ArrayList<Device> toReportAsLost = myDevicesAdaptor.getSelectedItems();
                for (Device i : toReportAsLost
                ) {
                    //Set device as lost. Not needed anymore when separation devices into separate ArrayLists: found, available, currentUsers in Model
                    i.setDeviceLost(true);
                    //get last know location from phones gps last time it was the deveice was in range
                    i.setLastKnownLocation("N"+(int)(Math.random()*90)+"V"+(int)(Math.random()*90));

                    //add to lost devices
                    model.getLostDevices().add(i);
                    //delete from available devices
                    model.getUserDevices().remove(i);
                }
                //redraw RecyclerView as needed to reflect changes.
                updateRecyclerView();
            }
        });

        //find button
        findDevice = findViewById(R.id.button6);
        //set clicllistener
        findDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDevices.this, LostDevices.class);
                startActivity(intent);
            }
        });

    }

    //Shared abstractions  / helper methods
    private void updateRecyclerView() {
        if (model.getAvailableDevices() != null) {
            //get current users devices from model
            ArrayList<Device> currentUsersDevices = model.getUserDevices();
            //add devices to RecyclerView adaptor
            myDevicesAdaptor = new DeviceAdapter(currentUsersDevices);
            myDevicesList.setAdapter(myDevicesAdaptor);
        }
    }

}
