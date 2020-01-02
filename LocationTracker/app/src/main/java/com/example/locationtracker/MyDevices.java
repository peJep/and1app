package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.model.Model;
import com.example.locationtracker.recyclerView.DeviceAdapter;
import com.example.locationtracker.viewModel.DeviceListViewModel;
import com.example.locationtracker.webApi.PokemonResponse;

import java.util.ArrayList;

public class MyDevices extends AppCompatActivity {

    //model retrieved from intent.
    //Bundle bundle;
    //Model retrieved from singleton
    Model model;
    DeviceListViewModel viewModel;

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

        String devicesFoundList ="";
        for (PokemonResponse pokemon: model.getWebServiceResponse()){
            devicesFoundList+=pokemon.getId()+" "+pokemon.getName()+"\n";
        }
        //Check if any found devices were on webservice retrofit 2
        Toast.makeText(this,"Number of lost devices found: "+model.getWebServiceResponse().size()+"\n"+devicesFoundList,Toast.LENGTH_LONG).show();

        //store current logged in users devices in ViewModel from Model
        viewModel = ViewModelProviders.of(this).get(DeviceListViewModel.class);
        viewModel.setDevices(Model.getInstance().getUserDevices());
        //observe changes in ViewModel devices
        viewModel.getDevices().observe(this, new Observer<ArrayList<Device>>() {
            @Override
            public void onChanged(ArrayList<Device> devices) {
                //update RecyclerView when the observed list of devices is changed
                updateRecyclerView();
            }
        });
        //for observer pattern to work MutableLiveData<>.setValue() must be called.
        // Not enough to change data referenced in MutableLiveData<> for observers to be notified.

        //I showcase LiveData observer pattern in removeDevice.setOnClickListener method within this method. Refer to the implementation of removeDevice.setOnClickListener in this activity.

        // Since it is bad practice to share references to ViewModels in between classes then ViewModel is useless as LiveData wrapper for me.
        //Maybe use Model with LiveData wrapped device list both then Model must be View Model and then it should not be shared in between activities since bad practice to share ViewModel in between activities.
        //I already use updateRecyclerView() whenever something is changed within an activity. The problem is when something changes from another activity.
        //better to use updateRecyclerView() when an activity is resumed.
        //like this: Refer to onResume() in this activity.


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

                    //here i use LiveData, just to show that the viewModel observer pattern works
                    //note that i do not call updateRecyclerView() inside this method. Update updateRecyclerView() still happens through Observer.onChanged(ArrayList<Device> devices).
                    viewModel.setDevices(model.getUserDevices());
                }
                //redraw RecyclerView as needed to reflect changes.
                //Use LiveData observer pattern and ViewModel to update RecyclerView instead though Observer.onChanged()
                //updateRecyclerView();
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

    //used to Update RecyclerView if model data was changed by other activities while this activity was paused
    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
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
