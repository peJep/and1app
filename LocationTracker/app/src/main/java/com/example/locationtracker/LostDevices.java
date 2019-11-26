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

public class LostDevices extends AppCompatActivity {


    private Model model;

    private Button foundDevice;

    RecyclerView myDevicesList;
    DeviceAdapter myDevicesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_devices);
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
        //found device button
        //set clicklistener
        foundDevice = findViewById(R.id.button7);
        foundDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mark selected items as found
                ArrayList<Device> devicesFound = myDevicesAdaptor.getSelectedItems();
                for (Device i : devicesFound
                ) {
                    i.setDeviceLost(false);
                    //add to logged in users devices
                    model.getUserDevices().add(i);
                    //delete from lost devices
                    model.getLostDevices().remove(i);
                }
                //redraw RecyclerView as needed to reflect changes.
                updateRecyclerView();
            }
        });

    }

    //Shared abstractions  / helper methods
    private void updateRecyclerView() {
        if (model.getAvailableDevices() != null) {
            ArrayList<Device> currentUsersLostDevices = model.getLostDevices();
            //add devices to RecyclerView adaptor
            myDevicesAdaptor = new DeviceAdapter(currentUsersLostDevices);
            myDevicesList.setAdapter(myDevicesAdaptor);
        }
    }

}
