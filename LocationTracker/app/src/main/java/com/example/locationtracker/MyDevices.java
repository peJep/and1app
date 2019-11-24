package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locationtracker.recyclerView.Device;
import com.example.locationtracker.recyclerView.DeviceAdapter;

import java.util.ArrayList;

public class MyDevices extends AppCompatActivity {

    Button AddDevice;

    RecyclerView myDevicesList;
    RecyclerView.Adapter myDevicesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);

        AddDevice = findViewById(R.id.button2);
        AddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDevices.this, AddDevice.class);
                startActivity(intent);
            }
        });

        myDevicesList = findViewById(R.id.recyclerView);
        myDevicesList.hasFixedSize();
        myDevicesList.setLayoutManager(new LinearLayoutManager(this));

        int maxWithinRange = 60;
        double maxPossibleDistanceModifier = 2.0;
        ArrayList<Device> pokemons = new ArrayList<Device>();
        pokemons.add(new Device("Bulbasaur", "Bluetooh-small",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Ivysaur", "Brick",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Venusaur", "Large-gps",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Charmander", "cake",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Charmeleon","pie",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Charizard", "Bluetooh-small",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Squirtle", "Bluetooh-small",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Wartortle", "cake",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Blastoise", "pie",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Caterpie", "Large-gps",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Metapod", "Brick",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        pokemons.add(new Device("Butterfree", "Large-gps",(int)(Math.random()*maxPossibleDistanceModifier*maxWithinRange)));
        //add more copy items
        int extraItemsToAdd=30;
        int arraySizeBefore=pokemons.size();
        for (int i = 0; i < extraItemsToAdd;i++){
            pokemons.add(pokemons.get((int)(arraySizeBefore*Math.random()-0.0001)));
        }

        myDevicesAdaptor = new DeviceAdapter(pokemons);
        myDevicesList.setAdapter(myDevicesAdaptor);
    }
}
