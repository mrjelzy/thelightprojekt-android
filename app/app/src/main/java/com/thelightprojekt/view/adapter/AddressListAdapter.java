package com.thelightprojekt.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thelightprojekt.R;
import com.thelightprojekt.model.data.address.AddressResponse;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>{

    private ArrayList<AddressResponse> addresses;
    Context context;

    public AddressListAdapter(Context context, ArrayList<AddressResponse> addresses) {
        this.addresses = addresses;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_adapter, parent, false);
        return new AddressListAdapter.AddressListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListViewHolder holder, int position) {
        AddressResponse address = addresses.get(position);

        holder.addressAlias.setText(address.getAddress().getAlias());
        holder.addressAddress.setText(address.getAddress().getAddress1());
        holder.addressName.setText(address.getAddress().getFirstname() + " " + address.getAddress().getLastname());
        holder.addressCity.setText(address.getAddress().getPostcode() + " " + address.getAddress().getCity());
        holder.addressCountry.setText(address.getAddress().getCountryName());
    }

    @Override
    public int getItemCount() {
        return this.addresses.size();
    }

    public void setItems(ArrayList<AddressResponse> addresses) {
        this.addresses = addresses;
    }

    public class AddressListViewHolder extends RecyclerView.ViewHolder {

        private TextView addressAlias;
        private TextView addressName;
        private TextView addressAddress;
        private TextView addressCity;
        private TextView addressCountry;


        public AddressListViewHolder(@NonNull View itemView) {
            super(itemView);
            addressAlias = itemView.findViewById(R.id.adapter_address_alias);
            addressName = itemView.findViewById(R.id.adapter_address_name);
            addressAddress = itemView.findViewById(R.id.adapter_address_address);
            addressCity = itemView.findViewById(R.id.adapter_address_city);
            addressCountry = itemView.findViewById(R.id.adapter_address_country);

        }

    }
}
