package com.example.societyapp.ui2.profile2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.societyapp.NavigationActivity;
import com.example.societyapp.NavigationActivity2;
import com.example.societyapp.R;
import com.example.societyapp.ui.profile.ChangeProfileDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class GuardProfileFragment extends Fragment {

    private GuardProfileViewModel guardProfileViewModel;
    String address, name, userId;
    long contact, gateno, shift;
    TextView guardName, guardAddress, guardContact, guardShift, guardGate, guardUserId;
    ImageView profilePicture;
    ChangeProfileDialog dialog;
    DatabaseReference ref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        guardProfileViewModel =
                ViewModelProviders.of(this).get(GuardProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guardprofile, container, false);
        root = inflater.inflate(R.layout.fragment_guardprofile, container, false);
        return root;

    }

    ;


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationActivity2 activity = (NavigationActivity2) getActivity();
        Log.i("key", activity.getUserId());
        userId = activity.getUserId();
        guardName = (TextView) view.findViewById(R.id.guardName);
        guardAddress = (TextView) view.findViewById(R.id.guardAddress);
        guardContact = (TextView) view.findViewById(R.id.guardContact);
        guardShift = (TextView) view.findViewById(R.id.guardShift);
        guardGate = (TextView) view.findViewById(R.id.guardGate);
        guardUserId=(TextView) view.findViewById(R.id.guardUserId);

        ref = FirebaseDatabase.getInstance().getReference("guards").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> guard = (HashMap<String, Object>) dataSnapshot.getValue();

                name = guard.get("name").toString();
                address = guard.get("address").toString();
                contact = Long.parseLong(guard.get("contactNo").toString());
                gateno = Long.parseLong(guard.get("gateNo").toString());
                shift = Long.parseLong(guard.get("shift").toString());

                guardUserId.setText("#" + userId);
                guardName.setText("Name:" + name);
                guardContact.setText("Contact:" + contact);
                guardAddress.setText("Address: " + address);
                guardGate.setText("Gate Number: " + gateno);
                guardShift.setText("Shift: " + shift);
            }
        });
    }
}