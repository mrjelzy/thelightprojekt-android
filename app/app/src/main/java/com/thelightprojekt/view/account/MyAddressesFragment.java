package com.thelightprojekt.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.address.AddressItem;
import com.thelightprojekt.model.data.address.AddressList;
import com.thelightprojekt.model.data.address.AddressResponse;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.model.data.order.OrderItem;
import com.thelightprojekt.model.data.order.OrderList;
import com.thelightprojekt.model.data.order.OrderResponse;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.view.adapter.AddressListAdapter;
import com.thelightprojekt.view.adapter.OrderListAdapter;
import com.thelightprojekt.viewmodel.AddressViewModel;
import com.thelightprojekt.viewmodel.OrderViewModel;

import java.util.ArrayList;


public class MyAddressesFragment extends Fragment {

    private AddressViewModel viewModel;
    private ArrayList<AddressResponse> addresses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        viewModel.init();
        addresses = new ArrayList<AddressResponse>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_adresses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo user = UserState.getInstance().getUser();
        MaterialButton newButton = view.findViewById(R.id.new_address_button);

        if(user != null) {

            RecyclerView addressesRecyclerView = view.findViewById(R.id.address_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            addressesRecyclerView.setLayoutManager(linearLayoutManager);

            AddressListAdapter adapter = new AddressListAdapter(requireContext(), addresses);
            addressesRecyclerView.setAdapter(adapter);
            addressesRecyclerView.setHasFixedSize(true);

            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.host_fragment_main_activity, new NewAddressFragment())
                            .setReorderingAllowed(true)
                            .addToBackStack("NewAddressFragment")
                            .commit();
                }
            });

            viewModel.getAddressListByIdCustomer(user.getId()).observe(getViewLifecycleOwner(), new Observer<AddressList>() {
                @Override
                public void onChanged(AddressList addressList) {
                    if(addressList != null){
                        addresses.clear();
                        for(AddressItem a : addressList.getAddresses()){
                                viewModel.getAddressById(String.valueOf(a.getId()), getViewLifecycleOwner()).observe(getViewLifecycleOwner(), new Observer<AddressResponse>() {
                                    @Override
                                    public void onChanged(AddressResponse addressResponse) {
                                        addresses.add(addressResponse);
                                        adapter.setItems(addresses);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        }
                    }
                }
            });


        }else{
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.host_fragment_main_activity, new LoginFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("LoginFragment")
                    .commit();
        }
    }
}