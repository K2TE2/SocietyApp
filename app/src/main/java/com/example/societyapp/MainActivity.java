package com.example.societyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    com.google.android.material.button.MaterialButton loginButton;
    TextInputEditText useridTIET;
    TextInputEditText passwordTIET;
    Resident currentResident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (com.google.android.material.button.MaterialButton) findViewById(R.id.loginButton);

        useridTIET = (TextInputEditText)findViewById(R.id.userIdTIET);
        passwordTIET = (TextInputEditText)findViewById(R.id.passwordTIET);
    }

    public void loginHandler(View view){
//        Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
//        startActivity(intent);
        final String userId = useridTIET.getText().toString();
        final String password = passwordTIET.getText().toString();
        if(userId.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Empty fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            ref = FirebaseDatabase.getInstance().getReference("residents").child(useridTIET.getText().toString());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()){
                        Toast.makeText(MainActivity.this, "Invalid UserID!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        HashMap<String, Object> resident = (HashMap<String, Object>) dataSnapshot.getValue();
                        String rightPassword = resident.get("password").toString();
                        if (rightPassword.equals(password)) {
                            String building = resident.get("building").toString();
                            int floor = Integer.parseInt(resident.get("floor").toString());
                            int flat = Integer.parseInt(resident.get("flat").toString());
                            String name = resident.get("name").toString();
                            long contactNo = Long.parseLong(resident.get("contactNo").toString());
                            String email = resident.get("email").toString();
                            currentResident = new Resident(userId, password, building, floor, flat, name, contactNo, email);
                            Log.i("Loggedin","yes");

                            Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
