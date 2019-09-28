package com.example.societyapp.ui.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.societyapp.R;
import com.example.societyapp.Resident;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ChangeProfileDialog extends DialogFragment {

    EditText newUserName,newEmail,newContactNo;
    Resident currentResident;
    String userId;
    DatabaseReference ref;
    com.google.android.material.button.MaterialButton chooseImageButton;
    Uri mImageUri;
    ImageView newProfileImage;
    Button button2;
    StorageReference mStorageRef;
    DatabaseReference mDbRef;

    private static  final int PICK_IMAGE_REQUEST = 1;
    private static  final int RESULT_OK = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        currentResident = (Resident)getArguments().getSerializable("currentResident");
        userId = currentResident.getUserId();

        mStorageRef = FirebaseStorage.getInstance().getReference(userId);


        View content = inflater.inflate(R.layout.change_profile, null);
        builder.setView(content)
                // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ref = FirebaseDatabase.getInstance().getReference("residents").child(userId);
                        HashMap<String,String>newValues = new HashMap<String, String>();
                        newValues.put("name",newUserName.getText().toString());
                        newValues.put("email",newEmail.getText().toString());
                        newValues.put("contactNo",newContactNo.getText().toString());
                        ref.updateChildren((Map)newValues,null);

                        uploadFile();

                        Toast.makeText(getContext(), "Profile Updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        newUserName = (EditText)content.findViewById(R.id.newUserName);
        newEmail = (EditText)content.findViewById(R.id.newEmail);
        newContactNo = (EditText)content.findViewById(R.id.newContactNo);
        chooseImageButton = (com.google.android.material.button.MaterialButton) content.findViewById(R.id.chooseImageButton);
        newProfileImage = (ImageView)content.findViewById(R.id.newProfileImage);

        ref = FirebaseDatabase.getInstance().getReference("residents/"+userId).child("profilePicture");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Picasso
                            .get()
                            .load(dataSnapshot.getValue().toString())
                            .into(newProfileImage);
                }
                else {
                    newProfileImage.setImageResource(R.drawable.profile_picture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        newUserName.setText(currentResident.getName());
        newEmail.setText(currentResident.getEmail());
        newContactNo.setText(String.valueOf(currentResident.getContactNo()));




        return builder.create();
    }



    public void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    public void uploadFile(){
        if(mImageUri!=null){
            final StorageReference fileReference = mStorageRef.child("ProfilePicture"+"."+getFileExtendsion(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mDbRef = FirebaseDatabase.getInstance().getReference("residents/"+userId).child("profilePicture");
                            mDbRef.setValue(uri.toString());
                            Log.i("Url",uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Fail",e.getMessage());
                }
            });
        }
    }

    public String getFileExtendsion(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData()!=null) {
            mImageUri = data.getData();
            newProfileImage.setImageURI(mImageUri);
        }
    }

}
