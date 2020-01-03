package com.example.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.locationtracker.model.Device;
import com.example.locationtracker.model.Model;
import com.example.locationtracker.webApi.PokemonResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

                EditText email = findViewById(R.id.editText);
                EditText password = findViewById(R.id.editText2);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                mAuth.signInWithEmailAndPassword("sds@sds.sds","sds@sds.sds"/*strEmail, strPassword*/)
                        .addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("FirebaseAuth", "signInWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, MyDevices.class);
                                    //put the model to the intent. //All model classes must implement Serializable.
                                    //Since that I use a singleton as model now I do not need to pass the reference in Intent Bundle.
                                    intent.putExtra("model", model);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("FirebaseAuth", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });


    }


}
