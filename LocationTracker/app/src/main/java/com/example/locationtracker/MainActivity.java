package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.model.Model;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get model
        //should eventually be instantiated after user login is verified (lazy instantiation) and user data is retrieved from webservice and stored in local model
        model = Model.getInstance();

        loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyDevices.class);
                //put the model to the intent. //All model classes must implement Serializable.
                //Since that I use a singleton as model now I do not need to pass the reference in Intent Bundle.
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });


    }


}
