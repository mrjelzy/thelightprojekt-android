package com.thelightprojekt.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.thelightprojekt.R;
import com.thelightprojekt.model.UserState;
import com.thelightprojekt.model.data.customer.UserInfo;
import com.thelightprojekt.view.LoginFragment;
import com.thelightprojekt.viewmodel.AddressViewModel;

public class NewAddressFragment extends Fragment {

    private AddressViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo user = UserState.getInstance().getUser();

        if(user != null){

            TextView messageUpdate = view.findViewById(R.id.error_message_address);
            viewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String message) {
                    if(message != null){
                        messageUpdate.setText(message);
                        messageUpdate.setVisibility(View.VISIBLE);
                    }
                }
            });

            TextInputEditText aliasInput = view.findViewById(R.id.alias_input_address);
            TextInputEditText lastNameInput = view.findViewById(R.id.lastname_input_address);
            TextInputEditText firstNameInput = view.findViewById(R.id.firstname_input_address);
            TextInputEditText addressInput = view.findViewById(R.id.address_input_address);
            TextInputEditText cityInput = view.findViewById(R.id.city_input_address);
            TextInputEditText postcodeInput = view.findViewById(R.id.postcode_input_address);
            TextInputEditText mobileInput = view.findViewById(R.id.mobile_input_address);
            MaterialButton addButton = view.findViewById(R.id.submit_add_address_button);


            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.newAddress(String.valueOf(aliasInput.getText()),
                            String.valueOf(firstNameInput.getText()),
                            String.valueOf(lastNameInput.getText()),
                            String.valueOf(addressInput.getText()),
                            String.valueOf(cityInput.getText()),
                            String.valueOf(postcodeInput.getText()),
                            String.valueOf(mobileInput.getText()),
                            getViewLifecycleOwner());
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