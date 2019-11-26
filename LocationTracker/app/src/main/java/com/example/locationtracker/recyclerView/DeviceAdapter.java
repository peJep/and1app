package com.example.locationtracker.recyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.R;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<Device> devicelist;

    private ArrayList<Device> selectedItems;

    public ArrayList<Device> getSelectedItems() {
        return selectedItems;
    }

    //Constructor
    public DeviceAdapter(ArrayList<Device> devicesList) {
        devicelist = devicesList;
        selectedItems = new ArrayList<>();
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_device_adapter, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Device currentItem = devicelist.get(position);

        viewHolder.deviceName.setText(devicelist.get(position).getDeviceName());
        viewHolder.deviceType.setText(devicelist.get(position).getDeviceType());


        //If devise is lost this field holds last known position
        if (devicelist.get(position).getDeviceLost()==true)
            viewHolder.distanceToDevice.setText(devicelist.get(position).getLastKnownLocation());
        // else it hold the distance to current phone position (from phone GPS)
        else
            viewHolder.distanceToDevice.setText(Integer.toString(devicelist.get(position).getDistanceToDevice()));

        //when an element from the RecyclerView list is clicked
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if ViewHolder not already selected then select
                if (viewHolder.selected == false) {

                    selectedItems.add(currentItem);
                    viewHolder.selected=true;
                    viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                //else ViewHolder already selected then de-select
                else{
                    selectedItems.remove(currentItem);
                    viewHolder.selected=false;
                    viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                }
            }
        });
    }

    public int getItemCount() {
        return devicelist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        TextView deviceType;
        TextView distanceToDevice;
        LinearLayout linearLayout;
        boolean selected;

        ViewHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            deviceType = itemView.findViewById(R.id.device_type_icon);
            distanceToDevice = itemView.findViewById(R.id.distance_to_device);
            linearLayout = itemView.findViewById(R.id.container);
            //the ViewHolder is initially deselected.
            selected = false;
        }

    }

}