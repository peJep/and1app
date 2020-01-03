package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.model.Model;
import com.example.locationtracker.recyclerView.DeviceAdapter;

import java.util.ArrayList;

public class AddDevice extends AppCompatActivity {

    private Model model;

    private Button addDevice;

    RecyclerView myDevicesList;
    DeviceAdapter myDevicesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        //retrieve model
        model = Model.getInstance();
        //bundle = getIntent().getExtras();

        //reference views and set functionality

        //reference recyclerView
        myDevicesList = findViewById(R.id.recyclerView);
        myDevicesList.hasFixedSize();
        myDevicesList.setLayoutManager(new LinearLayoutManager(this));

        //fill with elements from model if available.
        updateRecyclerView();

        //reference buttons
        //add button
        //set clicklistener
        addDevice = findViewById(R.id.button5);
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mark user as owner off selected items
                ArrayList<Device> addToUser = myDevicesAdaptor.getSelectedItems();
                for (Device i : addToUser
                ) {
                    i.setUser("currentUser");

                    //add to users devices
                    model.getUserDevices().add(i);
                    //delete from available devices
                    model.getAvailableDevices().remove(i);

                }
                //redraw RecyclerView as needed to reflect changes.
                updateRecyclerView();

                //re-sync changes to model with Firebase
                Model.saveToFirebase();
            }
        });

    }

    //Shared abstractions  / helper methods
    private void updateRecyclerView() {
        if (model.getAvailableDevices() != null) {
            //get available devices from model
            ArrayList<Device> availableDevices = model.getAvailableDevices();
            //add devices to RecyclerView adaptor
            myDevicesAdaptor = new DeviceAdapter(availableDevices);
            myDevicesList.setAdapter(myDevicesAdaptor);
        }
    }
}
