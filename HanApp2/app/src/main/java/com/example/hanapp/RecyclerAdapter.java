package com.example.hanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final  int TYPE = 1;
    private final Context context;
    private final List<Object> listRecyclerItem;

    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView contact;
        private TextView temperature;
        private TextView address;
        private TextView datetime;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            temperature = itemView.findViewById(R.id.temperature);
            address = itemView.findViewById(R.id.address);
            datetime = itemView.findViewById(R.id.datetime);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE:

            default:

                View layoutView;
                layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_item, viewGroup);
                return new ItemViewHolder((layoutView));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);

        switch (viewType)
        {
            case TYPE:
            default:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                customer_details customer_details = (customer_details) listRecyclerItem.get(i);

                itemViewHolder.name.setText(customer_details.getName());
                itemViewHolder.contact.setText(customer_details.getContact());
                itemViewHolder.temperature.setText(customer_details.getTemperature());
                itemViewHolder.address.setText(customer_details.getAddress());
                itemViewHolder.datetime.setText(customer_details.getDatetime());
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
