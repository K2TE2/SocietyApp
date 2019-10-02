package com.example.societyapp.ui2.addVisitor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.societyapp.R;
import com.example.societyapp.VisitorNotification;
import com.example.societyapp.ui.home.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.w3c.dom.Text;

public class AddVisitorFragment extends Fragment {

    private AddVisitorViewModel addVisitorViewModel;
    TextInputEditText visitor_name,visitor_contact_no,visitor_vehicle_no,reason_of_visit,building_name,floor_no,flat_no;
    ImageView visitor_image;
    MaterialButton click_visitor_picture,button_select_flat,button_add_visitor;
    SelectFlatDialog dialog;

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
        building_name = (TextInputEditText)view.findViewById(R.id.building_name);
        floor_no = (TextInputEditText)view.findViewById(R.id.floor_number);
        flat_no = (TextInputEditText)view.findViewById(R.id.flat_number);
        button_add_visitor = view.findViewById(R.id.button_add_visitor);

//        button_select_flat = (MaterialButton)view.findViewById(R.id.button_select_flat);
//        dialog = new SelectFlatDialog();

        click_visitor_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        button_add_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                String building = building_name.getText().toString();
                String floor = floor_no.getText().toString();
                String flat = flat_no.getText().toString();
                String topic = building+floor+flat;
                Log.i("topic in add visitor",topic);
                try {
                    VisitorNotification notification = new VisitorNotification(getContext(),"title","message",topic);
                    notification.sendNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        button_select_flat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
//                FragmentManager manager = getFragmentManager();
//                dialog.show(manager,"Noo");
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        visitor_image.setImageBitmap(bitmap);
    }
}