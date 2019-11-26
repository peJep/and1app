package com.example.locationtracker.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locationtracker.model.Device;

import java.util.ArrayList;

public class DeviceListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Device>> devices;

    public DeviceListViewModel(){
        devices = new MutableLiveData<>();
        devices.setValue(new ArrayList<Device>());
    }

    public LiveData<ArrayList<Device>> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices.setValue(devices);
    }
}
