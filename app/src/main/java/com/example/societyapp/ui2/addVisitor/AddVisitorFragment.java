package com.example.societyapp.ui2.addVisitor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.societyapp.R;
import com.example.societyapp.ui.home.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddVisitorFragment extends Fragment {

    private AddVisitorViewModel addVisitorViewModel;
    TextInputEditText visitor_name,visitor_contact_no,visitor_vehicle_no,reason_of_visit;
    ImageView visitor_image;
    MaterialButton click_visitor_picture;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addVisitorViewModel =
                ViewModelProviders.of(this).get(AddVisitorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_visitor, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        visitor_name = (TextInputEditText)view.findViewById(R.id.visitor_name);
        visitor_contact_no = (TextInputEditText)view.findViewById(R.id.visitor_contact_no);
        visitor_vehicle_no = (TextInputEditText)view.findViewById(R.id.visitor_vehicle_no);
        reason_of_visit = (TextInputEditText)view.findViewById(R.id.reason_of_visit);
        visitor_image = (ImageView)view.findViewById(R.id.visitor_image);
        click_visitor_picture = (MaterialButton)view.findViewById(R.id.click_visitor_picture);

        click_visitor_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        visitor_image.setImageBitmap(bitmap);
    }
}