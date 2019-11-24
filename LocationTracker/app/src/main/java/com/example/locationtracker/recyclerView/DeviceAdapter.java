package com.example.locationtracker.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationtracker.R;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<Device> deviceListAdapter;

    //Constructor
    public DeviceAdapter(ArrayList<Device> devicesList){
        deviceListAdapter = devicesList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_device_adapter, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.deviceName.setText(deviceListAdapter.get(position).getDeviceName());
        viewHolder.deviceType.setText(deviceListAdapter.get(position).getDeviceType());
        viewHolder.distanceToDevice.setText(Integer.toString(deviceListAdapter.get(position).getDistanceToDevice()));
    }

    public int getItemCount() {
        return deviceListAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        TextView deviceType;
        TextView distanceToDevice;
        ViewHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            deviceType = itemView.findViewById(R.id.device_type_icon);
            distanceToDevice = itemView.findViewById(R.id.distance_to_device);
        }

    }

}