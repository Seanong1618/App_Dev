package com.example.hanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
     LayoutInflater inflater;
     List<customer_details> customerDetail;

    public Adapter(Context ctx, List<customer_details> customerDetails) {
        this.inflater = LayoutInflater.from(ctx);
        this.customerDetail = customerDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //bind the data
        holder.name.setText(customerDetail.get(position).getName());
        holder.contact.setText(customerDetail.get(position).getContact());
        holder.temperature.setText(customerDetail.get(position).getTemperature());
        holder.address.setText(customerDetail.get(position).getAddress());
        holder.datetime.setText(customerDetail.get(position).getDatetime());

    }

    @Override
    public int getItemCount() {
        return customerDetail.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView contact;
        TextView temperature;
        TextView address;
        TextView datetime;


        public ViewHolder (@NonNull View itemView){
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            contact = (TextView) itemView.findViewById(R.id.contact);
            temperature = (TextView) itemView.findViewById(R.id.temperature);
            address = (TextView) itemView.findViewById(R.id.address);
            datetime = (TextView) itemView.findViewById(R.id.datetime);

        }
    }

}
