package com.example.societyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    com.google.android.material.button.MaterialButton loginButton;
    TextInputEditText useridTIET;
    TextInputEditText passwordTIET;
    Resident currentResident;
    RadioGroup userRadioGroup;
    com.google.android.material.radiobutton.MaterialRadioButton radioButton;
    String userType,userId,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Branch Vid

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        userType = "resident";
        loginButton = (com.google.android.material.button.MaterialButton) findViewById(R.id.loginButton);

        useridTIET = (TextInputEditText)findViewById(R.id.userIdTIET);
        passwordTIET = (TextInputEditText)findViewById(R.id.passwordTIET);
        userRadioGroup = (RadioGroup)findViewById(R.id.userRadioGroup);
    }

    public void checkHandler(View view){
        int id = userRadioGroup.getCheckedRadioButtonId();
        com.google.android.material.radiobutton.MaterialRadioButton radioButton = (com.google.android.material.radiobutton.MaterialRadioButton)findViewById(id);
        Log.i("Radio",String.valueOf(id));
        userType = radioButton.getTag().toString();
    }

    public void loginHandler(View view){

        userId = useridTIET.getText().toString();
        password = passwordTIET.getText().toString();
        if(userId.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Empty fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("User Type",userType);
            if(userType.equals("resident")){
                residentLogin();
            }
            else {
                guardLogin();
            }
        }
    }

    public void residentLogin(){
        ref = FirebaseDatabase.getInstance().getReference("residents").child(useridTIET.getText().toString());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

                        intent.putExtra("currentResident",currentResident);
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

    public void guardLogin(){
        ref = FirebaseDatabase.getInstance().getReference("guards").child(useridTIET.getText().toString());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Toast.makeText(MainActivity.this, "Invalid UserId!", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap<String,Object>guard = (HashMap<String, Object>) dataSnapshot.getValue();
                    String rightPassword = guard.get("password").toString();
                    if(rightPassword.equals(passwordTIET.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(),NavigationActivity2.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }
                    else{
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
