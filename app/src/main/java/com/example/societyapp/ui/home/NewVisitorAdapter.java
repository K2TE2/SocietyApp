package com.example.societyapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.societyapp.R;
import com.example.societyapp.Visitor;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewVisitorAdapter extends RecyclerView.Adapter<NewVisitorAdapter.VisitorViewHolder> {

    Context ctx;
    List<Visitor> visitorList;

    public NewVisitorAdapter(Context ctx, List<Visitor> visitorList) {
        this.ctx = ctx;
        this.visitorList = visitorList;
    }

    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_visitor,null);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor visitor = visitorList.get(position);
        holder.nameNewVisitor.setText(visitor.getName());
        holder.contactNoNewVisitor.setText(visitor.getContactNumber());
        holder.vehicleNoNewVisitor.setText(visitor.getVehicleNumber());
        holder.rovNewVisitor.setText(visitor.getReasonOfVisit());
        if(visitor.getImage().equals("")){
            Log.i("hi","hi");
            holder.imageNewVisitor.setImageResource(R.drawable.profile_picture);
        }
        else {
            Picasso
                    .get()
                    .load(visitor.getImage().toString())
                    .into(holder.imageNewVisitor);
        }
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    class VisitorViewHolder extends RecyclerView.ViewHolder{

        ImageView imageNewVisitor;
        TextView nameNewVisitor,contactNoNewVisitor,vehicleNoNewVisitor,rovNewVisitor;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);

            imageNewVisitor = itemView.findViewById(R.id.imageNewVisitor);
            nameNewVisitor = itemView.findViewById(R.id.nameNewVisitor);
            contactNoNewVisitor = itemView.findViewById(R.id.contactNoNewVisitor);
            vehicleNoNewVisitor = itemView.findViewById(R.id.vehicleNoNewVisitor);
            rovNewVisitor = itemView.findViewById(R.id.rovNewVisitor);
        }
    }

}
