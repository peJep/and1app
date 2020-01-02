package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

public class MyApplicationContext extends Application {

    private static Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate();
        MyApplicationContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplicationContext.context;
    }

}
